package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.Tag;
import com.myimages.dto.TagDTO;
import com.myimages.dto.TagSearchDTO;
import com.myimages.dto.TagPageDTO;
import com.myimages.dto.TagConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TagService extends GenericService<Tag, Integer> {

	List<Tag> findAll();

	ResultDTO addTag(TagDTO tagDTO, RequestDTO requestDTO);

	ResultDTO updateTag(TagDTO tagDTO, RequestDTO requestDTO);

    Page<Tag> getAllTags(Pageable pageable);

    Page<Tag> getAllTags(Specification<Tag> spec, Pageable pageable);

	ResponseEntity<TagPageDTO> getTags(TagSearchDTO tagSearchDTO);
	
	List<TagDTO> convertTagsToTagDTOs(List<Tag> tags, TagConvertCriteriaDTO convertCriteria);

	TagDTO getTagDTOById(Integer tagId);


}
