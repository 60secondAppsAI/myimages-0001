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

import com.myimages.domain.Settings;
import com.myimages.dto.SettingsDTO;
import com.myimages.dto.SettingsSearchDTO;
import com.myimages.dto.SettingsPageDTO;
import com.myimages.service.SettingsService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/settings")
@RestController
public class SettingsController {

	private final static Logger logger = LoggerFactory.getLogger(SettingsController.class);

	@Autowired
	SettingsService settingsService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Settings> getAll() {

		List<Settings> settingss = settingsService.findAll();
		
		return settingss;	
	}

	@GetMapping(value = "/{settingsId}")
	@ResponseBody
	public SettingsDTO getSettings(@PathVariable Integer settingsId) {
		
		return (settingsService.getSettingsDTOById(settingsId));
	}

 	@RequestMapping(value = "/addSettings", method = RequestMethod.POST)
	public ResponseEntity<?> addSettings(@RequestBody SettingsDTO settingsDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = settingsService.addSettings(settingsDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/settingss")
	public ResponseEntity<SettingsPageDTO> getSettingss(SettingsSearchDTO settingsSearchDTO) {
 
		return settingsService.getSettingss(settingsSearchDTO);
	}	

	@RequestMapping(value = "/updateSettings", method = RequestMethod.POST)
	public ResponseEntity<?> updateSettings(@RequestBody SettingsDTO settingsDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = settingsService.updateSettings(settingsDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
