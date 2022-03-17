package com.ig.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryHelper {

    private static final String PERSISTENCE_UNIT_NAME = "jpabook";

    private EntityManagerFactoryHelper() {
    }

    private static class SingletonHelper {
        private static final EntityManagerFactory INSTANCE = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static EntityManagerFactory getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
