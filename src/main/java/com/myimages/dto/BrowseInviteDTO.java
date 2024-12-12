package com.myimages.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseInviteDTO {

	private Integer ownerId;

	private Integer inviteId;

	private Integer inviteStatus;
	
	private Integer nextOrPrevious;
}

