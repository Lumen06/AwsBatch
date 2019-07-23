import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.batch.model.ComputeResource;
import com.amazonaws.services.batch.model.CreateComputeEnvironmentRequest;
import com.amazonaws.services.batch.model.CreateComputeEnvironmentResult;
import com.amazonaws.services.batch.AWSBatchClientBuilder;
import com.amazonaws.services.batch.*;
import com.amazonaws.services.batch.model.DescribeJobsRequest;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


//TODO(1): get response from existing user, his AccessKey and put into deleteAccessKeyByUser method
//TODO(2): find right serviceRoleArn

public class BatchSample {

    private static String awesomeUser = "awesome_user18";

    public static void main(String[] args) throws IOException {


        Region usWest2 = Region.getRegion(Regions.US_WEST_2);

        final CreateUserResult awesomeUserResponse = IamManager.createUser(awesomeUser);
        System.out.println(awesomeUserResponse);

        final CreateAccessKeyResult iamResponse = IamManager.createIam(awesomeUser);
        System.out.println(iamResponse);

        final Policy policy = IamManager.createPolicy("policy1", IamManager.ALLOW_EVERYTHING_POLICY_DOCUMENT);

        final Role role = IamManager.createRole("role3", IamManager.ALLOW_EVERYTHING_POLICY_DOCUMENT);
        System.out.println(role);

        AWSBatch client = AWSBatchClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:5000", Regions.US_WEST_2.getName()))
                .build();

        CreateComputeEnvironmentRequest request = new CreateComputeEnvironmentRequest()
                .withComputeEnvironmentName("C4OnDemand")
                .withType("MANAGED")
                .withState("ENABLED")
                .withComputeResources(
                        new ComputeResource().withType("EC2").withMinvCpus(0).withMaxvCpus(100).withDesiredvCpus(48)
                                .withInstanceTypes( "t2.micro", "c4.large", "c4.xlarge", "c4.2xlarge", "c4.4xlarge", "c4.8xlarge")
                                .withSubnets("subnet-a30d20e8", "subnet-8b4f5dd1", "subnet-5ab63c71","subnet-7d838304").withSecurityGroupIds("sg-cf5093b2")
                                .withEc2KeyPair("MyKeyPair").withInstanceRole("ecsInstanceRole"))
                .withServiceRole(role.getArn());

        CreateComputeEnvironmentResult response = client.createComputeEnvironment(request);


        //Submit to delete the user
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();

        AmazonIdentityManagement iam =
                AmazonIdentityManagementClientBuilder.defaultClient();

        iam.listUsers();

        /*IamManager.deleteAccessKeyByUser(awesomeUser, iamResponse.getAccessKey().getAccessKeyId());
        IamManager.deleteUser(awesomeUser);*/

      //  System.out.println(response.getComputeEnvironmentArn())


//        TODO: job for batch  => https://stackoverflow.com/questions/44689486/how-to-use-aws-batch-in-java-classlambda-function-to-submit-batch-job
//        SubmitJobRequest request = new SubmitJobRequest().withJobName("some-name")
//                .withJobQueue("job-queue-name")
//                .withJobDefinition("job-definition-name-with-revision-number:1");
//        SubmitJobResult response = client.submitJob(request); }
    }





}
