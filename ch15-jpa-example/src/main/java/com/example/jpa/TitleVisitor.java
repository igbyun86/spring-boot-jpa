package com.example.jpa;

import com.example.jpa.entity.item.Album;
import com.example.jpa.entity.item.Book;
import com.example.jpa.entity.item.Movie;

public class TitleVisitor implements Visitor{

    private String title;

    public String getTitle() {
        return title;
    }

    @Override
    public void visit(Book book) {
        System.out.println("[제목:" + book.getName() + " 저자:" + book.getAuthor() + "]");
    }

    @Override
    public void visit(Album album) {
        System.out.println("[제목:" + album.getName() + " 작가:" + album.getArtist() + "]");
    }

    @Override
    public void visit(Movie movie) {
        System.out.println("[제목:" + movie.getName() + " 배우:" + movie.getActor() + "]");
    }
}
