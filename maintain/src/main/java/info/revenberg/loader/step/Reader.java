package info.revenberg.loader.step;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.web.client.RestTemplate;

import info.revenberg.domain.Vers;

public class Reader implements ItemReader<Vers> {

	private static Long lastID = 0L;	

	@Override
	public Vers read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		String uri = "http://localhost:8090/rest/v1/vers/" + Long.toString(lastID) + "/next";

		RestTemplate restTemplate = new RestTemplate();

		Long id = restTemplate.getForObject(uri, Long.class);
		System.out.println(Long.toString(lastID) + "!!!!!!!!!!!!! a !!!!!!!!!!!!!!");
		System.out.println(id);		
		if (lastID == id) {
			return read();
		}
		lastID = id;
		
		uri = "http://localhost:8090/rest/v1/vers/" + Long.toString(id);
		Vers vers = restTemplate.getForObject(uri, Vers.class);
		System.out.println(vers);
		System.out.println(Long.toString(lastID) + "!!!!!!!!!!!!!!!! b !!!!!!!!!!!");
		return vers;

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
//		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println(vers.getId());
//		System.out.println(vers.getLocation());

		/*
		 * if (!list.isEmpty()) { String element = list.get(0); list.remove(0); return
		 * new Vers(element); }
		 */
		
	}

}