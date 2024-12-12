package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseHistoryDTO {

	private Integer ownerId;

	private Integer historyId;

	private Integer historyStatus;
	
	private Integer nextOrPrevious;
}

