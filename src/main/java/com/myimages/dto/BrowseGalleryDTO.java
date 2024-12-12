package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseGalleryDTO {

	private Integer ownerId;

	private Integer galleryId;

	private Integer galleryStatus;
	
	private Integer nextOrPrevious;
}

