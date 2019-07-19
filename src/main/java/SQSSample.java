import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class SQSSample {

    public String a;

    public void setA(String a) {
        this.a = a;
    }

    public static void main(String[] args) throws IOException {
        AmazonSQS sqs = new AmazonSQSClient();
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        sqs.setRegion(usWest2);
        sqs.setEndpoint("http://localhost:5000");

        String queueName = "my-first-queue";
        sqs.createQueue(queueName);

        System.out.println("Listing queues");
        for (String queue_url: sqs.listQueues().getQueueUrls()) {
            System.out.println(" - " + queue_url);
        }
        System.out.println();

    }

}

