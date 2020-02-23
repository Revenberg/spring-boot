package info.revenberg.loader.step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import info.revenberg.loader.objects.DataObject;

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

        String uri = "http://40.122.30.210:8090/rest/v1/vers?page=" + Integer.toString(counter) + "&size=100";
        
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);


/*	   if (!list.isEmpty()) {
		   String element = list.get(0);
		   list.remove(0);			
		   return new DataObject(element);
	   } 
*/
	   return null;
   }


}