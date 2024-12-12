package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseNotificationDTO {

	private Integer ownerId;

	private Integer notificationId;

	private Integer notificationStatus;
	
	private Integer nextOrPrevious;
}

