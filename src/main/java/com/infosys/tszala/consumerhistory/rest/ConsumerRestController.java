package com.infosys.tszala.consumerhistory.rest;

import com.infosys.tszala.consumerhistory.domain.Consumer;
import com.infosys.tszala.consumerhistory.domain.Order;
import com.infosys.tszala.consumerhistory.service.ConsumerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/consumers")
@AllArgsConstructor
public class ConsumerRestController {

    private ConsumerService consumerService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Consumer> findAll() {
        return consumerService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAllOrders(@PathVariable Long id) {
        return consumerService.getAllOrdersForConsumer(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/order-count-in-period", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long getOrderCountForLastThreeMonths(@PathVariable Long id) {
        return consumerService.getOrderCountForLastMonths(id, 3);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/average-order-value", produces = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal getAverageOrderValue(@PathVariable Long id) {
        return consumerService.getAverageOrderValueForConsumer(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/highest-order-value", produces = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal getHighestOrderValue(@PathVariable Long id) {
        return consumerService.getHighestOrderValueForConsumer(id);
    }

}
