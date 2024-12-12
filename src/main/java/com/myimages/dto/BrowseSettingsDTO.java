package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseSettingsDTO {

	private Integer ownerId;

	private Integer settingsId;

	private Integer settingsStatus;
	
	private Integer nextOrPrevious;
}

