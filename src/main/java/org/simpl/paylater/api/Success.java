package org.simpl.paylater.api;

public class Success<T> extends  Response<T> {
    public Success(T data) {
        super(data, Status.Success);
    }
}
