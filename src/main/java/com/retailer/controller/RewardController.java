package com.retailer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.entity.Customer;
import com.retailer.error.CustomerException;
import com.retailer.model.CustomerRewards;
import com.retailer.repository.CustRepository;
import com.retailer.repository.TransactionRepository;
import com.retailer.service.RewardsServiceImpl;

@RestController
@RequestMapping("/customers")
public class RewardController {

@Autowired
RewardsServiceImpl rewardsService;

	@Autowired
	CustRepository customerRepository;

	@Autowired
	TransactionRepository transactionRepository;

	//http://localhost:8080/customers/1001/rewards
	@GetMapping(value = "/{customerId}/rewards", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerRewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId) {

		Customer customer = customerRepository.findByCustomerId(customerId);

		//if customer is not found, gives custom exception
		if (customer == null) {

			throw new CustomerException("Customer not found with ID: " + customerId);

		}

		CustomerRewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);

		return new ResponseEntity<>(customerRewards, HttpStatus.OK);
	}

}
