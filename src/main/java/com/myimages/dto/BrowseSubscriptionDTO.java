package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseSubscriptionDTO {

	private Integer ownerId;

	private Integer subscriptionId;

	private Integer subscriptionStatus;
	
	private Integer nextOrPrevious;
}

