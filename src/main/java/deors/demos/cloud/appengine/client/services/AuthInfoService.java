package deors.demos.cloud.appengine.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import deors.demos.cloud.appengine.shared.AuthInfo;

@RemoteServiceRelativePath("AuthInfoService")
public interface AuthInfoService extends RemoteService {

    AuthInfo getAuthInfo(String backUrl);
}
