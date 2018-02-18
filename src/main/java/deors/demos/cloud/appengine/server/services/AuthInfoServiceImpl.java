package deors.demos.cloud.appengine.server.services;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import deors.demos.cloud.appengine.client.services.AuthInfoService;
import deors.demos.cloud.appengine.shared.AuthInfo;

public class AuthInfoServiceImpl extends RemoteServiceServlet implements AuthInfoService {

    private static final long serialVersionUID = 7418214164300791208L;

    @Override
    public AuthInfo getAuthInfo(String backUrl) {

        AuthInfo authInfo = new AuthInfo();

        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        if (userService.isUserLoggedIn()) {

            authInfo.setLogged(true);
            authInfo.setNick(user.getNickname());
            authInfo.setEmail(user.getEmail());
        }

        authInfo.setLoginUrl(userService.createLoginURL(backUrl));
        authInfo.setLogoutUrl(userService.createLogoutURL(backUrl));

        return authInfo;
    }
}
