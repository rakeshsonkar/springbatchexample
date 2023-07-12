package com.springbatchexample.springbatchexample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbatchexample.springbatchexample.entity.Customer;
@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {

}
