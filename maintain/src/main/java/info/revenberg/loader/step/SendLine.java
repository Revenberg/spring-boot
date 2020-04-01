package info.revenberg.loader.step;

import info.revenberg.domain.Line;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class SendLine {

    @Autowired
    RestTemplate restTemplate;

    public Line createPost(Line line) {
        String url = "http://40.122.30.210:8090/rest/v1/line";
    
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    
        // send POST request
        ResponseEntity<Line> response = this.restTemplate.postForEntity(url, line, Line.class);
    
        System.out.println(response);

        // check response status code
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }
}