package com.infosys.tszala.consumerhistory.service;

import com.infosys.tszala.consumerhistory.ConsumerNotFoundException;
import com.infosys.tszala.consumerhistory.domain.Consumer;
import com.infosys.tszala.consumerhistory.domain.Order;
import com.infosys.tszala.consumerhistory.repository.ConsumerRepository;
import static org.assertj.core.api.Assertions.*;

import org.apache.tomcat.jni.Local;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerServiceTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @MockBean
    private OrderService orderService;

    @MockBean
    private ConsumerRepository consumerRepository;

    @Autowired
    private ConsumerService consumerService;

    @Before
    public void doBefore() {
        Mockito.reset(orderService, consumerRepository);
    }

    @Test
    public void shouldThrowConsumerNotFoundWhenNoConsumerForId() {
        //given
        Long consumerId = 10L;
        thrown.expect(ConsumerNotFoundException.class);
        thrown.expectMessage(String.format("Consumer with id %d not found", consumerId));

        //when
        List<Order> orders = consumerService.getAllOrdersForConsumer(consumerId);
        //then
        //rule is applied
    }

    @Test
    public void shouldCallOrderServiceWithConsumerIdForGetAllOrders() {
        //given
        Long consumerId = 10L;
        Consumer consumer = new Consumer();
        consumer.setId(consumerId);
        Mockito.when(orderService.ordersForConsumer(consumer)).thenReturn(Collections.emptyList());
        Mockito.when(consumerRepository.findById(consumerId)).thenReturn(Optional.of(consumer));
        //when
        List<Order> orders = consumerService.getAllOrdersForConsumer(consumerId);
        //then
        Mockito.verify(orderService).ordersForConsumer(consumer);
        assertThat(orders).isEmpty();
    }

    @Test
    public void shouldCallOrderServiceWithConsumerIdForGetHighestOrderValue() {
        //given
        Long consumerId = 10L;
        Consumer consumer = new Consumer();
        consumer.setId(consumerId);
        Mockito.when(consumerRepository.findById(consumerId)).thenReturn(Optional.of(consumer));
        Mockito.when(orderService.getHighestOrderValueForConsumer(consumer)).thenReturn(BigDecimal.TEN);
        //when
        BigDecimal highestOrderValue = consumerService.getHighestOrderValueForConsumer(consumerId);
        //then
        Mockito.verify(orderService).getHighestOrderValueForConsumer(consumer);
        assertThat(highestOrderValue).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void shouldCallOrderServiceWithConsumerIdForGetAverageOrderValue() {
        //given
        Long consumerId = 10L;
        Consumer consumer = new Consumer();
        consumer.setId(consumerId);
        Mockito.when(consumerRepository.findById(consumerId)).thenReturn(Optional.of(consumer));
        Mockito.when(orderService.getAverageOrderValueForConsumer(consumer)).thenReturn(BigDecimal.ONE);
        //when
        BigDecimal averageOrderValue = consumerService.getAverageOrderValueForConsumer(consumerId);
        //then
        Mockito.verify(orderService).getAverageOrderValueForConsumer(consumer);
        assertThat(averageOrderValue).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void shouldReturnZeroForGetOrderCountForLastMonthsIfNoOrders() {
        //given
        Long consumerId = 10L;
        Consumer consumer = new Consumer();
        consumer.setId(consumerId);
        LocalDate today = LocalDate.now();
        Mockito.when(consumerRepository.findById(consumerId)).thenReturn(Optional.of(consumer));
        Mockito.when(orderService.getOrdersAfterDate(consumer, today)).thenReturn(Collections.emptyList());
        //when
        Long orderCount = consumerService.getOrderCountForLastMonths(consumerId, 3);
        //then
        Mockito.verify(orderService).getOrdersAfterDate(consumer, today.minusMonths(3));
        assertThat(orderCount).isEqualTo(Long.valueOf(0));
    }
}
