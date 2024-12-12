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

import com.myimages.domain.UserRole;
import com.myimages.dto.UserRoleDTO;
import com.myimages.dto.UserRoleSearchDTO;
import com.myimages.dto.UserRolePageDTO;
import com.myimages.service.UserRoleService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/userRole")
@RestController
public class UserRoleController {

	private final static Logger logger = LoggerFactory.getLogger(UserRoleController.class);

	@Autowired
	UserRoleService userRoleService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<UserRole> getAll() {

		List<UserRole> userRoles = userRoleService.findAll();
		
		return userRoles;	
	}

	@GetMapping(value = "/{userRoleId}")
	@ResponseBody
	public UserRoleDTO getUserRole(@PathVariable Integer userRoleId) {
		
		return (userRoleService.getUserRoleDTOById(userRoleId));
	}

 	@RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
	public ResponseEntity<?> addUserRole(@RequestBody UserRoleDTO userRoleDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userRoleService.addUserRole(userRoleDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/userRoles")
	public ResponseEntity<UserRolePageDTO> getUserRoles(UserRoleSearchDTO userRoleSearchDTO) {
 
		return userRoleService.getUserRoles(userRoleSearchDTO);
	}	

	@RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserRole(@RequestBody UserRoleDTO userRoleDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userRoleService.updateUserRole(userRoleDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
