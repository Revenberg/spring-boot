package info.revenberg.song.api.rest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import info.revenberg.domain.TempFile;
import info.revenberg.song.exception.DataContentTypeException;
import info.revenberg.song.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;

@RestController
@RequestMapping(value = "/rest/v1/image")
@Api(tags = { "image" })
public class ImageController {
	@Value("${upload.location}")
	private String location;

	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = { "multipart/form-data" }, produces = {
		"application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Upload a file resource.", notes = "Returns file name.")
	public @ResponseBody TempFile uploadData( @RequestPart("file") MultipartFile file) throws Exception {
		if (file == null) {
			throw new RuntimeException("You must select the a file for uploading");
		}
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		Path path = new File(filename).toPath();
		String mimeType = Files.probeContentType(path);		
		if (!mimeType.startsWith("image/")) {
			throw new DataContentTypeException("Content type must be image not " + mimeType);
		}
		String nfilename = fileService.store(file, location);		
		TempFile tempFile = new TempFile(file, nfilename);
		return tempFile;
	}
}