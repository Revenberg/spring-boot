package info.revenberg.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import info.revenberg.exception.ResourceNotFoundException;
import info.revenberg.exception.StorageException;

@Service
public class FileService {
	private static final Logger logger = Logger.getLogger(FileService.class.getName());

	private Path rootLocation = null;

	private static void createFolderIfNotExists(String dirName) throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}

	private List<String> mySort(List<String> filelist) {
		TreeMap<String, String> list = new TreeMap<String, String>();
		String key;
		for (String n : filelist) {
			if (n.contains("image")) {
				key = n.substring(n.lastIndexOf("image") + 5, n.lastIndexOf("."));
				while (key.length() < 8) {
					key = "0" + key;
				}
				list.put(key, n);
			}

		}
		List<String> rc = new ArrayList<>();
		for (Map.Entry<String, String> me : list.entrySet()) {
			rc.add((String) me.getValue());
		}

		return rc;
	}

	public List<String> unzip(MultipartFile mFile, String filename, String location, String dest) {
		FileService.createFolderIfNotExists(dest);
		this.rootLocation = Paths.get(location.trim());
		filename = this.rootLocation + "/" + filename;

		List<String> rc = new ArrayList<String>();
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(filename);
		} catch (IOException e) {
			throw new ResourceNotFoundException("resource not found " + filename);
		}
		Enumeration<?> enu = zipFile.entries();
		while (enu.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) enu.nextElement();

			String name = dest + "/" + zipEntry.getName();

			File file = new File(name);
			rc.add(name);
			if (name.endsWith("/")) {
				file.mkdirs();
				continue;
			}

			File parent = file.getParentFile();
			if (parent != null) {
				parent.mkdirs();
			}

			InputStream is;
			try {
				is = zipFile.getInputStream(zipEntry);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
				is.close();
				fos.close();

			} catch (IOException e) {
				throw new StorageException("Unzip error " + filename);
			}
		}
		try {
			zipFile.close();
		} catch (IOException e) {
			throw new StorageException("Unzip error " + filename);
		}

		return mySort(rc);
	}

	public String store(MultipartFile file, String location) {
		FileService.createFolderIfNotExists(location);
		this.rootLocation = Paths.get(location.trim());
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			while (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
				filename = filename.replace("..", ".").replace("\\", "_").replace("/", "_").trim();
			}
			if (filename.contains("/") || filename.contains("\\") || filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			filename = Normalizer.normalize(filename, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

			try (InputStream inputStream = file.getInputStream()) {
				String uuid = UUID.randomUUID().toString();
				Files.copy(inputStream, this.rootLocation.resolve(uuid + "_" + filename),
						StandardCopyOption.REPLACE_EXISTING);
				new FileCleaningThread(this.rootLocation.resolve(uuid + "_" + filename).toFile().getAbsolutePath());
				return uuid + "_" + filename;
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}
	/*
	 * public Path load(String filename) { return rootLocation.resolve(filename); }
	 * 
	 * public Resource loadAsResource(String filename) { try { Path file =
	 * load(filename); Resource resource = new UrlResource(file.toUri()); if
	 * (resource.exists() || resource.isReadable()) { return resource; } else {
	 * throw new StorageFileNotFoundException( "Could not read file: " + filename);
	 * 
	 * } } catch (MalformedURLException e) { throw new
	 * StorageFileNotFoundException("Could not read file: " + filename, e); } }
	 */

	private static File copyFile(String srcPath, String srcFilename, String destPath, String destFilename)
			throws IOException {
		InputStream is = null;
		OutputStream os = null;
		logger.info("src: " + srcPath + "/" + srcFilename);
		logger.info("dest: " + destPath + "/" + destFilename);
		try {
			File directory = new File(String.valueOf(destPath));
			if (!directory.exists()) {
				directory.mkdir();
			}

			is = new FileInputStream(srcPath + "/" + srcFilename);
			os = new FileOutputStream(destPath + "/" + destFilename);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			throw new ResourceNotFoundException("resource not found " + srcPath + "/" + srcFilename);
		}
		return new File(destPath + "/" + destFilename);
	}

	public File moveToMedia(String mediaLocation, String temp, String bundle, String song) throws IOException {
		logger.info("temp: " + temp);
		String[] s1 = temp.split("/");
		String oFilename = s1[s1.length - 1];
		String[] s2 = oFilename.split("\\.");
		String ext = s2[s2.length - 1];
		String vers = Integer.toString(Integer.valueOf(oFilename.replace("." + ext, "").replace("image", "")) - 1);
		String filename = (bundle + "." + song + ".vers_" + vers + "." + ext).replace(" ", "_");
		return copyFile(temp.replace("/" + oFilename, ""), oFilename, mediaLocation, filename);
	}

}