package deors.demos.cloud.gae.client.services;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import deors.demos.cloud.gae.shared.Thought;

public interface ThoughtServiceAsync {

    void getThoughts(AsyncCallback<List<Thought>> callback);

    void getThoughtsByDate(Date d, AsyncCallback<List<Thought>> callback);

    void postThought(Thought th, Date latest, AsyncCallback<List<Thought>> callback);

}
