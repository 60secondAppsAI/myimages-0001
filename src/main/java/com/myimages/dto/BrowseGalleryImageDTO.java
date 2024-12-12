package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseGalleryImageDTO {

	private Integer ownerId;

	private Integer galleryImageId;

	private Integer galleryImageStatus;
	
	private Integer nextOrPrevious;
}

