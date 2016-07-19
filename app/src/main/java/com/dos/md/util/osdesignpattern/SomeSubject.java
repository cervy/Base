package com.dos.md.util.osdesignpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOS on 2016/7/15.
 */
public class SomeSubject implements Subject {
    private List<Observer> mList = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        if (observer == null) throw new NullPointerException("Observer==null");
        if (!mList.contains(observer)) mList.add(observer);
    }

    public void addObservers(Observer... observer) {
        for (Observer someObserver : observer) {
            addObserver(someObserver);
        }

    }

    @Override
    public void removeObserver(Observer observer) {
        mList.remove(observer);
    }

    @Override
    public void removeAll() {
        mList.clear();
    }

    @Override
    public void notifyAllObserver(Object data) {
        for (Observer observer : mList) observer.update(this, data);
    }

    @Override
    public void notify(Observer observer, Object data) {
        if (observer != null) observer.update(this, data);
    }
}
