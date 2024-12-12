package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.Gallery;
import com.myimages.dto.GalleryDTO;
import com.myimages.dto.GallerySearchDTO;
import com.myimages.dto.GalleryPageDTO;
import com.myimages.dto.GalleryConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface GalleryService extends GenericService<Gallery, Integer> {

	List<Gallery> findAll();

	ResultDTO addGallery(GalleryDTO galleryDTO, RequestDTO requestDTO);

	ResultDTO updateGallery(GalleryDTO galleryDTO, RequestDTO requestDTO);

    Page<Gallery> getAllGallerys(Pageable pageable);

    Page<Gallery> getAllGallerys(Specification<Gallery> spec, Pageable pageable);

	ResponseEntity<GalleryPageDTO> getGallerys(GallerySearchDTO gallerySearchDTO);
	
	List<GalleryDTO> convertGallerysToGalleryDTOs(List<Gallery> gallerys, GalleryConvertCriteriaDTO convertCriteria);

	GalleryDTO getGalleryDTOById(Integer galleryId);


}
