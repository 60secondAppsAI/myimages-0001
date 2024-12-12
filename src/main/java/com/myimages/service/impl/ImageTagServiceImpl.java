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
import com.myimages.dao.ImageTagDAO;
import com.myimages.domain.ImageTag;
import com.myimages.dto.ImageTagDTO;
import com.myimages.dto.ImageTagSearchDTO;
import com.myimages.dto.ImageTagPageDTO;
import com.myimages.dto.ImageTagConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.ImageTagService;
import com.myimages.util.ControllerUtils;

@Service
public class ImageTagServiceImpl extends GenericServiceImpl<ImageTag, Integer> implements ImageTagService {

    private final static Logger logger = LoggerFactory.getLogger(ImageTagServiceImpl.class);

	@Autowired
	ImageTagDAO imageTagDao;

	@Override
	public GenericDAO<ImageTag, Integer> getDAO() {
		return (GenericDAO<ImageTag, Integer>) imageTagDao;
	}
	
	public List<ImageTag> findAll () {
		List<ImageTag> imageTags = imageTagDao.findAll();
		
		return imageTags;	
		
	}

	public ResultDTO addImageTag(ImageTagDTO imageTagDTO, RequestDTO requestDTO) {

		ImageTag imageTag = new ImageTag();

		imageTag.setImageTagId(imageTagDTO.getImageTagId());

		imageTag.setImageId(imageTagDTO.getImageId());

		imageTag.setTagId(imageTagDTO.getTagId());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		imageTag = imageTagDao.save(imageTag);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ImageTag> getAllImageTags(Pageable pageable) {
		return imageTagDao.findAll(pageable);
	}

	public Page<ImageTag> getAllImageTags(Specification<ImageTag> spec, Pageable pageable) {
		return imageTagDao.findAll(spec, pageable);
	}

	public ResponseEntity<ImageTagPageDTO> getImageTags(ImageTagSearchDTO imageTagSearchDTO) {
	
			Integer imageTagId = imageTagSearchDTO.getImageTagId(); 
 			Integer imageId = imageTagSearchDTO.getImageId(); 
 			Integer tagId = imageTagSearchDTO.getTagId(); 
 			String sortBy = imageTagSearchDTO.getSortBy();
			String sortOrder = imageTagSearchDTO.getSortOrder();
			String searchQuery = imageTagSearchDTO.getSearchQuery();
			Integer page = imageTagSearchDTO.getPage();
			Integer size = imageTagSearchDTO.getSize();

	        Specification<ImageTag> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, imageTagId, "imageTagId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, imageId, "imageId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, tagId, "tagId"); 
			

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

		Page<ImageTag> imageTags = this.getAllImageTags(spec, pageable);
		
		//System.out.println(String.valueOf(imageTags.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(imageTags.getTotalPages()));
		
		List<ImageTag> imageTagsList = imageTags.getContent();
		
		ImageTagConvertCriteriaDTO convertCriteria = new ImageTagConvertCriteriaDTO();
		List<ImageTagDTO> imageTagDTOs = this.convertImageTagsToImageTagDTOs(imageTagsList,convertCriteria);
		
		ImageTagPageDTO imageTagPageDTO = new ImageTagPageDTO();
		imageTagPageDTO.setImageTags(imageTagDTOs);
		imageTagPageDTO.setTotalElements(imageTags.getTotalElements());
		return ResponseEntity.ok(imageTagPageDTO);
	}

	public List<ImageTagDTO> convertImageTagsToImageTagDTOs(List<ImageTag> imageTags, ImageTagConvertCriteriaDTO convertCriteria) {
		
		List<ImageTagDTO> imageTagDTOs = new ArrayList<ImageTagDTO>();
		
		for (ImageTag imageTag : imageTags) {
			imageTagDTOs.add(convertImageTagToImageTagDTO(imageTag,convertCriteria));
		}
		
		return imageTagDTOs;

	}
	
	public ImageTagDTO convertImageTagToImageTagDTO(ImageTag imageTag, ImageTagConvertCriteriaDTO convertCriteria) {
		
		ImageTagDTO imageTagDTO = new ImageTagDTO();

		imageTagDTO.setImageTagId(imageTag.getImageTagId());

		imageTagDTO.setImageId(imageTag.getImageId());

		imageTagDTO.setTagId(imageTag.getTagId());
		
		return imageTagDTO;
	}

	public ResultDTO updateImageTag(ImageTagDTO imageTagDTO, RequestDTO requestDTO) {
		
		ImageTag imageTag = imageTagDao.getById(imageTagDTO.getImageTagId());
		
		imageTag.setImageTagId(ControllerUtils.setValue(imageTag.getImageTagId(), imageTagDTO.getImageTagId()));
		
		imageTag.setImageId(ControllerUtils.setValue(imageTag.getImageId(), imageTagDTO.getImageId()));
		
		imageTag.setTagId(ControllerUtils.setValue(imageTag.getTagId(), imageTagDTO.getTagId()));

        imageTag = imageTagDao.save(imageTag);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ImageTagDTO getImageTagDTOById(Integer imageTagId) {
	
		ImageTag imageTag = imageTagDao.getById(imageTagId);
		
		ImageTagConvertCriteriaDTO convertCriteria = new ImageTagConvertCriteriaDTO();
		return(this.convertImageTagToImageTagDTO(imageTag,convertCriteria));
	}
}
