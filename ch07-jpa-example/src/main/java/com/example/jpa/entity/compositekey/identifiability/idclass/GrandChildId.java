package com.example.jpa.entity.compositekey.identifiability.idclass;

import java.io.Serializable;
import java.util.Objects;

public class GrandChildId implements Serializable {

    private ChildId childId;    //GrandChildId.child 매핑

    private String id;          //GrandChild.id 매핑

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrandChildId that = (GrandChildId) o;
        return Objects.equals(childId, that.childId) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(childId, id);
    }
}
