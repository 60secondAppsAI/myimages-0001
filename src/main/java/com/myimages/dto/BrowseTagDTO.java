package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseTagDTO {

	private Integer ownerId;

	private Integer tagId;

	private Integer tagStatus;
	
	private Integer nextOrPrevious;
}

