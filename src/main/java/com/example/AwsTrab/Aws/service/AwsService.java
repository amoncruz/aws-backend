package com.example.AwsTrab.Aws.service;

import com.amazonaws.services.ec2.model.*;
import org.springframework.stereotype.Service;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AwsService {

    private static final AWSCredentials AWS_CREDENTIALS;

    static {
        // Your accesskey and secretkey
        AWS_CREDENTIALS = new BasicAWSCredentials(
                "AKIATQZVJF5H2KWW3XL5",
                "XbOd7KjS4mUeQEdkMA+rg88nlK4zTzhZ/PUh8d50"
        );
    }

   /* CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest()
            .withKeyName("teste");
    CreateKeyPairResult createKeyPairResult = ec2Client.createKeyPair(createKeyPairRequest);*/

    public void stopInstance(String id){

        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()

                .withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS))
                .withRegion(Regions.US_EAST_2)

                .build();

        //Stop EC2 Instance
        String instanecID = id;
        StopInstancesRequest stopInstancesRequest = new StopInstancesRequest()
                .withInstanceIds(instanecID);

        ec2Client.stopInstances(stopInstancesRequest)
                .getStoppingInstances()
                .get(0)
                .getPreviousState()
                .getName();
        System.out.println("Stopped the Instnace with ID: "+instanecID);

    }

    public void startInstance(String id) {

        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS))
                .withRegion(Regions.US_EAST_2)
                .build();

        String instanecID = id;

        StartInstancesRequest request = new StartInstancesRequest()
                .withInstanceIds(instanecID);

        ec2Client.startInstances(request);
    }


    public void terminateInstance(String id) {

        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS))
                .withRegion(Regions.US_EAST_2)
                .build();

        String instanecID = id;
        TerminateInstancesRequest request = new TerminateInstancesRequest().withInstanceIds(instanecID);

        ec2Client.terminateInstances(request);

    }

    public void createInstance() {

        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS))
                .withRegion(Regions.US_EAST_2)
                .build();



        RunInstancesRequest runInstancesRequest = new RunInstancesRequest().withImageId("ami-039589fd877979c04") // https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AMIs.html | https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/usingsharedamis-finding.html
                .withInstanceType("t2.micro") // https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/instance-types.html
                .withMinCount(1)
                .withMaxCount(1);
               /* .withKeyName("teste");*/ // optional - if not present, can't connect to instance

        String yourInstanceId = ec2Client.runInstances(runInstancesRequest).getReservation().getInstances().get(0).getInstanceId();

    }

    public List<Reservation>  describeInstances(){

        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()

                .withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS))
                .withRegion(Regions.US_EAST_2)

                .build();

        DescribeInstancesRequest request = new DescribeInstancesRequest();
        List<String> valuesT1 = new ArrayList<String>();
        valuesT1.add("teste");
        Filter filter = new Filter("key-name", valuesT1);

        DescribeInstancesResult result = ec2Client.describeInstances();

        List<Reservation> reservations = result.getReservations();

        return reservations;

    }

}
