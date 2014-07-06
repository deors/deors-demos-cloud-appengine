package deors.demos.cloud.gae.server.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import deors.demos.cloud.gae.client.services.ThoughtService;
import deors.demos.cloud.gae.server.utils.PersistenceProxy;
import deors.demos.cloud.gae.shared.Thought;

public class ThoughtServiceImpl extends RemoteServiceServlet implements ThoughtService {

    private static final long serialVersionUID = -7673104462148507681L;

    @SuppressWarnings("unchecked")
    @Override
    public List<Thought> getThoughts() {

        EntityManager em = PersistenceProxy.getFactory().createEntityManager();
        try {
            Query q = em.createQuery("SELECT t FROM Thought t ORDER BY t.postDate");
            List<Thought> result = (List<Thought>) q.getResultList();
            List<Thought> list = new ArrayList<Thought>();
            for (Thought t : result) {
                list.add(t);
            }
            return list;
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Thought> getThoughtsByDate(Date d) {

        EntityManager em = PersistenceProxy.getFactory().createEntityManager();
        try {
            Query q = em.createQuery("SELECT t FROM Thought t WHERE postDate > :d ORDER BY t.postDate");
            q.setParameter("d", d);
            List<Thought> result = (List<Thought>) q.getResultList();
            List<Thought> list = new ArrayList<Thought>();
            for (Thought t : result) {
                list.add(t);
            }
            return list;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Thought> postThought(Thought th, Date latest) {

        List<Thought> list = getThoughtsByDate(latest);

        EntityManager em = PersistenceProxy.getFactory().createEntityManager();
        try {
            em.persist(th);
            list.add(th);
            return list;
        } finally {
            em.close();
        }
    }
}
