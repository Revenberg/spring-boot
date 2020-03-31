package info.revenberg.loader.step;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.io.File;
import java.io.IOException;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.batch.item.ItemWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import info.revenberg.domain.Line;
import info.revenberg.loader.objects.DataObject;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Writer implements ItemWriter<DataObject> {
    @Autowired
   RestTemplate restTemplate;
   
    public String uploadFile(String postEndpoint, String filename) throws IOException {

        File testUploadFile = new File(filename);

        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                // keep alive for 30 seconds
                return 30 * 1000;
            }

        };

        CloseableHttpClient httpclient = HttpClients.custom().setKeepAliveStrategy(myStrategy).build();

        // build httpentity object and assign the file that need to be uploaded
        HttpEntity postData = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("file", testUploadFile, ContentType.DEFAULT_BINARY, testUploadFile.getName()).build();

        // build http request and assign httpentity object to it that we build above
        HttpUriRequest postRequest = RequestBuilder.post(postEndpoint).setEntity(postData).build();

        // System.out.println("Executing request " + postRequest.getRequestLine());

        HttpResponse response = httpclient.execute(postRequest);

        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        // Create the StringBuffer object and store the response into it.
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        // System.out.println("Response : \n" + result);

        // Throw runtime exception if status code isn't 200
        if ((response.getStatusLine().getStatusCode() < 200) || (response.getStatusLine().getStatusCode() > 299)) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        }
        return result.toString();
    }
/*
    public void createPost() {
        String url = "http://40.122.30.210:8090/rest/v1/line";            

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Line> entity = new HttpEntity<Line>(Line,headers);
  
        return restTemplate.exchange(
           "https://example.com/endpoint", HttpMethod.POST, entity, String.class).getBody();
     
    }
*/
    @Override
    public void write(List<? extends DataObject> dataObjects) throws Exception {
    
        for (DataObject dataObject : dataObjects) {
            for (Line line : dataObject.getLines()) {
                System.out.println("WWWWWWWWWRRRRRRRRRRRRRIIIIIIIIIITTTTTTTTTTEEEEEEEEE 1");
                System.out.println(line);
                System.out.println("WWWWWWWWWRRRRRRRRRRRRRIIIIIIIIIITTTTTTTTTTEEEEEEEEE 2");
                String response = uploadFile("http://40.122.30.210:8090/rest/v1/image/upload/", line.getLocation());
                System.out.println("WWWWWWWWWRRRRRRRRRRRRRIIIIIIIIIITTTTTTTTTTEEEEEEEEE 3");
                System.out.println(response);
                System.out.println("WWWWWWWWWRRRRRRRRRRRRRIIIIIIIIIITTTTTTTTTTEEEEEEEEE 4");
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response);
                System.out.println("WWWWWWWWWRRRRRRRRRRRRRIIIIIIIIIITTTTTTTTTTEEEEEEEEE 5");
                System.out.println(json);
                System.out.println("WWWWWWWWWRRRRRRRRRRRRRIIIIIIIIIITTTTTTTTTTEEEEEEEEE 6");
                System.out.println(json.get("filename"));
                System.out.println("WWWWWWWWWRRRRRRRRRRRRRIIIIIIIIIITTTTTTTTTTEEEEEEEEE 7");
            }
        }
        TimeUnit.SECONDS.sleep(30);
    }
/*
public test () {
    List<? extends vers>  messages = null;
        int count = 0;
        int retry = 3;
        for (Vers msg : messages) {
            if (msg != null) {
                if (msg.getFilename() != "") {
                    counter++;
                    System.out.println(Integer.toString(counter) + " " + Integer.toString(messages.size()) + " "
                            + Integer.toString(count) + " Writing the data " + " - " + msg.getBundleName() + " - "
                            + msg.getSongName());
                    retry = 15;
                    while (retry > 0) {
                        try {
                            uploadFile("http://40.122.30.210:8090/rest/v1/ppt/" + msg.getBundleName() + "/"
                                    + msg.getSongName(), msg.getFilename());
                            retry = 0;
                        } catch (Exception e) {
                            retry--;
                            System.out.println(Integer.toString(retry) + " Writing the data " + " - "
                                    + msg.getBundleName() + " - " + msg.getSongName());
                        }
                    }
                }
                count++;
            }
        }
    }
*/
   
}