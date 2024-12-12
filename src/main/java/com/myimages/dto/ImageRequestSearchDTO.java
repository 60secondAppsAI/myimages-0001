package com.myimages.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageRequestSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer imageRequestId;
	
	private Integer promptConfigId;
	
	private Date createdOnId;
	
	private Integer createdImageId;
	
}
