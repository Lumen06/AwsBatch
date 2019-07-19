import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {


        AmazonIdentityManagement iam =
                AmazonIdentityManagementClientBuilder.defaultClient();

        System.out.println(iam.listUsers());

        iam.listUsers().getUsers().forEach(user -> {
            listAccessKeys(iam, user).forEach(key -> IamManager.deleteAccessKeyByUser(user.getUserName(), key.getAccessKeyId()));
            IamManager.deleteUser(user.getUserName());
        });

        /*IamManager.deleteAccessKeyByUser("awesome_user1");
        IamManager.deleteUser("awesome_user1");

        IamManager.deleteAccessKeyByUser("awesome_user2");
        IamManager.deleteUser("awesome_user2");*/

    }

    private static Stream<AccessKeyMetadata> listAccessKeys(AmazonIdentityManagement iam, User user) {
        boolean done = false;
        ListAccessKeysRequest request = new ListAccessKeysRequest().withUserName(user.getUserName());
        List<AccessKeyMetadata> keys = new ArrayList<>();

        while (!done) {

            ListAccessKeysResult response = iam.listAccessKeys(request);
            keys.addAll(response.getAccessKeyMetadata());
            request.setMarker(response.getMarker());

            if (!response.getIsTruncated()) {
                done = true;
            }
        }
        return keys.stream();
    }
}
