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
import com.myimages.dao.FeedbackDAO;
import com.myimages.domain.Feedback;
import com.myimages.dto.FeedbackDTO;
import com.myimages.dto.FeedbackSearchDTO;
import com.myimages.dto.FeedbackPageDTO;
import com.myimages.dto.FeedbackConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.FeedbackService;
import com.myimages.util.ControllerUtils;

@Service
public class FeedbackServiceImpl extends GenericServiceImpl<Feedback, Integer> implements FeedbackService {

    private final static Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

	@Autowired
	FeedbackDAO feedbackDao;

	@Override
	public GenericDAO<Feedback, Integer> getDAO() {
		return (GenericDAO<Feedback, Integer>) feedbackDao;
	}
	
	public List<Feedback> findAll () {
		List<Feedback> feedbacks = feedbackDao.findAll();
		
		return feedbacks;	
		
	}

	public ResultDTO addFeedback(FeedbackDTO feedbackDTO, RequestDTO requestDTO) {

		Feedback feedback = new Feedback();

		feedback.setFeedbackId(feedbackDTO.getFeedbackId());

		feedback.setImageRequestId(feedbackDTO.getImageRequestId());

		feedback.setUserProfileId(feedbackDTO.getUserProfileId());

		feedback.setFeedbackText(feedbackDTO.getFeedbackText());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		feedback = feedbackDao.save(feedback);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Feedback> getAllFeedbacks(Pageable pageable) {
		return feedbackDao.findAll(pageable);
	}

	public Page<Feedback> getAllFeedbacks(Specification<Feedback> spec, Pageable pageable) {
		return feedbackDao.findAll(spec, pageable);
	}

	public ResponseEntity<FeedbackPageDTO> getFeedbacks(FeedbackSearchDTO feedbackSearchDTO) {
	
			Integer feedbackId = feedbackSearchDTO.getFeedbackId(); 
 			Integer imageRequestId = feedbackSearchDTO.getImageRequestId(); 
 			Integer userProfileId = feedbackSearchDTO.getUserProfileId(); 
 			String feedbackText = feedbackSearchDTO.getFeedbackText(); 
 			String sortBy = feedbackSearchDTO.getSortBy();
			String sortOrder = feedbackSearchDTO.getSortOrder();
			String searchQuery = feedbackSearchDTO.getSearchQuery();
			Integer page = feedbackSearchDTO.getPage();
			Integer size = feedbackSearchDTO.getSize();

	        Specification<Feedback> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, feedbackId, "feedbackId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, imageRequestId, "imageRequestId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, userProfileId, "userProfileId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, feedbackText, "feedbackText"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("feedbackText")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Feedback> feedbacks = this.getAllFeedbacks(spec, pageable);
		
		//System.out.println(String.valueOf(feedbacks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(feedbacks.getTotalPages()));
		
		List<Feedback> feedbacksList = feedbacks.getContent();
		
		FeedbackConvertCriteriaDTO convertCriteria = new FeedbackConvertCriteriaDTO();
		List<FeedbackDTO> feedbackDTOs = this.convertFeedbacksToFeedbackDTOs(feedbacksList,convertCriteria);
		
		FeedbackPageDTO feedbackPageDTO = new FeedbackPageDTO();
		feedbackPageDTO.setFeedbacks(feedbackDTOs);
		feedbackPageDTO.setTotalElements(feedbacks.getTotalElements());
		return ResponseEntity.ok(feedbackPageDTO);
	}

	public List<FeedbackDTO> convertFeedbacksToFeedbackDTOs(List<Feedback> feedbacks, FeedbackConvertCriteriaDTO convertCriteria) {
		
		List<FeedbackDTO> feedbackDTOs = new ArrayList<FeedbackDTO>();
		
		for (Feedback feedback : feedbacks) {
			feedbackDTOs.add(convertFeedbackToFeedbackDTO(feedback,convertCriteria));
		}
		
		return feedbackDTOs;

	}
	
	public FeedbackDTO convertFeedbackToFeedbackDTO(Feedback feedback, FeedbackConvertCriteriaDTO convertCriteria) {
		
		FeedbackDTO feedbackDTO = new FeedbackDTO();

		feedbackDTO.setFeedbackId(feedback.getFeedbackId());

		feedbackDTO.setImageRequestId(feedback.getImageRequestId());

		feedbackDTO.setUserProfileId(feedback.getUserProfileId());

		feedbackDTO.setFeedbackText(feedback.getFeedbackText());
		
		return feedbackDTO;
	}

	public ResultDTO updateFeedback(FeedbackDTO feedbackDTO, RequestDTO requestDTO) {
		
		Feedback feedback = feedbackDao.getById(feedbackDTO.getFeedbackId());
		
		feedback.setFeedbackId(ControllerUtils.setValue(feedback.getFeedbackId(), feedbackDTO.getFeedbackId()));
		
		feedback.setImageRequestId(ControllerUtils.setValue(feedback.getImageRequestId(), feedbackDTO.getImageRequestId()));
		
		feedback.setUserProfileId(ControllerUtils.setValue(feedback.getUserProfileId(), feedbackDTO.getUserProfileId()));
		
		feedback.setFeedbackText(ControllerUtils.setValue(feedback.getFeedbackText(), feedbackDTO.getFeedbackText()));

        feedback = feedbackDao.save(feedback);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public FeedbackDTO getFeedbackDTOById(Integer feedbackId) {
	
		Feedback feedback = feedbackDao.getById(feedbackId);
		
		FeedbackConvertCriteriaDTO convertCriteria = new FeedbackConvertCriteriaDTO();
		return(this.convertFeedbackToFeedbackDTO(feedback,convertCriteria));
	}
}
