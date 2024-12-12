package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.GalleryImage;
import com.myimages.dto.GalleryImageDTO;
import com.myimages.dto.GalleryImageSearchDTO;
import com.myimages.dto.GalleryImagePageDTO;
import com.myimages.dto.GalleryImageConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface GalleryImageService extends GenericService<GalleryImage, Integer> {

	List<GalleryImage> findAll();

	ResultDTO addGalleryImage(GalleryImageDTO galleryImageDTO, RequestDTO requestDTO);

	ResultDTO updateGalleryImage(GalleryImageDTO galleryImageDTO, RequestDTO requestDTO);

    Page<GalleryImage> getAllGalleryImages(Pageable pageable);

    Page<GalleryImage> getAllGalleryImages(Specification<GalleryImage> spec, Pageable pageable);

	ResponseEntity<GalleryImagePageDTO> getGalleryImages(GalleryImageSearchDTO galleryImageSearchDTO);
	
	List<GalleryImageDTO> convertGalleryImagesToGalleryImageDTOs(List<GalleryImage> galleryImages, GalleryImageConvertCriteriaDTO convertCriteria);

	GalleryImageDTO getGalleryImageDTOById(Integer galleryImageId);


}
