package com.dos.md.util.osdesignpattern;

/**
 * Created by DOS on 2016/7/15.
 */
public interface Subject {//Observed
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void removeAll();

    void notifyAllObserver(Object data);

    void notify(Observer observer, Object data);

}
