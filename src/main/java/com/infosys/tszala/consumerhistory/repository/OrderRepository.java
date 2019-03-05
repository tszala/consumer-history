package com.infosys.tszala.consumerhistory.repository;

import com.infosys.tszala.consumerhistory.domain.Consumer;
import com.infosys.tszala.consumerhistory.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByConsumer(Consumer consumer);

    List<Order> findByConsumerAndOrderDateAfter(Consumer consumer, Date orderDate);
}
