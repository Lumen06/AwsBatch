import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.*;

public class IamManager  {


    private static AmazonIdentityManagement IamClient = AmazonIdentityManagementClientBuilder.standard().build();
    private static AccessKeyMetadata data = new AccessKeyMetadata();

    public static final String ALLOW_EVERYTHING_POLICY_DOCUMENT =
 /*           "{" +
            "  \"Version\": \"2012-10-17\"," +
            "  \"Statement\": [" +
            "    {" +
            "        \"Effect\": \"Allow\"," +
            "        \"Action\": \"logs:CreateLogGroup\"," +
            "        \"Resource\": \"*\"" +
            "    }," +
            "    {" +
            "        \"Effect\": \"Allow\"," +
            "        \"Action\": [" +
            "            \"dynamodb:DeleteItem\"," +
            "            \"dynamodb:GetItem\"," +
            "            \"dynamodb:PutItem\"," +
            "            \"dynamodb:Scan\"," +
            "            \"dynamodb:UpdateItem\"" +
            "       ]," +
            "       \"Resource\": \"*\"" +
            "    }" +
            "   ]" +
            "}";*/
            "{" +
                    "  \"Version\": \"2012-10-17\"," +
                    "  \"Statement\": [" +
                    "    {" +
                    "        \"Sid\": \"\"," +
                    "        \"Effect\": \"Allow\"," +
                    "        \"Action\": [ \"iam:AttachRolePolicy\", \"iam:CreateRole\", \"iam:PutRolePolicy\" ]" +
                    "        \"Resource\": \"*\"" +
                    "    }" +
                    "   ]" +
                    "}";

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

    public static Policy createPolicy(final String roleName, final String document) {
        final AmazonIdentityManagement iam =
                AmazonIdentityManagementClientBuilder.defaultClient();

        final CreatePolicyRequest request = new CreatePolicyRequest()
                .withPolicyName(roleName)
                .withPolicyDocument(document);

        return iam.createPolicy(request).getPolicy();
    }

    public static Role createRole(final String roleName, final String document) {
        final AmazonIdentityManagement iam =
                AmazonIdentityManagementClientBuilder.defaultClient();

        final CreateRoleRequest request = new CreateRoleRequest()
                .withRoleName(roleName)
                .withAssumeRolePolicyDocument(document);

        return iam.createRole(request).getRole();
    }

    public static Role createRoleWithAttachedPolicy(final String roleName, Policy policy) {
        final AmazonIdentityManagement iam =
                AmazonIdentityManagementClientBuilder.defaultClient();

        AttachRolePolicyRequest request = new AttachRolePolicyRequest().withRoleName(policy.getPolicyName()).withPolicyArn(policy.getArn());
        AttachRolePolicyResult response = iam.attachRolePolicy(request);

        return ;
    }
}





