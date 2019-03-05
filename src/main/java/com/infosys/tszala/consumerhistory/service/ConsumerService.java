package com.infosys.tszala.consumerhistory.service;

import com.infosys.tszala.consumerhistory.ConsumerNotFoundException;
import com.infosys.tszala.consumerhistory.domain.Consumer;
import com.infosys.tszala.consumerhistory.domain.Order;
import com.infosys.tszala.consumerhistory.repository.ConsumerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsumerService {

    private ConsumerRepository consumerRepo;

    private OrderService orderService;

    public List<Consumer> findAll() {
        return (List<Consumer>)consumerRepo.findAll();
    }

    public List<Order> getAllOrdersForConsumer(Long consumerId) {

        Consumer consumer = getConsumer(consumerId);

        return orderService.ordersForConsumer(consumer);
    }

    public BigDecimal getHighestOrderValueForConsumer(Long consumerId) {

        Consumer consumer = getConsumer(consumerId);

        return orderService.getHighestOrderValueForConsumer(consumer);
    }

    public BigDecimal getAverageOrderValueForConsumer(Long consumerId) {

        Consumer consumer = getConsumer(consumerId);

        return orderService.getAverageOrderValueForConsumer(consumer);
    }

    private Consumer getConsumer(Long consumerId) {
        return consumerRepo.findById(consumerId).orElseThrow(() -> new ConsumerNotFoundException(consumerId));
    }

    public Long getOrderCountForLastMonths(Long consumerId, int numberOfMonths) {
        Consumer consumer = getConsumer(consumerId);

        LocalDate today = LocalDate.now();
        LocalDate past = today.minusMonths(numberOfMonths);

        return Long.valueOf(orderService.getOrdersAfterDate(consumer, past).size());
    }
}
