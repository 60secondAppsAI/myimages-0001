package com.myimages.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ImageDTO {

	private Integer imageId;

	private Integer promptConfigId;

	private String imageUrl;

	private Date uploadDate;






}
