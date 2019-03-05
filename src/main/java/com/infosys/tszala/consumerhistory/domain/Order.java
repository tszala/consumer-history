package com.infosys.tszala.consumerhistory.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_date")
    @NotNull
    private Date orderDate;

    @Column(name="order_value")
    @NotNull
    private BigDecimal orderValue;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="consumer_id")
    private Consumer consumer;
}
