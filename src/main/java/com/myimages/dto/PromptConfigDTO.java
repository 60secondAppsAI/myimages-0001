package com.myimages.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PromptConfigDTO {

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
