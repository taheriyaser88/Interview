package com.lobox.interview.dto;

import com.lobox.interview.model.Actor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDTO {

    private Long id;
    private String title;
    private String director;
    private String writer;
    private List<Actor> actors;
    private String genre;
    private int year;
    private int votes;
    private double rating;
    private boolean isAlive;

}
