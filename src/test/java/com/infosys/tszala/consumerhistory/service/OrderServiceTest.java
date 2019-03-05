package com.infosys.tszala.consumerhistory.service;


import com.infosys.tszala.consumerhistory.domain.Consumer;
import com.infosys.tszala.consumerhistory.domain.Order;
import com.infosys.tszala.consumerhistory.repository.OrderRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Before
    public void doBefore() {
        Mockito.reset(orderRepository);
    }

    @Test
    public void shouldInvokeRepositoryOnOrdersForConsumer() {
        //given
        Consumer consumer = new Consumer();
        consumer.setId(1L);
        //when
        List<Order> orders = orderService.ordersForConsumer(consumer);
        //then
        Mockito.verify(orderRepository).findByConsumer(consumer);
        assertThat(orders).isEmpty();
    }

    @Test
    public void shouldReturnZeroForHighestOrdersIfNoOrdersForConsumer() {
        //given
        Consumer consumer = new Consumer();
        consumer.setId(1L);
        Mockito.when(orderRepository.findByConsumer(consumer)).thenReturn(Collections.emptyList());
        //when
        BigDecimal highestOrderValue = orderService.getHighestOrderValueForConsumer(consumer);
        //then
        Mockito.verify(orderRepository).findByConsumer(consumer);
        assertThat(highestOrderValue).isZero();
    }

    @Test
    public void shouldReturnValueFromOrderForHighestValueOrdersIfSingleOrder() {
        //given
        Consumer consumer = new Consumer();
        consumer.setId(1L);
        Order order = new Order();
        order.setConsumer(consumer);
        order.setOrderValue(BigDecimal.TEN);
        List<Order> orders = new ArrayList<Order>();
        orders.add(order);
        Mockito.when(orderRepository.findByConsumer(consumer)).thenReturn(orders);
        //when
        BigDecimal highestOrderValue = orderService.getHighestOrderValueForConsumer(consumer);
        //then
        Mockito.verify(orderRepository).findByConsumer(consumer);
        assertThat(highestOrderValue).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void shouldReturnValueFromOrderForHighestValueOrdersIfMultipleOrders() {
        //given
        Consumer consumer = new Consumer();
        consumer.setId(1L);
        Order order1 = new Order();
        order1.setConsumer(consumer);
        order1.setOrderValue(BigDecimal.TEN);
        List<Order> orders = new ArrayList<Order>();
        orders.add(order1);
        Order order2 = new Order();
        order2.setConsumer(consumer);
        order2.setOrderValue(BigDecimal.ONE);
        orders.add(order2);
        Order order3 = new Order();
        order3.setConsumer(consumer);
        order3.setOrderValue(BigDecimal.TEN.multiply(BigDecimal.TEN));
        orders.add(order3);
        Mockito.when(orderRepository.findByConsumer(consumer)).thenReturn(orders);
        //when
        BigDecimal highestOrderValue = orderService.getHighestOrderValueForConsumer(consumer);
        //then
        Mockito.verify(orderRepository).findByConsumer(consumer);
        assertThat(highestOrderValue).isEqualTo(BigDecimal.TEN.multiply(BigDecimal.TEN));
    }

    @Test
    public void shouldReturnZeroForAverateOrdersIfNoOrdersForConsumer() {
        //given
        Consumer consumer = new Consumer();
        consumer.setId(1L);
        Mockito.when(orderRepository.findByConsumer(consumer)).thenReturn(Collections.emptyList());
        //when
        BigDecimal averageOrderValue = orderService.getAverageOrderValueForConsumer(consumer);
        //then
        Mockito.verify(orderRepository).findByConsumer(consumer);
        assertThat(averageOrderValue).isZero();
    }

    @Test
    public void shouldReturnValueFromOrderForAverageValueOrdersIfSingleOrder() {
        //given
        Consumer consumer = new Consumer();
        consumer.setId(1L);
        Order order = new Order();
        order.setConsumer(consumer);
        order.setOrderValue(BigDecimal.TEN);
        List<Order> orders = new ArrayList<Order>();
        orders.add(order);
        Mockito.when(orderRepository.findByConsumer(consumer)).thenReturn(orders);
        //when
        BigDecimal averageOrderValue = orderService.getAverageOrderValueForConsumer(consumer);
        //then
        Mockito.verify(orderRepository).findByConsumer(consumer);
        assertThat(averageOrderValue).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void shouldReturnValueFromOrderForAverageValueOrdersIfMultipleOrders() {
        //given
        Consumer consumer = new Consumer();
        consumer.setId(1L);
        Order order1 = new Order();
        order1.setConsumer(consumer);
        order1.setOrderValue(BigDecimal.TEN);
        List<Order> orders = new ArrayList<Order>();
        orders.add(order1);
        Order order2 = new Order();
        order2.setConsumer(consumer);
        order2.setOrderValue(BigDecimal.ONE);
        orders.add(order2);
        Order order3 = new Order();
        order3.setConsumer(consumer);
        order3.setOrderValue(BigDecimal.TEN.multiply(BigDecimal.TEN));
        orders.add(order3);
        Mockito.when(orderRepository.findByConsumer(consumer)).thenReturn(orders);
        //when
        BigDecimal averageOrderValue = orderService.getAverageOrderValueForConsumer(consumer);
        //then
        Mockito.verify(orderRepository).findByConsumer(consumer);

        BigDecimal expectedAverage = order1.getOrderValue().add(order2.getOrderValue()).add(order3.getOrderValue()).divide(BigDecimal.valueOf(3L));
        assertThat(averageOrderValue).isEqualTo(expectedAverage);
    }
}
