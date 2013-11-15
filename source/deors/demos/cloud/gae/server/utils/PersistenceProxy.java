package deors.demos.cloud.gae.server.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class PersistenceProxy {

    private static final EntityManagerFactory factory =
        Persistence.createEntityManagerFactory("transactions-optional");

    private PersistenceProxy() {
        super();
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}
