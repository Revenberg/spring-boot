package info.revenberg.loader.step;

import info.revenberg.loader.objects.DataObject;

import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<DataObject , DataObject > {

	@Override
	public DataObject process(final DataObject data) throws Exception {
		if (data == null) {
			return data;
		}		
	return null;
}
}