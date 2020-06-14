package info.revenberg.loader.step;

import org.springframework.batch.item.ItemReader;

import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

public class Reader implements ItemReader<Long> {

	private static Long lastID = 0L;

	@Override
	public synchronized Long read() {
		String directory = "/var/songs";
		String fileName = "maintain.next";
		String absolutePath = directory + File.separator + fileName;
		Long id = 0L;

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

		try {
			String uri = "http://40.122.30.210:8090/rest/v1/vers/" + Long.toString(lastID) + "/next";

			RestTemplate restTemplate = new RestTemplate();
			lastID = restTemplate.getForObject(uri, Long.class);
			if (lastID == 0L) {
				return null;
			}

		} catch (Exception e) {
			// Exception handling
			System.out.println(e.getMessage());
			lastID = lastID + 1;
		}

		// Write the content in file
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(absolutePath))) {
			bufferedWriter.write(Long.toString(lastID));
		} catch (IOException e) {
			// Exception handling
		}
		Runtime runtime = Runtime.getRuntime();

		NumberFormat format = NumberFormat.getInstance();

		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();

		System.out.println("free memory: " + format.format(freeMemory / 1024) );
		System.out.println("allocated memory: " + format.format(allocatedMemory / 1024) );
		System.out.println("max memory: " + format.format(maxMemory / 1024) );
		System.out.println("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) );
		return lastID;

	}

}