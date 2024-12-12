package com.myimages.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.myimages.dao.GenericDAO;
import com.myimages.service.GenericService;
import com.myimages.service.impl.GenericServiceImpl;
import com.myimages.dao.HistoryDAO;
import com.myimages.domain.History;
import com.myimages.dto.HistoryDTO;
import com.myimages.dto.HistorySearchDTO;
import com.myimages.dto.HistoryPageDTO;
import com.myimages.dto.HistoryConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.HistoryService;
import com.myimages.util.ControllerUtils;

@Service
public class HistoryServiceImpl extends GenericServiceImpl<History, Integer> implements HistoryService {

    private final static Logger logger = LoggerFactory.getLogger(HistoryServiceImpl.class);

	@Autowired
	HistoryDAO historyDao;

	@Override
	public GenericDAO<History, Integer> getDAO() {
		return (GenericDAO<History, Integer>) historyDao;
	}
	
	public List<History> findAll () {
		List<History> historys = historyDao.findAll();
		
		return historys;	
		
	}

	public ResultDTO addHistory(HistoryDTO historyDTO, RequestDTO requestDTO) {

		History history = new History();

		history.setHistoryId(historyDTO.getHistoryId());

		history.setUserProfileId(historyDTO.getUserProfileId());

		history.setImageAction(historyDTO.getImageAction());

		history.setActionDate(historyDTO.getActionDate());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		history = historyDao.save(history);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<History> getAllHistorys(Pageable pageable) {
		return historyDao.findAll(pageable);
	}

	public Page<History> getAllHistorys(Specification<History> spec, Pageable pageable) {
		return historyDao.findAll(spec, pageable);
	}

	public ResponseEntity<HistoryPageDTO> getHistorys(HistorySearchDTO historySearchDTO) {
	
			Integer historyId = historySearchDTO.getHistoryId(); 
 			Integer userProfileId = historySearchDTO.getUserProfileId(); 
 			String imageAction = historySearchDTO.getImageAction(); 
   			String sortBy = historySearchDTO.getSortBy();
			String sortOrder = historySearchDTO.getSortOrder();
			String searchQuery = historySearchDTO.getSearchQuery();
			Integer page = historySearchDTO.getPage();
			Integer size = historySearchDTO.getSize();

	        Specification<History> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, historyId, "historyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, userProfileId, "userProfileId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, imageAction, "imageAction"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("imageAction")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<History> historys = this.getAllHistorys(spec, pageable);
		
		//System.out.println(String.valueOf(historys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(historys.getTotalPages()));
		
		List<History> historysList = historys.getContent();
		
		HistoryConvertCriteriaDTO convertCriteria = new HistoryConvertCriteriaDTO();
		List<HistoryDTO> historyDTOs = this.convertHistorysToHistoryDTOs(historysList,convertCriteria);
		
		HistoryPageDTO historyPageDTO = new HistoryPageDTO();
		historyPageDTO.setHistorys(historyDTOs);
		historyPageDTO.setTotalElements(historys.getTotalElements());
		return ResponseEntity.ok(historyPageDTO);
	}

	public List<HistoryDTO> convertHistorysToHistoryDTOs(List<History> historys, HistoryConvertCriteriaDTO convertCriteria) {
		
		List<HistoryDTO> historyDTOs = new ArrayList<HistoryDTO>();
		
		for (History history : historys) {
			historyDTOs.add(convertHistoryToHistoryDTO(history,convertCriteria));
		}
		
		return historyDTOs;

	}
	
	public HistoryDTO convertHistoryToHistoryDTO(History history, HistoryConvertCriteriaDTO convertCriteria) {
		
		HistoryDTO historyDTO = new HistoryDTO();

		historyDTO.setHistoryId(history.getHistoryId());

		historyDTO.setUserProfileId(history.getUserProfileId());

		historyDTO.setImageAction(history.getImageAction());

		historyDTO.setActionDate(history.getActionDate());
		
		return historyDTO;
	}

	public ResultDTO updateHistory(HistoryDTO historyDTO, RequestDTO requestDTO) {
		
		History history = historyDao.getById(historyDTO.getHistoryId());
		
		history.setHistoryId(ControllerUtils.setValue(history.getHistoryId(), historyDTO.getHistoryId()));
		
		history.setUserProfileId(ControllerUtils.setValue(history.getUserProfileId(), historyDTO.getUserProfileId()));
		
		history.setImageAction(ControllerUtils.setValue(history.getImageAction(), historyDTO.getImageAction()));
		
		history.setActionDate(ControllerUtils.setValue(history.getActionDate(), historyDTO.getActionDate()));

        history = historyDao.save(history);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public HistoryDTO getHistoryDTOById(Integer historyId) {
	
		History history = historyDao.getById(historyId);
		
		HistoryConvertCriteriaDTO convertCriteria = new HistoryConvertCriteriaDTO();
		return(this.convertHistoryToHistoryDTO(history,convertCriteria));
	}
}
