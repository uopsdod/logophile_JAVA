package com.model.mem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public interface MemRepository extends JpaRepository<Mem, Long> {

    List<Mem> findByMemName(String memName);
    List<Mem> findByMemAccount(String memAccount);

    @Query("select m from Mem m where m.memName = :memName")
    Stream<Mem> findByEmailReturnStream(@Param("memName") String memName);

    List<Mem> findByDate(Date date);

    //@Query("select c from Mem c")
    //Stream<Customer> findAllAndStream();

    //List<Customer> findByDateBetween(Date from, Date to);

}
