package org.lambda.patterns.observer.impl;

import org.lambda.patterns.observer.Observer;

import java.util.List;

public record ObserverImpl<T>(String name) implements Observer<T> {

    @Override
    public void handleEvent(List<T> events) {
        System.out.printf("we have events: %s\n", events);
    }
}
