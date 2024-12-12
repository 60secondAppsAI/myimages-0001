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

import com.myimages.domain.PromptConfig;
import com.myimages.dto.PromptConfigDTO;
import com.myimages.dto.PromptConfigSearchDTO;
import com.myimages.dto.PromptConfigPageDTO;
import com.myimages.service.PromptConfigService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/promptConfig")
@RestController
public class PromptConfigController {

	private final static Logger logger = LoggerFactory.getLogger(PromptConfigController.class);

	@Autowired
	PromptConfigService promptConfigService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PromptConfig> getAll() {

		List<PromptConfig> promptConfigs = promptConfigService.findAll();
		
		return promptConfigs;	
	}

	@GetMapping(value = "/{promptConfigId}")
	@ResponseBody
	public PromptConfigDTO getPromptConfig(@PathVariable Integer promptConfigId) {
		
		return (promptConfigService.getPromptConfigDTOById(promptConfigId));
	}

 	@RequestMapping(value = "/addPromptConfig", method = RequestMethod.POST)
	public ResponseEntity<?> addPromptConfig(@RequestBody PromptConfigDTO promptConfigDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = promptConfigService.addPromptConfig(promptConfigDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/promptConfigs")
	public ResponseEntity<PromptConfigPageDTO> getPromptConfigs(PromptConfigSearchDTO promptConfigSearchDTO) {
 
		return promptConfigService.getPromptConfigs(promptConfigSearchDTO);
	}	

	@RequestMapping(value = "/updatePromptConfig", method = RequestMethod.POST)
	public ResponseEntity<?> updatePromptConfig(@RequestBody PromptConfigDTO promptConfigDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = promptConfigService.updatePromptConfig(promptConfigDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
