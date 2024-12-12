package com.myimages.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PromptConfigSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer promptConfigId;
	
	private Integer ownerId;
	
	private Date createdOnId;
	
	private String color1;
	
	private String color2;
	
	private String color3;
	
	private String style;
	
	private String sponsor1;
	
	private String sponsor2;
	
}
