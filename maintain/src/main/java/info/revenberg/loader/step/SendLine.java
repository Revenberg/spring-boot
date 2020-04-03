package info.revenberg.loader.step;

import info.revenberg.domain.Line;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.Collections;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class SendLine {

    @Autowired
    RestTemplate restTemplate;

    public Line createPost(Line line) {
        System.out.println("createPost 1");
        try {
            String url = "http://40.122.30.210:8090/rest/v1/line";
            System.out.println("createPost 2");
            Gson gson = new Gson();
            String json = gson.toJson(line);
            System.out.println("createPost 2b");
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_FORM_URLENCODED);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://localhost:8080/register");
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());

        } catch (Exception ex) {
            System.out.println("createPost 2b");
            System.out.println(ex);
            System.out.println("createPost 2b");
            // handle exception here

        }
        return line;

    }

    public Line createPost1(Line line) {
        System.out.println("createPost 1");
        String url = "http://40.122.30.210:8090/rest/v1/line";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        System.out.println("createPost 2");
        Gson gson = new Gson();
        String json = gson.toJson(line);
        System.out.println("createPost 2b");

        System.out.println(json);
        System.out.println("createPost 2b");
        // send POST request

        HttpClient httpClient = HttpClientBuilder.create().build(); // Use this instead

        try {

            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(json);
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);
            // handle response here...

        } catch (Exception ex) {
            System.out.println("createPost 2b");
            System.out.println(ex);
            System.out.println("createPost 2b");
            // handle exception here

        } finally {
            // Deprecated
            // httpClient.getConnectionManager().shutdown();
        }
        System.out.println("createPost 9");
        return line;
        /*
         * ResponseEntity<Line> response = this.restTemplate.postForEntity(url, line,
         * Line.class); System.out.println("createPost 3");
         * System.out.println(response); System.out.println("createPost 4"); // check
         * response status code if (response.getStatusCode() == HttpStatus.CREATED) {
         * return response.getBody(); } else { return null; }
         */
    }
}