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

import com.myimages.domain.History;
import com.myimages.dto.HistoryDTO;
import com.myimages.dto.HistorySearchDTO;
import com.myimages.dto.HistoryPageDTO;
import com.myimages.service.HistoryService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/history")
@RestController
public class HistoryController {

	private final static Logger logger = LoggerFactory.getLogger(HistoryController.class);

	@Autowired
	HistoryService historyService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<History> getAll() {

		List<History> historys = historyService.findAll();
		
		return historys;	
	}

	@GetMapping(value = "/{historyId}")
	@ResponseBody
	public HistoryDTO getHistory(@PathVariable Integer historyId) {
		
		return (historyService.getHistoryDTOById(historyId));
	}

 	@RequestMapping(value = "/addHistory", method = RequestMethod.POST)
	public ResponseEntity<?> addHistory(@RequestBody HistoryDTO historyDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = historyService.addHistory(historyDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/historys")
	public ResponseEntity<HistoryPageDTO> getHistorys(HistorySearchDTO historySearchDTO) {
 
		return historyService.getHistorys(historySearchDTO);
	}	

	@RequestMapping(value = "/updateHistory", method = RequestMethod.POST)
	public ResponseEntity<?> updateHistory(@RequestBody HistoryDTO historyDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = historyService.updateHistory(historyDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}




}
