package org.lambda.patterns.observer;


import java.util.List;

public interface Observer<T> {
    void handleEvent(List<T> data);
}
