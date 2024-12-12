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

import com.myimages.domain.ImageMetadata;
import com.myimages.dto.ImageMetadataDTO;
import com.myimages.dto.ImageMetadataSearchDTO;
import com.myimages.dto.ImageMetadataPageDTO;
import com.myimages.service.ImageMetadataService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/imageMetadata")
@RestController
public class ImageMetadataController {

	private final static Logger logger = LoggerFactory.getLogger(ImageMetadataController.class);

	@Autowired
	ImageMetadataService imageMetadataService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ImageMetadata> getAll() {

		List<ImageMetadata> imageMetadatas = imageMetadataService.findAll();
		
		return imageMetadatas;	
	}

	@GetMapping(value = "/{imageMetadataId}")
	@ResponseBody
	public ImageMetadataDTO getImageMetadata(@PathVariable Integer imageMetadataId) {
		
		return (imageMetadataService.getImageMetadataDTOById(imageMetadataId));
	}

 	@RequestMapping(value = "/addImageMetadata", method = RequestMethod.POST)
	public ResponseEntity<?> addImageMetadata(@RequestBody ImageMetadataDTO imageMetadataDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = imageMetadataService.addImageMetadata(imageMetadataDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/imageMetadatas")
	public ResponseEntity<ImageMetadataPageDTO> getImageMetadatas(ImageMetadataSearchDTO imageMetadataSearchDTO) {
 
		return imageMetadataService.getImageMetadatas(imageMetadataSearchDTO);
	}	

	@RequestMapping(value = "/updateImageMetadata", method = RequestMethod.POST)
	public ResponseEntity<?> updateImageMetadata(@RequestBody ImageMetadataDTO imageMetadataDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = imageMetadataService.updateImageMetadata(imageMetadataDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
