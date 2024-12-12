package com.myimages.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SettingsSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer settingsId;
	
	private Integer userProfileId;
	
	private String theme;
	
	private String notificationEnabled;
	
}
