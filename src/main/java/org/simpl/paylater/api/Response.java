package org.simpl.paylater.api;

public abstract class Response<T> {
    private final T data;
    private final Status status;

    public Response(T data, Status status) {
        this.data = data;
        this.status = status;
    }

    public enum Status {
        Failure, Success
    }

    public T getData() {
        return data;
    }

    public Status getStatus() {
        return status;
    }
}
