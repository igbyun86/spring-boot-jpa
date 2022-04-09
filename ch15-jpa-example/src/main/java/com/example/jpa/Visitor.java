package com.example.jpa;

import com.example.jpa.entity.item.Album;
import com.example.jpa.entity.item.Book;
import com.example.jpa.entity.item.Movie;

public interface Visitor {

    void visit(Book book);

    void visit(Album album);

    void visit(Movie movie);
}
