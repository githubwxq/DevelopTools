package com.example.design_pattern.abstrctfactory.simple_factory;

public class Factory {
    public static Api create(int type){
        switch (type){
            case 1:
                return new ImplA();
            case 2:
                return new ImplB();
            default:
                return new ImplA();
        }
    }
}




