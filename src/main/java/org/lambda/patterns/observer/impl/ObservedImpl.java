package org.lambda.patterns.observer.impl;

import org.lambda.patterns.observer.Observed;
import org.lambda.patterns.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ObservedImpl<T> implements Observed<T> {

    List<T> events = new ArrayList<>();
    List<Observer<T>> observers = new ArrayList<>();

    public void addEvent(T event) throws ExecutionException, InterruptedException {
        this.events.add(event);
        this.notifyObservers();
    }

    public void addAll(List<T> events) throws ExecutionException, InterruptedException {
        this.events.addAll(events);
        this.notifyObservers();
    }

    public void removeEvent(T event) throws ExecutionException, InterruptedException {
        this.events.remove(event);
        this.notifyObservers();
    }

    public void removeAll() {
        this.events.clear();
    }

    @Override
    public void addObserver(Observer<T> observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<T> observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() throws ExecutionException, InterruptedException {
        for(final Observer<T> observer : this.observers) {
            final List<T> data = new ArrayList<>(this.events);
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                observer.handleEvent(data);
            });
            future.get();
        }
    }
}
