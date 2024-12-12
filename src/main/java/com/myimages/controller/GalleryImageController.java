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

import com.myimages.domain.GalleryImage;
import com.myimages.dto.GalleryImageDTO;
import com.myimages.dto.GalleryImageSearchDTO;
import com.myimages.dto.GalleryImagePageDTO;
import com.myimages.service.GalleryImageService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/galleryImage")
@RestController
public class GalleryImageController {

	private final static Logger logger = LoggerFactory.getLogger(GalleryImageController.class);

	@Autowired
	GalleryImageService galleryImageService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<GalleryImage> getAll() {

		List<GalleryImage> galleryImages = galleryImageService.findAll();
		
		return galleryImages;	
	}

	@GetMapping(value = "/{galleryImageId}")
	@ResponseBody
	public GalleryImageDTO getGalleryImage(@PathVariable Integer galleryImageId) {
		
		return (galleryImageService.getGalleryImageDTOById(galleryImageId));
	}

 	@RequestMapping(value = "/addGalleryImage", method = RequestMethod.POST)
	public ResponseEntity<?> addGalleryImage(@RequestBody GalleryImageDTO galleryImageDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = galleryImageService.addGalleryImage(galleryImageDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/galleryImages")
	public ResponseEntity<GalleryImagePageDTO> getGalleryImages(GalleryImageSearchDTO galleryImageSearchDTO) {
 
		return galleryImageService.getGalleryImages(galleryImageSearchDTO);
	}	

	@RequestMapping(value = "/updateGalleryImage", method = RequestMethod.POST)
	public ResponseEntity<?> updateGalleryImage(@RequestBody GalleryImageDTO galleryImageDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = galleryImageService.updateGalleryImage(galleryImageDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
