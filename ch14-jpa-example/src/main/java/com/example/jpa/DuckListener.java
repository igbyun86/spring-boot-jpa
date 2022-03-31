package com.example.jpa;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

public class DuckListener {

    @PrePersist
    private void prePersist(Object obj) {
        System.out.println("DuckListener.prePersist obj = [" + obj + "]");  //obj = [Duck(id=null, name=오리)]
    }

    @PostPersist
    private void postPersist(Object obj) {
        System.out.println("DuckListener.postPersist obj = [" + obj + "]");
    }

}
