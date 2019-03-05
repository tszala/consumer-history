package com.infosys.tszala.consumerhistory.service;

import com.infosys.tszala.consumerhistory.domain.Consumer;
import com.infosys.tszala.consumerhistory.domain.Order;
import com.infosys.tszala.consumerhistory.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {

    private static Logger LOG = LoggerFactory.getLogger(OrderService.class);

    private OrderRepository orderRepo;

    public List<Order> ordersForConsumer(Consumer consumer) {
        return orderRepo.findByConsumer(consumer);
    }

    public BigDecimal getHighestOrderValueForConsumer(Consumer consumer) {

        List<Order> orders = ordersForConsumer(consumer);

        return CollectionUtils.isEmpty(orders) ? BigDecimal.ZERO : getHighestOrderValue(orders);
    }

    private BigDecimal getHighestOrderValue(List<Order> orders) {

        Optional<Order> highestOrder = orders.stream().max(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getOrderValue().compareTo(o2.getOrderValue());
            }
        });

        return highestOrder.isPresent() ? highestOrder.get().getOrderValue() : BigDecimal.ZERO;
    }

    public BigDecimal getAverageOrderValueForConsumer(Consumer consumer) {
        List<Order> orders = ordersForConsumer(consumer);

        return CollectionUtils.isEmpty(orders) ? BigDecimal.ZERO : getAverageOrderValue(orders);
    }

    private BigDecimal getAverageOrderValue(List<Order> orders) {
        BigDecimal allOrdersSum = orders.stream()
                .map(o -> o.getOrderValue())
                .reduce(BigDecimal.ZERO, (v1, v2) -> v1.add(v2));
        return allOrdersSum.divide(BigDecimal.valueOf(orders.size()));
    }

    public Collection<Order> getOrdersAfterDate(Consumer consumer, LocalDate past) {

        Date pastDate = Date.from(past.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LOG.info("Retrieving orders since {}", pastDate.toString());

        List<Order> orders = orderRepo.findByConsumerAndOrderDateAfter(consumer, pastDate);

        return orders;
    }
}
