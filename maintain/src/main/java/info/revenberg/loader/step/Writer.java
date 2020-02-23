package info.revenberg.loader.step;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
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

import info.revenberg.loader.objects.DataObject;

import org.springframework.batch.item.ItemWriter;

public class Writer implements ItemWriter<DataObject> {
    private static int counter = 0;

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

    @Override
    public void write(List<? extends DataObject> messages) {
        int count = 0;
        int retry = 3;
        for (DataObject msg : messages) {
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

}