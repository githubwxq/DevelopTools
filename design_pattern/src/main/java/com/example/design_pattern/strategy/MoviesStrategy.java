package com.example.design_pattern.strategy;

public class MoviesStrategy implements ChaseStragety {

        @Override
        public void chase() {
            System.out.println("一起看电影咯~");
        }
    }