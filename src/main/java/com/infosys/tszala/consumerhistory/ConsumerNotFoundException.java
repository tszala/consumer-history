package com.infosys.tszala.consumerhistory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ConsumerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -779041494072937362L;

    public ConsumerNotFoundException(Long id) {
        super(String.format("Consumer with id %d not found", id));
    }
}
