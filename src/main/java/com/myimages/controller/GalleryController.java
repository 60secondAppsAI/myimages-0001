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

import com.myimages.domain.Gallery;
import com.myimages.dto.GalleryDTO;
import com.myimages.dto.GallerySearchDTO;
import com.myimages.dto.GalleryPageDTO;
import com.myimages.service.GalleryService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/gallery")
@RestController
public class GalleryController {

	private final static Logger logger = LoggerFactory.getLogger(GalleryController.class);

	@Autowired
	GalleryService galleryService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Gallery> getAll() {

		List<Gallery> gallerys = galleryService.findAll();
		
		return gallerys;	
	}

	@GetMapping(value = "/{galleryId}")
	@ResponseBody
	public GalleryDTO getGallery(@PathVariable Integer galleryId) {
		
		return (galleryService.getGalleryDTOById(galleryId));
	}

 	@RequestMapping(value = "/addGallery", method = RequestMethod.POST)
	public ResponseEntity<?> addGallery(@RequestBody GalleryDTO galleryDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = galleryService.addGallery(galleryDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/gallerys")
	public ResponseEntity<GalleryPageDTO> getGallerys(GallerySearchDTO gallerySearchDTO) {
 
		return galleryService.getGallerys(gallerySearchDTO);
	}	

	@RequestMapping(value = "/updateGallery", method = RequestMethod.POST)
	public ResponseEntity<?> updateGallery(@RequestBody GalleryDTO galleryDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = galleryService.updateGallery(galleryDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
