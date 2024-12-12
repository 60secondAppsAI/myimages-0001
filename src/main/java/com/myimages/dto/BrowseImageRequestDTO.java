package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseImageRequestDTO {

	private Integer ownerId;

	private Integer imageRequestId;

	private Integer imageRequestStatus;
	
	private Integer nextOrPrevious;
}

