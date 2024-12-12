package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseUserProfileDTO {

	private Integer ownerId;

	private Integer userProfileId;

	private Integer userProfileStatus;
	
	private Integer nextOrPrevious;
}

