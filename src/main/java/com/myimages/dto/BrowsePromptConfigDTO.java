package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowsePromptConfigDTO {

	private Integer ownerId;

	private Integer promptConfigId;

	private Integer promptConfigStatus;
	
	private Integer nextOrPrevious;
}

