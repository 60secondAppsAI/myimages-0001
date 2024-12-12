package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseUserRoleDTO {

	private Integer ownerId;

	private Integer userRoleId;

	private Integer userRoleStatus;
	
	private Integer nextOrPrevious;
}

