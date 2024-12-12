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
import com.myimages.dao.GalleryImageDAO;
import com.myimages.domain.GalleryImage;
import com.myimages.dto.GalleryImageDTO;
import com.myimages.dto.GalleryImageSearchDTO;
import com.myimages.dto.GalleryImagePageDTO;
import com.myimages.dto.GalleryImageConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.GalleryImageService;
import com.myimages.util.ControllerUtils;

@Service
public class GalleryImageServiceImpl extends GenericServiceImpl<GalleryImage, Integer> implements GalleryImageService {

    private final static Logger logger = LoggerFactory.getLogger(GalleryImageServiceImpl.class);

	@Autowired
	GalleryImageDAO galleryImageDao;

	@Override
	public GenericDAO<GalleryImage, Integer> getDAO() {
		return (GenericDAO<GalleryImage, Integer>) galleryImageDao;
	}
	
	public List<GalleryImage> findAll () {
		List<GalleryImage> galleryImages = galleryImageDao.findAll();
		
		return galleryImages;	
		
	}

	public ResultDTO addGalleryImage(GalleryImageDTO galleryImageDTO, RequestDTO requestDTO) {

		GalleryImage galleryImage = new GalleryImage();

		galleryImage.setGalleryImageId(galleryImageDTO.getGalleryImageId());

		galleryImage.setGalleryId(galleryImageDTO.getGalleryId());

		galleryImage.setImageId(galleryImageDTO.getImageId());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		galleryImage = galleryImageDao.save(galleryImage);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<GalleryImage> getAllGalleryImages(Pageable pageable) {
		return galleryImageDao.findAll(pageable);
	}

	public Page<GalleryImage> getAllGalleryImages(Specification<GalleryImage> spec, Pageable pageable) {
		return galleryImageDao.findAll(spec, pageable);
	}

	public ResponseEntity<GalleryImagePageDTO> getGalleryImages(GalleryImageSearchDTO galleryImageSearchDTO) {
	
			Integer galleryImageId = galleryImageSearchDTO.getGalleryImageId(); 
 			Integer galleryId = galleryImageSearchDTO.getGalleryId(); 
 			Integer imageId = galleryImageSearchDTO.getImageId(); 
 			String sortBy = galleryImageSearchDTO.getSortBy();
			String sortOrder = galleryImageSearchDTO.getSortOrder();
			String searchQuery = galleryImageSearchDTO.getSearchQuery();
			Integer page = galleryImageSearchDTO.getPage();
			Integer size = galleryImageSearchDTO.getSize();

	        Specification<GalleryImage> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, galleryImageId, "galleryImageId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, galleryId, "galleryId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, imageId, "imageId"); 
			

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

		Page<GalleryImage> galleryImages = this.getAllGalleryImages(spec, pageable);
		
		//System.out.println(String.valueOf(galleryImages.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(galleryImages.getTotalPages()));
		
		List<GalleryImage> galleryImagesList = galleryImages.getContent();
		
		GalleryImageConvertCriteriaDTO convertCriteria = new GalleryImageConvertCriteriaDTO();
		List<GalleryImageDTO> galleryImageDTOs = this.convertGalleryImagesToGalleryImageDTOs(galleryImagesList,convertCriteria);
		
		GalleryImagePageDTO galleryImagePageDTO = new GalleryImagePageDTO();
		galleryImagePageDTO.setGalleryImages(galleryImageDTOs);
		galleryImagePageDTO.setTotalElements(galleryImages.getTotalElements());
		return ResponseEntity.ok(galleryImagePageDTO);
	}

	public List<GalleryImageDTO> convertGalleryImagesToGalleryImageDTOs(List<GalleryImage> galleryImages, GalleryImageConvertCriteriaDTO convertCriteria) {
		
		List<GalleryImageDTO> galleryImageDTOs = new ArrayList<GalleryImageDTO>();
		
		for (GalleryImage galleryImage : galleryImages) {
			galleryImageDTOs.add(convertGalleryImageToGalleryImageDTO(galleryImage,convertCriteria));
		}
		
		return galleryImageDTOs;

	}
	
	public GalleryImageDTO convertGalleryImageToGalleryImageDTO(GalleryImage galleryImage, GalleryImageConvertCriteriaDTO convertCriteria) {
		
		GalleryImageDTO galleryImageDTO = new GalleryImageDTO();

		galleryImageDTO.setGalleryImageId(galleryImage.getGalleryImageId());

		galleryImageDTO.setGalleryId(galleryImage.getGalleryId());

		galleryImageDTO.setImageId(galleryImage.getImageId());
		
		return galleryImageDTO;
	}

	public ResultDTO updateGalleryImage(GalleryImageDTO galleryImageDTO, RequestDTO requestDTO) {
		
		GalleryImage galleryImage = galleryImageDao.getById(galleryImageDTO.getGalleryImageId());
		
		galleryImage.setGalleryImageId(ControllerUtils.setValue(galleryImage.getGalleryImageId(), galleryImageDTO.getGalleryImageId()));
		
		galleryImage.setGalleryId(ControllerUtils.setValue(galleryImage.getGalleryId(), galleryImageDTO.getGalleryId()));
		
		galleryImage.setImageId(ControllerUtils.setValue(galleryImage.getImageId(), galleryImageDTO.getImageId()));

        galleryImage = galleryImageDao.save(galleryImage);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public GalleryImageDTO getGalleryImageDTOById(Integer galleryImageId) {
	
		GalleryImage galleryImage = galleryImageDao.getById(galleryImageId);
		
		GalleryImageConvertCriteriaDTO convertCriteria = new GalleryImageConvertCriteriaDTO();
		return(this.convertGalleryImageToGalleryImageDTO(galleryImage,convertCriteria));
	}
}
