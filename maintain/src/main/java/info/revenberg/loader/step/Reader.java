package info.revenberg.loader.step;

import java.util.ArrayList;
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
		counter++;

		String uri = "http://localhost:8090/rest/v1/vers?page=" + Integer.toString(counter) + "&size=1";

		RestTemplate restTemplate = new RestTemplate();

		RestResponsePage<Vers> pages = restTemplate.getForObject(uri, RestResponsePage.class);

		Stream<Vers> v = pages.get();
		Optional<Vers> c = v.findFirst();
		
		if (c.isPresent()) {
			System.out.println(Integer.toString(counter) + "!!!!!!!!!!!!! a !!!!!!!!!!!!!!");
			System.out.println((c.get()).getClass().getName());
			System.out.println(c.get());
			System.out.println(Integer.toString(counter) + "!!!!!!!!!!!!!!!! b !!!!!!!!!!!");
			return (Vers) c.get();
		}

		
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