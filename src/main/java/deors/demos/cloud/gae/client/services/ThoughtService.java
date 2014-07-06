package deors.demos.cloud.gae.client.services;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import deors.demos.cloud.gae.shared.Thought;

@RemoteServiceRelativePath("ThoughtService")
public interface ThoughtService extends RemoteService {

    List<Thought> getThoughts();
    List<Thought> getThoughtsByDate(Date d);
    List<Thought> postThought(Thought th, Date latest);
}
