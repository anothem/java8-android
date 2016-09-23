package org.florescu.java8examples;


public interface FilterCondition<T> {
    boolean accept(T candidate);
}
