package org.simpl.paylater;

import org.simpl.paylater.api.Failure;
import org.simpl.paylater.api.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController {

    @ExceptionHandler(Exception.class)
    public Response<String> failure(Exception e) {
        return new Failure(new Failure.Error(e.getMessage()));
    }
}
