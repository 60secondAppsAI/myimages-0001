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
import com.myimages.dao.ImageMetadataDAO;
import com.myimages.domain.ImageMetadata;
import com.myimages.dto.ImageMetadataDTO;
import com.myimages.dto.ImageMetadataSearchDTO;
import com.myimages.dto.ImageMetadataPageDTO;
import com.myimages.dto.ImageMetadataConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.ImageMetadataService;
import com.myimages.util.ControllerUtils;

@Service
public class ImageMetadataServiceImpl extends GenericServiceImpl<ImageMetadata, Integer> implements ImageMetadataService {

    private final static Logger logger = LoggerFactory.getLogger(ImageMetadataServiceImpl.class);

	@Autowired
	ImageMetadataDAO imageMetadataDao;

	@Override
	public GenericDAO<ImageMetadata, Integer> getDAO() {
		return (GenericDAO<ImageMetadata, Integer>) imageMetadataDao;
	}
	
	public List<ImageMetadata> findAll () {
		List<ImageMetadata> imageMetadatas = imageMetadataDao.findAll();
		
		return imageMetadatas;	
		
	}

	public ResultDTO addImageMetadata(ImageMetadataDTO imageMetadataDTO, RequestDTO requestDTO) {

		ImageMetadata imageMetadata = new ImageMetadata();

		imageMetadata.setImageMetadataId(imageMetadataDTO.getImageMetadataId());

		imageMetadata.setImageId(imageMetadataDTO.getImageId());

		imageMetadata.setResolution(imageMetadataDTO.getResolution());

		imageMetadata.setFormat(imageMetadataDTO.getFormat());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		imageMetadata = imageMetadataDao.save(imageMetadata);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ImageMetadata> getAllImageMetadatas(Pageable pageable) {
		return imageMetadataDao.findAll(pageable);
	}

	public Page<ImageMetadata> getAllImageMetadatas(Specification<ImageMetadata> spec, Pageable pageable) {
		return imageMetadataDao.findAll(spec, pageable);
	}

	public ResponseEntity<ImageMetadataPageDTO> getImageMetadatas(ImageMetadataSearchDTO imageMetadataSearchDTO) {
	
			Integer imageMetadataId = imageMetadataSearchDTO.getImageMetadataId(); 
 			Integer imageId = imageMetadataSearchDTO.getImageId(); 
 			String resolution = imageMetadataSearchDTO.getResolution(); 
 			String format = imageMetadataSearchDTO.getFormat(); 
 			String sortBy = imageMetadataSearchDTO.getSortBy();
			String sortOrder = imageMetadataSearchDTO.getSortOrder();
			String searchQuery = imageMetadataSearchDTO.getSearchQuery();
			Integer page = imageMetadataSearchDTO.getPage();
			Integer size = imageMetadataSearchDTO.getSize();

	        Specification<ImageMetadata> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, imageMetadataId, "imageMetadataId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, imageId, "imageId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, resolution, "resolution"); 
			
			spec = ControllerUtils.andIfNecessary(spec, format, "format"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("resolution")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("format")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<ImageMetadata> imageMetadatas = this.getAllImageMetadatas(spec, pageable);
		
		//System.out.println(String.valueOf(imageMetadatas.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(imageMetadatas.getTotalPages()));
		
		List<ImageMetadata> imageMetadatasList = imageMetadatas.getContent();
		
		ImageMetadataConvertCriteriaDTO convertCriteria = new ImageMetadataConvertCriteriaDTO();
		List<ImageMetadataDTO> imageMetadataDTOs = this.convertImageMetadatasToImageMetadataDTOs(imageMetadatasList,convertCriteria);
		
		ImageMetadataPageDTO imageMetadataPageDTO = new ImageMetadataPageDTO();
		imageMetadataPageDTO.setImageMetadatas(imageMetadataDTOs);
		imageMetadataPageDTO.setTotalElements(imageMetadatas.getTotalElements());
		return ResponseEntity.ok(imageMetadataPageDTO);
	}

	public List<ImageMetadataDTO> convertImageMetadatasToImageMetadataDTOs(List<ImageMetadata> imageMetadatas, ImageMetadataConvertCriteriaDTO convertCriteria) {
		
		List<ImageMetadataDTO> imageMetadataDTOs = new ArrayList<ImageMetadataDTO>();
		
		for (ImageMetadata imageMetadata : imageMetadatas) {
			imageMetadataDTOs.add(convertImageMetadataToImageMetadataDTO(imageMetadata,convertCriteria));
		}
		
		return imageMetadataDTOs;

	}
	
	public ImageMetadataDTO convertImageMetadataToImageMetadataDTO(ImageMetadata imageMetadata, ImageMetadataConvertCriteriaDTO convertCriteria) {
		
		ImageMetadataDTO imageMetadataDTO = new ImageMetadataDTO();

		imageMetadataDTO.setImageMetadataId(imageMetadata.getImageMetadataId());

		imageMetadataDTO.setImageId(imageMetadata.getImageId());

		imageMetadataDTO.setResolution(imageMetadata.getResolution());

		imageMetadataDTO.setFormat(imageMetadata.getFormat());
		
		return imageMetadataDTO;
	}

	public ResultDTO updateImageMetadata(ImageMetadataDTO imageMetadataDTO, RequestDTO requestDTO) {
		
		ImageMetadata imageMetadata = imageMetadataDao.getById(imageMetadataDTO.getImageMetadataId());
		
		imageMetadata.setImageMetadataId(ControllerUtils.setValue(imageMetadata.getImageMetadataId(), imageMetadataDTO.getImageMetadataId()));
		
		imageMetadata.setImageId(ControllerUtils.setValue(imageMetadata.getImageId(), imageMetadataDTO.getImageId()));
		
		imageMetadata.setResolution(ControllerUtils.setValue(imageMetadata.getResolution(), imageMetadataDTO.getResolution()));
		
		imageMetadata.setFormat(ControllerUtils.setValue(imageMetadata.getFormat(), imageMetadataDTO.getFormat()));

        imageMetadata = imageMetadataDao.save(imageMetadata);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ImageMetadataDTO getImageMetadataDTOById(Integer imageMetadataId) {
	
		ImageMetadata imageMetadata = imageMetadataDao.getById(imageMetadataId);
		
		ImageMetadataConvertCriteriaDTO convertCriteria = new ImageMetadataConvertCriteriaDTO();
		return(this.convertImageMetadataToImageMetadataDTO(imageMetadata,convertCriteria));
	}
}
