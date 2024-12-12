package com.myimages.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class SubscriptionDTO {

	private Integer subscriptionId;

	private Integer userProfileId;

	private String subscriptionType;

	private Date startDate;






}
