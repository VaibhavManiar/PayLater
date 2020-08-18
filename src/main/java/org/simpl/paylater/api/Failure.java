package org.simpl.paylater.api;

public class Failure extends Response<String> {

    private final Error error;
    public Failure(Error error) {
        super("", Status.Failure);
        this.error = error;
    }

    public static class Error {
        private String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public Error getError() {
        return error;
    }
}
