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
import com.myimages.dao.ImageRequestDAO;
import com.myimages.domain.ImageRequest;
import com.myimages.dto.ImageRequestDTO;
import com.myimages.dto.ImageRequestSearchDTO;
import com.myimages.dto.ImageRequestPageDTO;
import com.myimages.dto.ImageRequestConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.ImageRequestService;
import com.myimages.util.ControllerUtils;

@Service
public class ImageRequestServiceImpl extends GenericServiceImpl<ImageRequest, Integer> implements ImageRequestService {

    private final static Logger logger = LoggerFactory.getLogger(ImageRequestServiceImpl.class);

	@Autowired
	ImageRequestDAO imageRequestDao;

	@Override
	public GenericDAO<ImageRequest, Integer> getDAO() {
		return (GenericDAO<ImageRequest, Integer>) imageRequestDao;
	}
	
	public List<ImageRequest> findAll () {
		List<ImageRequest> imageRequests = imageRequestDao.findAll();
		
		return imageRequests;	
		
	}

	public ResultDTO addImageRequest(ImageRequestDTO imageRequestDTO, RequestDTO requestDTO) {

		ImageRequest imageRequest = new ImageRequest();

		imageRequest.setImageRequestId(imageRequestDTO.getImageRequestId());

		imageRequest.setPromptConfigId(imageRequestDTO.getPromptConfigId());

		imageRequest.setCreatedOnId(imageRequestDTO.getCreatedOnId());

		imageRequest.setCreatedImageId(imageRequestDTO.getCreatedImageId());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		imageRequest = imageRequestDao.save(imageRequest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ImageRequest> getAllImageRequests(Pageable pageable) {
		return imageRequestDao.findAll(pageable);
	}

	public Page<ImageRequest> getAllImageRequests(Specification<ImageRequest> spec, Pageable pageable) {
		return imageRequestDao.findAll(spec, pageable);
	}

	public ResponseEntity<ImageRequestPageDTO> getImageRequests(ImageRequestSearchDTO imageRequestSearchDTO) {
	
			Integer imageRequestId = imageRequestSearchDTO.getImageRequestId(); 
 			Integer promptConfigId = imageRequestSearchDTO.getPromptConfigId(); 
   			Integer createdImageId = imageRequestSearchDTO.getCreatedImageId(); 
 			String sortBy = imageRequestSearchDTO.getSortBy();
			String sortOrder = imageRequestSearchDTO.getSortOrder();
			String searchQuery = imageRequestSearchDTO.getSearchQuery();
			Integer page = imageRequestSearchDTO.getPage();
			Integer size = imageRequestSearchDTO.getSize();

	        Specification<ImageRequest> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, imageRequestId, "imageRequestId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, promptConfigId, "promptConfigId"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, createdImageId, "createdImageId"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<ImageRequest> imageRequests = this.getAllImageRequests(spec, pageable);
		
		//System.out.println(String.valueOf(imageRequests.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(imageRequests.getTotalPages()));
		
		List<ImageRequest> imageRequestsList = imageRequests.getContent();
		
		ImageRequestConvertCriteriaDTO convertCriteria = new ImageRequestConvertCriteriaDTO();
		List<ImageRequestDTO> imageRequestDTOs = this.convertImageRequestsToImageRequestDTOs(imageRequestsList,convertCriteria);
		
		ImageRequestPageDTO imageRequestPageDTO = new ImageRequestPageDTO();
		imageRequestPageDTO.setImageRequests(imageRequestDTOs);
		imageRequestPageDTO.setTotalElements(imageRequests.getTotalElements());
		return ResponseEntity.ok(imageRequestPageDTO);
	}

	public List<ImageRequestDTO> convertImageRequestsToImageRequestDTOs(List<ImageRequest> imageRequests, ImageRequestConvertCriteriaDTO convertCriteria) {
		
		List<ImageRequestDTO> imageRequestDTOs = new ArrayList<ImageRequestDTO>();
		
		for (ImageRequest imageRequest : imageRequests) {
			imageRequestDTOs.add(convertImageRequestToImageRequestDTO(imageRequest,convertCriteria));
		}
		
		return imageRequestDTOs;

	}
	
	public ImageRequestDTO convertImageRequestToImageRequestDTO(ImageRequest imageRequest, ImageRequestConvertCriteriaDTO convertCriteria) {
		
		ImageRequestDTO imageRequestDTO = new ImageRequestDTO();

		imageRequestDTO.setImageRequestId(imageRequest.getImageRequestId());

		imageRequestDTO.setPromptConfigId(imageRequest.getPromptConfigId());

		imageRequestDTO.setCreatedOnId(imageRequest.getCreatedOnId());

		imageRequestDTO.setCreatedImageId(imageRequest.getCreatedImageId());
		
		return imageRequestDTO;
	}

	public ResultDTO updateImageRequest(ImageRequestDTO imageRequestDTO, RequestDTO requestDTO) {
		
		ImageRequest imageRequest = imageRequestDao.getById(imageRequestDTO.getImageRequestId());
		
		imageRequest.setImageRequestId(ControllerUtils.setValue(imageRequest.getImageRequestId(), imageRequestDTO.getImageRequestId()));
		
		imageRequest.setPromptConfigId(ControllerUtils.setValue(imageRequest.getPromptConfigId(), imageRequestDTO.getPromptConfigId()));
		
		imageRequest.setCreatedOnId(ControllerUtils.setValue(imageRequest.getCreatedOnId(), imageRequestDTO.getCreatedOnId()));
		
		imageRequest.setCreatedImageId(ControllerUtils.setValue(imageRequest.getCreatedImageId(), imageRequestDTO.getCreatedImageId()));

        imageRequest = imageRequestDao.save(imageRequest);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ImageRequestDTO getImageRequestDTOById(Integer imageRequestId) {
	
		ImageRequest imageRequest = imageRequestDao.getById(imageRequestId);
		
		ImageRequestConvertCriteriaDTO convertCriteria = new ImageRequestConvertCriteriaDTO();
		return(this.convertImageRequestToImageRequestDTO(imageRequest,convertCriteria));
	}
}
