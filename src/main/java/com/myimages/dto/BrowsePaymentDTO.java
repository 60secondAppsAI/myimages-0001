package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePaymentDTO {

	private Integer ownerId;

	private Integer paymentId;

	private Integer paymentStatus;
	
	private Integer nextOrPrevious;
}

