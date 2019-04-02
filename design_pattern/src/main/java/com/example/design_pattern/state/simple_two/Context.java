package com.example.design_pattern.state.simple_two;

public class Context {
        private PersonState mPersonState;

        public void setPersonState(PersonState personState) {
            mPersonState = personState;
        }

        public void fallInLove() {
            System.out.println("fallInLove state");
            setPersonState(new LoveState());
        }

        public void disappointmentInLove() {
            System.out.println("disappointmentInLove  i am  single dog");
            setPersonState(new DogState());
        }

        public void movies() {
            mPersonState.movies();
        }

        public void shopping() {
            mPersonState.shopping();
        }
    }