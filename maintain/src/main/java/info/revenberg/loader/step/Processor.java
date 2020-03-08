package info.revenberg.loader.step;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import info.revenberg.domain.Line;
import info.revenberg.domain.Vers;
import info.revenberg.domain.line.FindLinesInImage;
import info.revenberg.domain.line.ImageDefinition;
import java.util.Map;

public class Processor implements ItemProcessor<Vers, String> {

    @Value("${media.location}")
    private String mediaLocation;

    @Override
    public String process(final Vers vers) throws Exception {
        if (vers == null) {
            return null;
        }
        FindLinesInImage result = null;

        try {
            System.out.println("process A");
            System.out.println(vers);
            String uri = "http://40.122.30.210:8090/rest/v1/vers/" + Long.toString(vers.getId()) + "/image";
            System.out.println(uri);

            FindLinesInImage images = new FindLinesInImage(uri, mediaLocation + "/vers", vers.getSong().getBundle().getName(),
                    vers.getSong().getName());

            System.out.println(result);
            System.out.println("process B");

            for (Map.Entry<Integer, ImageDefinition> entry : images.getImageDefinitions().entrySet()) {
                ImageDefinition imageDefinition = entry.getValue();

                Line line = new Line();
                line.setRank(entry.getKey());
                line.setLocation(imageDefinition.getFilename());
                line.setVers(vers);
                System.out.println(imageDefinition.getFilename());
                return imageDefinition.getTitle();
            }
        } catch (Exception e) {
        }
        return null;
    }
}