package com.dos.md.util.osdesignpattern;

/**
 * Created by DOS on 2016/7/15.
 */
public class UseTheOS {
    private SomeSubject mSomeSubject;
    private Observer mSomeObserver, mSomeObserver1;

    public void main() {
        mSomeSubject = new SomeSubject();
        mSomeObserver = new SomeObserver();
        mSomeObserver1 = new SomeObserver1();

        mSomeSubject.addObservers(mSomeObserver, mSomeObserver1);
        mSomeSubject.notifyAllObserver("data");
        mSomeSubject.notify(mSomeObserver, "someData");
    }
}
