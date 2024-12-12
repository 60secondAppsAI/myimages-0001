package com.myimages.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.myimages.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.myimages.domain.ImageTag;
import com.myimages.dto.ImageTagDTO;
import com.myimages.dto.ImageTagSearchDTO;
import com.myimages.dto.ImageTagPageDTO;
import com.myimages.service.ImageTagService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/imageTag")
@RestController
public class ImageTagController {

	private final static Logger logger = LoggerFactory.getLogger(ImageTagController.class);

	@Autowired
	ImageTagService imageTagService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ImageTag> getAll() {

		List<ImageTag> imageTags = imageTagService.findAll();
		
		return imageTags;	
	}

	@GetMapping(value = "/{imageTagId}")
	@ResponseBody
	public ImageTagDTO getImageTag(@PathVariable Integer imageTagId) {
		
		return (imageTagService.getImageTagDTOById(imageTagId));
	}

 	@RequestMapping(value = "/addImageTag", method = RequestMethod.POST)
	public ResponseEntity<?> addImageTag(@RequestBody ImageTagDTO imageTagDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = imageTagService.addImageTag(imageTagDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/imageTags")
	public ResponseEntity<ImageTagPageDTO> getImageTags(ImageTagSearchDTO imageTagSearchDTO) {
 
		return imageTagService.getImageTags(imageTagSearchDTO);
	}	

	@RequestMapping(value = "/updateImageTag", method = RequestMethod.POST)
	public ResponseEntity<?> updateImageTag(@RequestBody ImageTagDTO imageTagDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = imageTagService.updateImageTag(imageTagDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
