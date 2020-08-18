package org.simpl.paylater;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/paylater")
public class PingController {

    /**
     * curl --location --request GET 'localhost:3030/ping'
     * @return
     */
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
