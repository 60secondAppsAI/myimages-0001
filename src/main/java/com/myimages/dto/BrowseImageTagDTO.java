package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseImageTagDTO {

	private Integer ownerId;

	private Integer imageTagId;

	private Integer imageTagStatus;
	
	private Integer nextOrPrevious;
}

