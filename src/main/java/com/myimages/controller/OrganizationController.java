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

import com.myimages.domain.Organization;
import com.myimages.dto.OrganizationDTO;
import com.myimages.dto.OrganizationSearchDTO;
import com.myimages.dto.OrganizationPageDTO;
import com.myimages.service.OrganizationService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/organization")
@RestController
public class OrganizationController {

	private final static Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	OrganizationService organizationService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Organization> getAll() {

		List<Organization> organizations = organizationService.findAll();
		
		return organizations;	
	}

	@GetMapping(value = "/{organizationId}")
	@ResponseBody
	public OrganizationDTO getOrganization(@PathVariable Integer organizationId) {
		
		return (organizationService.getOrganizationDTOById(organizationId));
	}

 	@RequestMapping(value = "/addOrganization", method = RequestMethod.POST)
	public ResponseEntity<?> addOrganization(@RequestBody OrganizationDTO organizationDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = organizationService.addOrganization(organizationDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/organizations")
	public ResponseEntity<OrganizationPageDTO> getOrganizations(OrganizationSearchDTO organizationSearchDTO) {
 
		return organizationService.getOrganizations(organizationSearchDTO);
	}	

	@RequestMapping(value = "/updateOrganization", method = RequestMethod.POST)
	public ResponseEntity<?> updateOrganization(@RequestBody OrganizationDTO organizationDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = organizationService.updateOrganization(organizationDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
