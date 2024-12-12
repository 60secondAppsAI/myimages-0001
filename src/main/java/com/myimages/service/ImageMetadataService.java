package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.ImageMetadata;
import com.myimages.dto.ImageMetadataDTO;
import com.myimages.dto.ImageMetadataSearchDTO;
import com.myimages.dto.ImageMetadataPageDTO;
import com.myimages.dto.ImageMetadataConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ImageMetadataService extends GenericService<ImageMetadata, Integer> {

	List<ImageMetadata> findAll();

	ResultDTO addImageMetadata(ImageMetadataDTO imageMetadataDTO, RequestDTO requestDTO);

	ResultDTO updateImageMetadata(ImageMetadataDTO imageMetadataDTO, RequestDTO requestDTO);

    Page<ImageMetadata> getAllImageMetadatas(Pageable pageable);

    Page<ImageMetadata> getAllImageMetadatas(Specification<ImageMetadata> spec, Pageable pageable);

	ResponseEntity<ImageMetadataPageDTO> getImageMetadatas(ImageMetadataSearchDTO imageMetadataSearchDTO);
	
	List<ImageMetadataDTO> convertImageMetadatasToImageMetadataDTOs(List<ImageMetadata> imageMetadatas, ImageMetadataConvertCriteriaDTO convertCriteria);

	ImageMetadataDTO getImageMetadataDTOById(Integer imageMetadataId);


}
