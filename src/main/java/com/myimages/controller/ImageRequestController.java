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

import com.myimages.domain.ImageRequest;
import com.myimages.dto.ImageRequestDTO;
import com.myimages.dto.ImageRequestSearchDTO;
import com.myimages.dto.ImageRequestPageDTO;
import com.myimages.service.ImageRequestService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/imageRequest")
@RestController
public class ImageRequestController {

	private final static Logger logger = LoggerFactory.getLogger(ImageRequestController.class);

	@Autowired
	ImageRequestService imageRequestService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ImageRequest> getAll() {

		List<ImageRequest> imageRequests = imageRequestService.findAll();
		
		return imageRequests;	
	}

	@GetMapping(value = "/{imageRequestId}")
	@ResponseBody
	public ImageRequestDTO getImageRequest(@PathVariable Integer imageRequestId) {
		
		return (imageRequestService.getImageRequestDTOById(imageRequestId));
	}

 	@RequestMapping(value = "/addImageRequest", method = RequestMethod.POST)
	public ResponseEntity<?> addImageRequest(@RequestBody ImageRequestDTO imageRequestDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = imageRequestService.addImageRequest(imageRequestDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/imageRequests")
	public ResponseEntity<ImageRequestPageDTO> getImageRequests(ImageRequestSearchDTO imageRequestSearchDTO) {
 
		return imageRequestService.getImageRequests(imageRequestSearchDTO);
	}	

	@RequestMapping(value = "/updateImageRequest", method = RequestMethod.POST)
	public ResponseEntity<?> updateImageRequest(@RequestBody ImageRequestDTO imageRequestDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = imageRequestService.updateImageRequest(imageRequestDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
