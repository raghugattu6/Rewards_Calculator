package com.retailer.repository;

import org.springframework.data.repository.CrudRepository;

import com.retailer.entity.Customer;

public interface CustRepository extends CrudRepository<Customer,Long>{
	
	public Customer findByCustomerId(Long customerId);

}
