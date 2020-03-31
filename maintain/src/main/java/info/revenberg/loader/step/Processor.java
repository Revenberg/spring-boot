package info.revenberg.loader.step;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import info.revenberg.domain.Line;
import info.revenberg.domain.Vers;
import info.revenberg.domain.line.FindLinesInImage;
import info.revenberg.domain.line.ImageDefinition;
import info.revenberg.loader.objects.DataObject;
import info.revenberg.service.LineService;

import java.util.Map;

public class Processor implements ItemProcessor<Vers, DataObject> {

    @Value("${media.location}")
    private String mediaLocation;

    @Autowired
        private LineService lineService;
        
    @Override
    public synchronized DataObject process(final Vers vers) throws Exception {
        if (vers == null) {
            return null;
        }
        DataObject rc = new DataObject();
        
        try {
            //System.out.println("process A");
            //System.out.println(vers);
            String uri = "http://40.122.30.210:8090/rest/v1/vers/" + Long.toString(vers.getId()) + "/image";
            //System.out.println(uri);

            //mediaLocation = "D:/Songs/temp";
            mediaLocation = "/var/songs/media";
            FindLinesInImage images = new FindLinesInImage(uri, mediaLocation + "/vers",
                    vers.getSong().getBundle().getName(), vers.getSong().getName(), vers.getSong().getId());

            //System.out.println("process B");

            for (Map.Entry<Integer, ImageDefinition> entry : images.getImageDefinitions().entrySet()) {
                ImageDefinition imageDefinition = entry.getValue();

                Line line = new Line();
                line.setText(imageDefinition.getFilename());
                line.setRank(entry.getKey() + 1);
                line.setLocation(imageDefinition.getFilename());
                line.setVers(vers);
                line.setLocation(imageDefinition.getFilename());
                System.out.println("@@@@@@@@@@@@@@@@@@@@ a @@@@@@@@@@@@@@@@@@@@@");
                System.out.println(imageDefinition.getFilename());
                System.out.println(line);

                rc.add(line);
                System.out.println( imageDefinition.getTitle() );
                System.out.println("@@@@@@@@@@@@@@@@@@@@ c @@@@@@@@@@@@@@@@@@@@@");
            }
        } catch (Exception e) {
            System.out.println("===========Exception ============");            
            System.out.println(vers);
            System.out.println(mediaLocation);            
            System.out.println(e);
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
        return rc;
    }
}