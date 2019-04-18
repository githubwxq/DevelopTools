package com.example.design_pattern.observer.simple_one;



/**
 * Created by Mjj on 2017/10/6.
 */

public interface Observerable {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}

