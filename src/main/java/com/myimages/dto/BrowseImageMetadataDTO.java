package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseImageMetadataDTO {

	private Integer ownerId;

	private Integer imageMetadataId;

	private Integer imageMetadataStatus;
	
	private Integer nextOrPrevious;
}

