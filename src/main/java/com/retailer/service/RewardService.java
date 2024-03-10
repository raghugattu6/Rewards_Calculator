package com.retailer.service;

import com.retailer.model.CustomerRewards;

public interface RewardService {
	 public CustomerRewards getRewardsByCustomerId(Long customerId);
}
