package com.infosys.tszala.consumerhistory.repository;

import com.infosys.tszala.consumerhistory.domain.Consumer;
import org.springframework.data.repository.CrudRepository;

public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
}
