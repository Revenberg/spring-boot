package info.revenberg.loader.step;

import org.springframework.batch.item.ItemProcessor;

import info.revenberg.domain.Vers;

public class Processor implements ItemProcessor<Vers , Vers > {

	@Override
	public Vers process(final Vers data) throws Exception {
		if (data == null) {
			System.out.println(data);
			//return data;
		}		
	return null;
}
}