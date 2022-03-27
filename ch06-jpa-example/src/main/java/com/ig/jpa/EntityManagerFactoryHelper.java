package com.ig.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryHelper {

    private EntityManagerFactoryHelper() {
    }

    private static class SingletonHelper {
        private static final EntityManagerFactory INSTANCE = Persistence.createEntityManagerFactory("");
    }
}
