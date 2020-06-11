package info.revenberg.loader.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.web.client.RestTemplate;

public class Reader implements ItemReader<Long> {

	private static Long lastID = 0L;

	@Override
	public synchronized Long read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {		
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