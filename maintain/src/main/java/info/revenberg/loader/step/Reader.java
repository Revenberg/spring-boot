package info.revenberg.loader.step;

import java.util.concurrent.TimeUnit;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.web.client.RestTemplate;

import info.revenberg.domain.Vers;

public class Reader implements ItemReader<Vers> {

	private static Long lastID = 0L;

	@Override
	public synchronized Vers read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if lastID > 0 {
			return null;
		}
		System.out.println(Long.toString(lastID) + "synchronized !!!!!!!!!!!!!!!! a !!!!!!!!!!!");

		String uri = "http://40.122.30.210:8090/rest/v1/vers/" + Long.toString(lastID) + "/next";

		RestTemplate restTemplate = new RestTemplate();

		Long id = restTemplate.getForObject(uri, Long.class);
		// System.out.println(Long.toString(lastID) + "!!!!!!!!!!!!! a !!!!!!!!!!!!!!");
		if (id == null) {
			return null;
		}
		if (lastID == id) {
			return read();
		}
		lastID = id;

		uri = "http://40.122.30.210:8090/rest/v1/vers/" + Long.toString(id);
		System.out.println(Long.toString(lastID) + "!!!!!!!!!!!!!!!! b0 !!!!!!!!!!!");
		System.out.println(uri);
		Vers vers = restTemplate.getForObject(uri, Vers.class);
		System.out.println(vers);
		System.out.println(Long.toString(lastID) + "!!!!!!!!!!!!!!!! b1 !!!!!!!!!!!");
		TimeUnit.SECONDS.sleep(15);
		return vers;

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