package com.example.jpa.entity.compositekey.identifiability.embeded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChildId implements Serializable {

    public String parentId;       //@MapsId("parentid")로 매핑

    @Column(name = "CHILD_ID")
    private String childId;     //Child.childId 매핑

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildId childId1 = (ChildId) o;
        return Objects.equals(parentId, childId1.parentId) && Objects.equals(childId, childId1.childId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentId, childId);
    }
}
