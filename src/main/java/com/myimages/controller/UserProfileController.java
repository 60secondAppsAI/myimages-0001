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

import com.myimages.domain.UserProfile;
import com.myimages.dto.UserProfileDTO;
import com.myimages.dto.UserProfileSearchDTO;
import com.myimages.dto.UserProfilePageDTO;
import com.myimages.service.UserProfileService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/userProfile")
@RestController
public class UserProfileController {

	private final static Logger logger = LoggerFactory.getLogger(UserProfileController.class);

	@Autowired
	UserProfileService userProfileService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<UserProfile> getAll() {

		List<UserProfile> userProfiles = userProfileService.findAll();
		
		return userProfiles;	
	}

	@GetMapping(value = "/{userProfileId}")
	@ResponseBody
	public UserProfileDTO getUserProfile(@PathVariable Integer userProfileId) {
		
		return (userProfileService.getUserProfileDTOById(userProfileId));
	}

 	@RequestMapping(value = "/addUserProfile", method = RequestMethod.POST)
	public ResponseEntity<?> addUserProfile(@RequestBody UserProfileDTO userProfileDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userProfileService.addUserProfile(userProfileDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/userProfiles")
	public ResponseEntity<UserProfilePageDTO> getUserProfiles(UserProfileSearchDTO userProfileSearchDTO) {
 
		return userProfileService.getUserProfiles(userProfileSearchDTO);
	}	

	@RequestMapping(value = "/updateUserProfile", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserProfile(@RequestBody UserProfileDTO userProfileDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userProfileService.updateUserProfile(userProfileDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
