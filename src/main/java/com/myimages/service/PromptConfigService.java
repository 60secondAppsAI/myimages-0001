package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.PromptConfig;
import com.myimages.dto.PromptConfigDTO;
import com.myimages.dto.PromptConfigSearchDTO;
import com.myimages.dto.PromptConfigPageDTO;
import com.myimages.dto.PromptConfigConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PromptConfigService extends GenericService<PromptConfig, Integer> {

	List<PromptConfig> findAll();

	ResultDTO addPromptConfig(PromptConfigDTO promptConfigDTO, RequestDTO requestDTO);

	ResultDTO updatePromptConfig(PromptConfigDTO promptConfigDTO, RequestDTO requestDTO);

    Page<PromptConfig> getAllPromptConfigs(Pageable pageable);

    Page<PromptConfig> getAllPromptConfigs(Specification<PromptConfig> spec, Pageable pageable);

	ResponseEntity<PromptConfigPageDTO> getPromptConfigs(PromptConfigSearchDTO promptConfigSearchDTO);
	
	List<PromptConfigDTO> convertPromptConfigsToPromptConfigDTOs(List<PromptConfig> promptConfigs, PromptConfigConvertCriteriaDTO convertCriteria);

	PromptConfigDTO getPromptConfigDTOById(Integer promptConfigId);


}
