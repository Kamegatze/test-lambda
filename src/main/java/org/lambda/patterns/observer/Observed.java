package org.lambda.patterns.observer;

import java.util.concurrent.ExecutionException;

public interface Observed<T> {
    void addObserver(Observer<T> observer);
    void removeObserver(Observer<T> observer);
    void notifyObservers() throws ExecutionException, InterruptedException;
}
