package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.History;
import com.myimages.dto.HistoryDTO;
import com.myimages.dto.HistorySearchDTO;
import com.myimages.dto.HistoryPageDTO;
import com.myimages.dto.HistoryConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface HistoryService extends GenericService<History, Integer> {

	List<History> findAll();

	ResultDTO addHistory(HistoryDTO historyDTO, RequestDTO requestDTO);

	ResultDTO updateHistory(HistoryDTO historyDTO, RequestDTO requestDTO);

    Page<History> getAllHistorys(Pageable pageable);

    Page<History> getAllHistorys(Specification<History> spec, Pageable pageable);

	ResponseEntity<HistoryPageDTO> getHistorys(HistorySearchDTO historySearchDTO);
	
	List<HistoryDTO> convertHistorysToHistoryDTOs(List<History> historys, HistoryConvertCriteriaDTO convertCriteria);

	HistoryDTO getHistoryDTOById(Integer historyId);


}
