package info.revenberg.loader.step;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import info.revenberg.domain.Vers;
import info.revenberg.domain.line.FindLinesInImage;

public class Processor implements ItemProcessor<Vers, FindLinesInImage> {

	@Value("${media.location}")
	private String mediaLocation;

	@Override
	public FindLinesInImage process(final Vers vers) throws Exception {
		if (vers == null) {
			return null;
		}
		FindLinesInImage result = null;
		try {
			System.out.println("process A");
			System.out.println(vers);
			String uri = "http://40.122.30.210:8090/rest/v1/vers/" + Long.toString(vers.getId()) + "/image";
			System.out.println(uri);

			result = new FindLinesInImage(uri, mediaLocation, vers.getSong().getBundle().getName(),
					vers.getSong().getName());

			System.out.println(result);
			System.out.println("process B");

		} catch (Exception e) {
		}
return result;
	}
}