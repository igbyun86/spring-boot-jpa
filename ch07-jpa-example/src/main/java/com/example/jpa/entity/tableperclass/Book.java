package com.example.jpa.entity.tableperclass;

import com.example.jpa.entity.singletable.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
public class Book extends Item {
}
