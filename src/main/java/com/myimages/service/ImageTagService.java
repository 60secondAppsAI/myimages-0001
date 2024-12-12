package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.ImageTag;
import com.myimages.dto.ImageTagDTO;
import com.myimages.dto.ImageTagSearchDTO;
import com.myimages.dto.ImageTagPageDTO;
import com.myimages.dto.ImageTagConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ImageTagService extends GenericService<ImageTag, Integer> {

	List<ImageTag> findAll();

	ResultDTO addImageTag(ImageTagDTO imageTagDTO, RequestDTO requestDTO);

	ResultDTO updateImageTag(ImageTagDTO imageTagDTO, RequestDTO requestDTO);

    Page<ImageTag> getAllImageTags(Pageable pageable);

    Page<ImageTag> getAllImageTags(Specification<ImageTag> spec, Pageable pageable);

	ResponseEntity<ImageTagPageDTO> getImageTags(ImageTagSearchDTO imageTagSearchDTO);
	
	List<ImageTagDTO> convertImageTagsToImageTagDTOs(List<ImageTag> imageTags, ImageTagConvertCriteriaDTO convertCriteria);

	ImageTagDTO getImageTagDTOById(Integer imageTagId);


}
