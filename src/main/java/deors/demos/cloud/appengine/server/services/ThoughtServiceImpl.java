package deors.demos.cloud.appengine.server.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import deors.demos.cloud.appengine.client.services.ThoughtService;
import deors.demos.cloud.appengine.shared.Thought;

public class ThoughtServiceImpl extends RemoteServiceServlet implements ThoughtService {

    private static final long serialVersionUID = -7673104462148507681L;

    @Override
    public List<Thought> getThoughts() {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query("Thought").addSort("postDate");

        List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        List<Thought> list = new ArrayList<>();

        for (Entity persistedThought : results) {
            Thought thought = new Thought(
                (String) persistedThought.getProperty("poster"),
                (String) persistedThought.getProperty("target"),
                (String) persistedThought.getProperty("message"),
                (Date) persistedThought.getProperty("postDate")
            );
            list.add(thought);
        }

        return list;
    }

    @Override
    public List<Thought> getThoughtsAfterDate(Date threshold) {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Filter filter = new FilterPredicate("postDate", FilterOperator.GREATER_THAN, threshold);
        Query query = new Query("Thought").addSort("postDate").setFilter(filter);

        List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        List<Thought> list = new ArrayList<>();

        for (Entity persistedThought : results) {
            Thought thought = new Thought(
                (String) persistedThought.getProperty("poster"),
                (String) persistedThought.getProperty("target"),
                (String) persistedThought.getProperty("message"),
                (Date) persistedThought.getProperty("postDate")
            );
            list.add(thought);
        }

        return list;
    }

    @Override
    public List<Thought> postThought(Thought thought, Date latest) {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // to return also thoughts posted by other clients
        // since the last update to the client posting this thought
        List<Thought> list = getThoughtsAfterDate(latest);

        Entity thoughtToPersist = new Entity("Thought");
        thoughtToPersist.setProperty("message", thought.getMessage());
        thoughtToPersist.setProperty("postDate", thought.getPostDate());
        thoughtToPersist.setProperty("poster", thought.getPoster());
        thoughtToPersist.setProperty("target", thought.getTarget());

        datastore.put(thoughtToPersist);

        list.add(thought);

        return list;
    }
}
