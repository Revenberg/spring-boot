package info.revenberg.loader.step;

import java.util.ArrayList;
import java.util.List;

import info.revenberg.domain.Vers;
import info.revenberg.loader.objects.DataObject;
import info.revenberg.loader.objects.RestResponsePage;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.web.client.RestTemplate;

public class Reader implements ItemReader<DataObject> {

	private static int counter = 0;
	List<String> list = new ArrayList<>();

	@Override
	public DataObject read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		counter++;

		String uri = "http://localhost:8090/rest/v1/vers?page=" + Integer.toString(counter) + "&size=1";

		RestTemplate restTemplate = new RestTemplate();

		RestResponsePage<Vers> pages = restTemplate.getForObject(uri, RestResponsePage.class);

		List<Vers> result = pages.getContent();

		System.out.println(Integer.toString(counter) + "================");
		System.out.println(Integer.toString(counter) + ":================");
		System.out.println(pages);
		System.out.println(Integer.toString(counter) + ":================");
		System.out.println(result);
		System.out.println(Integer.toString(counter) + ":1111111111111111111111111111");
		System.out.println(result.get(0).getClass());
		System.out.println(Integer.toString(counter) + ":22222222222222222222");
		System.out.println(result.get(0));
		System.out.println(Integer.toString(counter) + ":!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
//		Vers vers = result.get(0);
//		System.out.println("================");
//		System.out.println(vers.getId());
//		System.out.println(vers.getLocation());

		/*
		 * if (!list.isEmpty()) { String element = list.get(0); list.remove(0); return
		 * new DataObject(element); }
		 */
		return null;
	}

}