package com.example.jpa.entity.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
public class Movie extends Item {
}
