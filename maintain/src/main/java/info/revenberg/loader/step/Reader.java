package info.revenberg.loader.step;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import info.revenberg.domain.Vers;
import info.revenberg.loader.objects.RestResponsePage;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.web.client.RestTemplate;

public class Reader implements ItemReader<Vers> {

	private static int counter = 0;
	List<String> list = new ArrayList<>();

	@Override
	public Vers read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		String uri = "http://localhost:8090/rest/v1/vers/" + Integer.toString(counter) + "/next";

		RestTemplate restTemplate = new RestTemplate();

		Long id = restTemplate.getForObject(uri, Long.class);
		System.out.println(Integer.toString(counter) + "!!!!!!!!!!!!! a !!!!!!!!!!!!!!");
		System.out.println(id);
		System.out.println(Integer.toString(counter) + "!!!!!!!!!!!!!!!! b !!!!!!!!!!!");

		/*
		RestResponsePage pages = restTemplate.getForObject(uri, RestResponsePage.class);
	
		List v = pages.getContent();
		LinkedHashMap c = (LinkedHashMap) v.get(0);
		System.out.println(Integer.toString(counter) + "!!!!!!!!!!!!! a !!!!!!!!!!!!!!");
		System.out.println(c);
		System.out.println(c.getClass());
		System.out.println(c.getClass().getSimpleName());
		System.out.println(c.size());
		System.out.println(Integer.toString(counter) + "!!!!!!!!!!!!!!!! b !!!!!!!!!!!");
		*/

		/*if (c.isPresent()) {
			System.out.println(Integer.toString(counter) + "!!!!!!!!!!!!! a !!!!!!!!!!!!!!");
			System.out.println(c.get());
			System.out.println(c.get().getClass());
			System.out.println(c.get().getClass().getName());
			System.out.println(Integer.toString(counter) + "!!!!!!!!!!!!!!!! b !!!!!!!!!!!");
			return (Vers) c.get();
		}
*/
		
//		Vers vers = result.get(0);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println(vers.getId());
//		System.out.println(vers.getLocation());

		/*
		 * if (!list.isEmpty()) { String element = list.get(0); list.remove(0); return
		 * new Vers(element); }
		 */
		return null;
	}

}