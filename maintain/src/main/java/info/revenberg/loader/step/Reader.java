package info.revenberg.loader.step;

import org.springframework.batch.item.ItemReader;

import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Reader implements ItemReader<Long> {

	private static Long lastID = 0L;

	@Override
	public synchronized Long read() {
		System.out.println(System.getProperty("logfile.path"));
		String directory = System.getProperty("logfile.path");
		String fileName = "maintain.next";
		String absolutePath = directory + File.separator + fileName;

		if (lastID == 0L) {
			// Read the content from file
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath))) {
				String line = bufferedReader.readLine();
				while (line != null) {
					System.out.println(line);
					lastID = Long.valueOf(line);
					line = bufferedReader.readLine();
				}
			} catch (FileNotFoundException e) {
				// Exception handling
			} catch (IOException e) {
				// Exception handling
			}
		}

		String uri = "http://40.122.30.210:8090/rest/v1/vers/" + Long.toString(lastID) + "/next";

		RestTemplate restTemplate = new RestTemplate();

		Long id = restTemplate.getForObject(uri, Long.class);
		if (id == null) {
			return null;
		}
		if (lastID == id) {
			return read();
		}
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(Long.toString(id));

		// Write the content in file
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(absolutePath))) {
			bufferedWriter.write(Long.toString(lastID));
		} catch (IOException e) {
			// Exception handling
		}

		lastID = id;

		return lastID;

		/*
		 * RestResponsePage pages = restTemplate.getForObject(uri,
		 * RestResponsePage.class);
		 * 
		 * List v = pages.getContent(); LinkedHashMap c = (LinkedHashMap) v.get(0);
		 * System.out.println(Integer.toString(counter) +
		 * "!!!!!!!!!!!!! a !!!!!!!!!!!!!!"); System.out.println(c);
		 * System.out.println(c.getClass());
		 * System.out.println(c.getClass().getSimpleName());
		 * System.out.println(c.size()); System.out.println(Integer.toString(counter) +
		 * "!!!!!!!!!!!!!!!! b !!!!!!!!!!!");
		 */

		/*
		 * if (c.isPresent()) { System.out.println(Integer.toString(counter) +
		 * "!!!!!!!!!!!!! a !!!!!!!!!!!!!!"); System.out.println(c.get());
		 * System.out.println(c.get().getClass());
		 * System.out.println(c.get().getClass().getName());
		 * System.out.println(Integer.toString(counter) +
		 * "!!!!!!!!!!!!!!!! b !!!!!!!!!!!"); return (Vers) c.get(); }
		 */

		// Vers vers = result.get(0);
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		// System.out.println(vers.getId());
		// System.out.println(vers.getLocation());

		/*
		 * if (!list.isEmpty()) { String element = list.get(0); list.remove(0); return
		 * new Vers(element); }
		 */

	}

}