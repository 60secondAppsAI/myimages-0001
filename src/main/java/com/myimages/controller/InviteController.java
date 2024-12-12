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

import com.myimages.domain.Invite;
import com.myimages.dto.InviteDTO;
import com.myimages.dto.InviteSearchDTO;
import com.myimages.dto.InvitePageDTO;
import com.myimages.service.InviteService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/invite")
@RestController
public class InviteController {

	private final static Logger logger = LoggerFactory.getLogger(InviteController.class);

	@Autowired
	InviteService inviteService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Invite> getAll() {

		List<Invite> invites = inviteService.findAll();
		
		return invites;	
	}

	@GetMapping(value = "/{inviteId}")
	@ResponseBody
	public InviteDTO getInvite(@PathVariable Integer inviteId) {
		
		return (inviteService.getInviteDTOById(inviteId));
	}

 	@RequestMapping(value = "/addInvite", method = RequestMethod.POST)
	public ResponseEntity<?> addInvite(@RequestBody InviteDTO inviteDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = inviteService.addInvite(inviteDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/invites")
	public ResponseEntity<InvitePageDTO> getInvites(InviteSearchDTO inviteSearchDTO) {
 
		return inviteService.getInvites(inviteSearchDTO);
	}	

	@RequestMapping(value = "/updateInvite", method = RequestMethod.POST)
	public ResponseEntity<?> updateInvite(@RequestBody InviteDTO inviteDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = inviteService.updateInvite(inviteDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
