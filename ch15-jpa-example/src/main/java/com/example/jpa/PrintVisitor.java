package com.example.jpa;

import com.example.jpa.entity.item.Album;
import com.example.jpa.entity.item.Book;
import com.example.jpa.entity.item.Movie;

public class PrintVisitor implements Visitor {

    @Override
    public void visit(Book book) {
        //넘어오는 book은 proxy가 아닌 원본 엔티티다.
        System.out.println("book.class = " + book.getClass());
        System.out.println("[PrintVisitor][제목:" + book.getName() + " 저자:" + book.getAuthor() + "]");
    }

    @Override
    public void visit(Album album) {
        System.out.println("album.class = " + album.getClass());
        System.out.println("[PrintVisitor][제목:" + album.getName() + " 작가:" + album.getArtist() + "]");
    }

    @Override
    public void visit(Movie movie) {
        System.out.println("movie.class = " + movie.getClass());
        System.out.println("[PrintVisitor][제목:" + movie.getName() + " 배우:" + movie.getActor() + "]");
    }
}
