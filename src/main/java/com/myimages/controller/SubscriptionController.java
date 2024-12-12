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

import com.myimages.domain.Subscription;
import com.myimages.dto.SubscriptionDTO;
import com.myimages.dto.SubscriptionSearchDTO;
import com.myimages.dto.SubscriptionPageDTO;
import com.myimages.service.SubscriptionService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/subscription")
@RestController
public class SubscriptionController {

	private final static Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

	@Autowired
	SubscriptionService subscriptionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Subscription> getAll() {

		List<Subscription> subscriptions = subscriptionService.findAll();
		
		return subscriptions;	
	}

	@GetMapping(value = "/{subscriptionId}")
	@ResponseBody
	public SubscriptionDTO getSubscription(@PathVariable Integer subscriptionId) {
		
		return (subscriptionService.getSubscriptionDTOById(subscriptionId));
	}

 	@RequestMapping(value = "/addSubscription", method = RequestMethod.POST)
	public ResponseEntity<?> addSubscription(@RequestBody SubscriptionDTO subscriptionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = subscriptionService.addSubscription(subscriptionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/subscriptions")
	public ResponseEntity<SubscriptionPageDTO> getSubscriptions(SubscriptionSearchDTO subscriptionSearchDTO) {
 
		return subscriptionService.getSubscriptions(subscriptionSearchDTO);
	}	

	@RequestMapping(value = "/updateSubscription", method = RequestMethod.POST)
	public ResponseEntity<?> updateSubscription(@RequestBody SubscriptionDTO subscriptionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = subscriptionService.updateSubscription(subscriptionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
