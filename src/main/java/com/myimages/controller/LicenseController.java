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

import com.myimages.domain.License;
import com.myimages.dto.LicenseDTO;
import com.myimages.dto.LicenseSearchDTO;
import com.myimages.dto.LicensePageDTO;
import com.myimages.service.LicenseService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/license")
@RestController
public class LicenseController {

	private final static Logger logger = LoggerFactory.getLogger(LicenseController.class);

	@Autowired
	LicenseService licenseService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<License> getAll() {

		List<License> licenses = licenseService.findAll();
		
		return licenses;	
	}

	@GetMapping(value = "/{licenseId}")
	@ResponseBody
	public LicenseDTO getLicense(@PathVariable Integer licenseId) {
		
		return (licenseService.getLicenseDTOById(licenseId));
	}

 	@RequestMapping(value = "/addLicense", method = RequestMethod.POST)
	public ResponseEntity<?> addLicense(@RequestBody LicenseDTO licenseDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = licenseService.addLicense(licenseDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/licenses")
	public ResponseEntity<LicensePageDTO> getLicenses(LicenseSearchDTO licenseSearchDTO) {
 
		return licenseService.getLicenses(licenseSearchDTO);
	}	

	@RequestMapping(value = "/updateLicense", method = RequestMethod.POST)
	public ResponseEntity<?> updateLicense(@RequestBody LicenseDTO licenseDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = licenseService.updateLicense(licenseDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
