package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseLicenseDTO {

	private Integer ownerId;

	private Integer licenseId;

	private Integer licenseStatus;
	
	private Integer nextOrPrevious;
}

