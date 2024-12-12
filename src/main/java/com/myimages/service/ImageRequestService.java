package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.ImageRequest;
import com.myimages.dto.ImageRequestDTO;
import com.myimages.dto.ImageRequestSearchDTO;
import com.myimages.dto.ImageRequestPageDTO;
import com.myimages.dto.ImageRequestConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ImageRequestService extends GenericService<ImageRequest, Integer> {

	List<ImageRequest> findAll();

	ResultDTO addImageRequest(ImageRequestDTO imageRequestDTO, RequestDTO requestDTO);

	ResultDTO updateImageRequest(ImageRequestDTO imageRequestDTO, RequestDTO requestDTO);

    Page<ImageRequest> getAllImageRequests(Pageable pageable);

    Page<ImageRequest> getAllImageRequests(Specification<ImageRequest> spec, Pageable pageable);

	ResponseEntity<ImageRequestPageDTO> getImageRequests(ImageRequestSearchDTO imageRequestSearchDTO);
	
	List<ImageRequestDTO> convertImageRequestsToImageRequestDTOs(List<ImageRequest> imageRequests, ImageRequestConvertCriteriaDTO convertCriteria);

	ImageRequestDTO getImageRequestDTOById(Integer imageRequestId);


}
