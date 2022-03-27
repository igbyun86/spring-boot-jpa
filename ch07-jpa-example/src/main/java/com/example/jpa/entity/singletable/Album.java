package com.example.jpa.entity.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Album extends Item {
}
