package com.myimages.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserProfileDTO {

	private Integer userProfileId;

	private String userName;

	private String userEmail;

	private Date createdDate;






}
