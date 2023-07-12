package com.springbatchexample.springbatchexample.config;

import org.springframework.batch.item.ItemProcessor;

import com.springbatchexample.springbatchexample.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer>{

	@Override
	public Customer process(Customer item) throws Exception {
		return item;
	}

}
