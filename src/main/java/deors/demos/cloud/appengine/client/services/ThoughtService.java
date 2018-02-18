package deors.demos.cloud.appengine.client.services;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import deors.demos.cloud.appengine.shared.Thought;

@RemoteServiceRelativePath("ThoughtService")
public interface ThoughtService extends RemoteService {

    List<Thought> getThoughts();
    List<Thought> getThoughtsAfterDate(Date threshold);
    List<Thought> postThought(Thought thought, Date latest);
}
