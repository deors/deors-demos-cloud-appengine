package deors.demos.cloud.gae.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import deors.demos.cloud.gae.shared.AuthInfo;

public interface AuthInfoServiceAsync {

    void getAuthInfo(String backUrl, AsyncCallback<AuthInfo> callback);
}
