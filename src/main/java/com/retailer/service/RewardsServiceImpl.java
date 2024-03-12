package com.retailer.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.entity.Transaction;
import com.retailer.model.CustomerRewards;
import com.retailer.repository.TransactionRepository;
import com.retailer.rewardconstants.Constants;

@Service
public class RewardsServiceImpl implements RewardService{

@Autowired
TransactionRepository transactionRepository;

	public CustomerRewards getRewardsByCustomerId(Long customerId) {

		
		//calculating time stamps for last three months
		Timestamp lastMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusDays(Constants.daysInMonths));
		Timestamp lastSecondMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusDays(2 * Constants.daysInMonths));
		Timestamp lastThirdMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusDays(3 * Constants.daysInMonths));
        
		//checking the specific date ranges for last three months on the transaction table
		List<Transaction> lastMonthTransactions = transactionRepository.findAllByCustomerIdAndTransactionDateBetween(
				customerId,lastMonthTimestamp ,Timestamp.from(Instant.now()) );
		
		System.out.println("TimestampNow: " + Timestamp.from(Instant.now()));
		
		List<Transaction> lastSecondMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
		List<Transaction> lastThirdMonthTransactions = transactionRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,
						lastSecondMonthTimestamp);

		//calling the getRewardPoints method to calculate transaction received from transaction table
		Long lastMonthRewardPoints = getRewardsPerMonth(lastMonthTransactions);
		Long lastSecondMonthRewardPoints = getRewardsPerMonth(lastSecondMonthTransactions);
		Long lastThirdMonthRewardPoints = getRewardsPerMonth(lastThirdMonthTransactions);

		CustomerRewards customerRewards = new CustomerRewards();
		customerRewards.setCustomerId(customerId);
		customerRewards.setLastMonthRewardPoints(lastMonthRewardPoints);
		customerRewards.setLastSecondMonthRewardPoints(lastSecondMonthRewardPoints);
		customerRewards.setLastThirdMonthRewardPoints(lastThirdMonthRewardPoints);
		customerRewards
				.setTotalRewards(lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints);

		return customerRewards;

	}

	//calucating rewards for each transactions
	private Long getRewardsPerMonth(List<Transaction> transactions) {
		return transactions.stream().map(transaction -> calculateRewards(transaction))
				.collect(Collectors.summingLong(r -> r.longValue()));
	}

	//Applying logic
	private Long calculateRewards(Transaction t) {
		if (t.getTransactionAmount() > Constants.firstRewardLimit
				&& t.getTransactionAmount() <= Constants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.firstRewardLimit);
		} else if (t.getTransactionAmount() > Constants.secondRewardLimit) {
			return Math.round(t.getTransactionAmount() - Constants.secondRewardLimit) * 2
					+ (Constants.secondRewardLimit - Constants.firstRewardLimit);
		} else
			return 0l;

	}

}
