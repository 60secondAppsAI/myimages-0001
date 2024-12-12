package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseImageDTO {

	private Integer ownerId;

	private Integer imageId;

	private Integer imageStatus;
	
	private Integer nextOrPrevious;
}

