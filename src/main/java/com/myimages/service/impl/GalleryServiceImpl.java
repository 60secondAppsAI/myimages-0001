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
import com.myimages.dao.GalleryDAO;
import com.myimages.domain.Gallery;
import com.myimages.dto.GalleryDTO;
import com.myimages.dto.GallerySearchDTO;
import com.myimages.dto.GalleryPageDTO;
import com.myimages.dto.GalleryConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.GalleryService;
import com.myimages.util.ControllerUtils;

@Service
public class GalleryServiceImpl extends GenericServiceImpl<Gallery, Integer> implements GalleryService {

    private final static Logger logger = LoggerFactory.getLogger(GalleryServiceImpl.class);

	@Autowired
	GalleryDAO galleryDao;

	@Override
	public GenericDAO<Gallery, Integer> getDAO() {
		return (GenericDAO<Gallery, Integer>) galleryDao;
	}
	
	public List<Gallery> findAll () {
		List<Gallery> gallerys = galleryDao.findAll();
		
		return gallerys;	
		
	}

	public ResultDTO addGallery(GalleryDTO galleryDTO, RequestDTO requestDTO) {

		Gallery gallery = new Gallery();

		gallery.setGalleryId(galleryDTO.getGalleryId());

		gallery.setOwnerId(galleryDTO.getOwnerId());

		gallery.setGalleryName(galleryDTO.getGalleryName());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		gallery = galleryDao.save(gallery);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Gallery> getAllGallerys(Pageable pageable) {
		return galleryDao.findAll(pageable);
	}

	public Page<Gallery> getAllGallerys(Specification<Gallery> spec, Pageable pageable) {
		return galleryDao.findAll(spec, pageable);
	}

	public ResponseEntity<GalleryPageDTO> getGallerys(GallerySearchDTO gallerySearchDTO) {
	
			Integer galleryId = gallerySearchDTO.getGalleryId(); 
 			Integer ownerId = gallerySearchDTO.getOwnerId(); 
 			String galleryName = gallerySearchDTO.getGalleryName(); 
 			String sortBy = gallerySearchDTO.getSortBy();
			String sortOrder = gallerySearchDTO.getSortOrder();
			String searchQuery = gallerySearchDTO.getSearchQuery();
			Integer page = gallerySearchDTO.getPage();
			Integer size = gallerySearchDTO.getSize();

	        Specification<Gallery> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, galleryId, "galleryId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, ownerId, "ownerId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, galleryName, "galleryName"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("galleryName")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Gallery> gallerys = this.getAllGallerys(spec, pageable);
		
		//System.out.println(String.valueOf(gallerys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(gallerys.getTotalPages()));
		
		List<Gallery> gallerysList = gallerys.getContent();
		
		GalleryConvertCriteriaDTO convertCriteria = new GalleryConvertCriteriaDTO();
		List<GalleryDTO> galleryDTOs = this.convertGallerysToGalleryDTOs(gallerysList,convertCriteria);
		
		GalleryPageDTO galleryPageDTO = new GalleryPageDTO();
		galleryPageDTO.setGallerys(galleryDTOs);
		galleryPageDTO.setTotalElements(gallerys.getTotalElements());
		return ResponseEntity.ok(galleryPageDTO);
	}

	public List<GalleryDTO> convertGallerysToGalleryDTOs(List<Gallery> gallerys, GalleryConvertCriteriaDTO convertCriteria) {
		
		List<GalleryDTO> galleryDTOs = new ArrayList<GalleryDTO>();
		
		for (Gallery gallery : gallerys) {
			galleryDTOs.add(convertGalleryToGalleryDTO(gallery,convertCriteria));
		}
		
		return galleryDTOs;

	}
	
	public GalleryDTO convertGalleryToGalleryDTO(Gallery gallery, GalleryConvertCriteriaDTO convertCriteria) {
		
		GalleryDTO galleryDTO = new GalleryDTO();

		galleryDTO.setGalleryId(gallery.getGalleryId());

		galleryDTO.setOwnerId(gallery.getOwnerId());

		galleryDTO.setGalleryName(gallery.getGalleryName());
		
		return galleryDTO;
	}

	public ResultDTO updateGallery(GalleryDTO galleryDTO, RequestDTO requestDTO) {
		
		Gallery gallery = galleryDao.getById(galleryDTO.getGalleryId());
		
		gallery.setGalleryId(ControllerUtils.setValue(gallery.getGalleryId(), galleryDTO.getGalleryId()));
		
		gallery.setOwnerId(ControllerUtils.setValue(gallery.getOwnerId(), galleryDTO.getOwnerId()));
		
		gallery.setGalleryName(ControllerUtils.setValue(gallery.getGalleryName(), galleryDTO.getGalleryName()));

        gallery = galleryDao.save(gallery);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public GalleryDTO getGalleryDTOById(Integer galleryId) {
	
		Gallery gallery = galleryDao.getById(galleryId);
		
		GalleryConvertCriteriaDTO convertCriteria = new GalleryConvertCriteriaDTO();
		return(this.convertGalleryToGalleryDTO(gallery,convertCriteria));
	}
}
