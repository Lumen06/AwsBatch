import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.*;

public class IamManager  {


    private static AmazonIdentityManagement IamClient = AmazonIdentityManagementClientBuilder.standard().build();
    private static AccessKeyMetadata data = new AccessKeyMetadata();

    public static final DeleteUserResult deleteUser(String username) {

        DeleteUserRequest request = new DeleteUserRequest().withUserName(username);

        return IamClient.deleteUser(request);
    }

    public static final DeleteAccessKeyResult deleteAccessKeyByUser(String username, String accessKeyId) {

        DeleteAccessKeyRequest request = new DeleteAccessKeyRequest().withUserName(username).withAccessKeyId(accessKeyId);

        return IamClient.deleteAccessKey(request);
    }

    public static CreateAccessKeyResult createIam(final String userName) {
        final AmazonIdentityManagement iam =
                AmazonIdentityManagementClientBuilder.defaultClient();

        final CreateAccessKeyRequest iamRequest = new CreateAccessKeyRequest()
                .withUserName(userName);

        return iam.createAccessKey(iamRequest);
    }

    public static CreateUserResult createUser(final String username) {

        final AmazonIdentityManagement iam =
                AmazonIdentityManagementClientBuilder.defaultClient();

        final CreateUserRequest request = new CreateUserRequest()
                .withUserName(username);

        return iam.createUser(request);
    }
}





