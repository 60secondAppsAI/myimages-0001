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
import com.myimages.dao.PromptConfigDAO;
import com.myimages.domain.PromptConfig;
import com.myimages.dto.PromptConfigDTO;
import com.myimages.dto.PromptConfigSearchDTO;
import com.myimages.dto.PromptConfigPageDTO;
import com.myimages.dto.PromptConfigConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.PromptConfigService;
import com.myimages.util.ControllerUtils;

@Service
public class PromptConfigServiceImpl extends GenericServiceImpl<PromptConfig, Integer> implements PromptConfigService {

    private final static Logger logger = LoggerFactory.getLogger(PromptConfigServiceImpl.class);

	@Autowired
	PromptConfigDAO promptConfigDao;

	@Override
	public GenericDAO<PromptConfig, Integer> getDAO() {
		return (GenericDAO<PromptConfig, Integer>) promptConfigDao;
	}
	
	public List<PromptConfig> findAll () {
		List<PromptConfig> promptConfigs = promptConfigDao.findAll();
		
		return promptConfigs;	
		
	}

	public ResultDTO addPromptConfig(PromptConfigDTO promptConfigDTO, RequestDTO requestDTO) {

		PromptConfig promptConfig = new PromptConfig();

		promptConfig.setPromptConfigId(promptConfigDTO.getPromptConfigId());

		promptConfig.setOwnerId(promptConfigDTO.getOwnerId());

		promptConfig.setCreatedOnId(promptConfigDTO.getCreatedOnId());

		promptConfig.setColor1(promptConfigDTO.getColor1());

		promptConfig.setColor2(promptConfigDTO.getColor2());

		promptConfig.setColor3(promptConfigDTO.getColor3());

		promptConfig.setStyle(promptConfigDTO.getStyle());

		promptConfig.setSponsor1(promptConfigDTO.getSponsor1());

		promptConfig.setSponsor2(promptConfigDTO.getSponsor2());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		promptConfig = promptConfigDao.save(promptConfig);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PromptConfig> getAllPromptConfigs(Pageable pageable) {
		return promptConfigDao.findAll(pageable);
	}

	public Page<PromptConfig> getAllPromptConfigs(Specification<PromptConfig> spec, Pageable pageable) {
		return promptConfigDao.findAll(spec, pageable);
	}

	public ResponseEntity<PromptConfigPageDTO> getPromptConfigs(PromptConfigSearchDTO promptConfigSearchDTO) {
	
			Integer promptConfigId = promptConfigSearchDTO.getPromptConfigId(); 
 			Integer ownerId = promptConfigSearchDTO.getOwnerId(); 
   			String color1 = promptConfigSearchDTO.getColor1(); 
 			String color2 = promptConfigSearchDTO.getColor2(); 
 			String color3 = promptConfigSearchDTO.getColor3(); 
 			String style = promptConfigSearchDTO.getStyle(); 
 			String sponsor1 = promptConfigSearchDTO.getSponsor1(); 
 			String sponsor2 = promptConfigSearchDTO.getSponsor2(); 
 			String sortBy = promptConfigSearchDTO.getSortBy();
			String sortOrder = promptConfigSearchDTO.getSortOrder();
			String searchQuery = promptConfigSearchDTO.getSearchQuery();
			Integer page = promptConfigSearchDTO.getPage();
			Integer size = promptConfigSearchDTO.getSize();

	        Specification<PromptConfig> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, promptConfigId, "promptConfigId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, ownerId, "ownerId"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, color1, "color1"); 
			
			spec = ControllerUtils.andIfNecessary(spec, color2, "color2"); 
			
			spec = ControllerUtils.andIfNecessary(spec, color3, "color3"); 
			
			spec = ControllerUtils.andIfNecessary(spec, style, "style"); 
			
			spec = ControllerUtils.andIfNecessary(spec, sponsor1, "sponsor1"); 
			
			spec = ControllerUtils.andIfNecessary(spec, sponsor2, "sponsor2"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("color1")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("color2")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("color3")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("style")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("sponsor1")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("sponsor2")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<PromptConfig> promptConfigs = this.getAllPromptConfigs(spec, pageable);
		
		//System.out.println(String.valueOf(promptConfigs.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(promptConfigs.getTotalPages()));
		
		List<PromptConfig> promptConfigsList = promptConfigs.getContent();
		
		PromptConfigConvertCriteriaDTO convertCriteria = new PromptConfigConvertCriteriaDTO();
		List<PromptConfigDTO> promptConfigDTOs = this.convertPromptConfigsToPromptConfigDTOs(promptConfigsList,convertCriteria);
		
		PromptConfigPageDTO promptConfigPageDTO = new PromptConfigPageDTO();
		promptConfigPageDTO.setPromptConfigs(promptConfigDTOs);
		promptConfigPageDTO.setTotalElements(promptConfigs.getTotalElements());
		return ResponseEntity.ok(promptConfigPageDTO);
	}

	public List<PromptConfigDTO> convertPromptConfigsToPromptConfigDTOs(List<PromptConfig> promptConfigs, PromptConfigConvertCriteriaDTO convertCriteria) {
		
		List<PromptConfigDTO> promptConfigDTOs = new ArrayList<PromptConfigDTO>();
		
		for (PromptConfig promptConfig : promptConfigs) {
			promptConfigDTOs.add(convertPromptConfigToPromptConfigDTO(promptConfig,convertCriteria));
		}
		
		return promptConfigDTOs;

	}
	
	public PromptConfigDTO convertPromptConfigToPromptConfigDTO(PromptConfig promptConfig, PromptConfigConvertCriteriaDTO convertCriteria) {
		
		PromptConfigDTO promptConfigDTO = new PromptConfigDTO();

		promptConfigDTO.setPromptConfigId(promptConfig.getPromptConfigId());

		promptConfigDTO.setOwnerId(promptConfig.getOwnerId());

		promptConfigDTO.setCreatedOnId(promptConfig.getCreatedOnId());

		promptConfigDTO.setColor1(promptConfig.getColor1());

		promptConfigDTO.setColor2(promptConfig.getColor2());

		promptConfigDTO.setColor3(promptConfig.getColor3());

		promptConfigDTO.setStyle(promptConfig.getStyle());

		promptConfigDTO.setSponsor1(promptConfig.getSponsor1());

		promptConfigDTO.setSponsor2(promptConfig.getSponsor2());
		
		return promptConfigDTO;
	}

	public ResultDTO updatePromptConfig(PromptConfigDTO promptConfigDTO, RequestDTO requestDTO) {
		
		PromptConfig promptConfig = promptConfigDao.getById(promptConfigDTO.getPromptConfigId());
		
		promptConfig.setPromptConfigId(ControllerUtils.setValue(promptConfig.getPromptConfigId(), promptConfigDTO.getPromptConfigId()));
		
		promptConfig.setOwnerId(ControllerUtils.setValue(promptConfig.getOwnerId(), promptConfigDTO.getOwnerId()));
		
		promptConfig.setCreatedOnId(ControllerUtils.setValue(promptConfig.getCreatedOnId(), promptConfigDTO.getCreatedOnId()));
		
		promptConfig.setColor1(ControllerUtils.setValue(promptConfig.getColor1(), promptConfigDTO.getColor1()));
		
		promptConfig.setColor2(ControllerUtils.setValue(promptConfig.getColor2(), promptConfigDTO.getColor2()));
		
		promptConfig.setColor3(ControllerUtils.setValue(promptConfig.getColor3(), promptConfigDTO.getColor3()));
		
		promptConfig.setStyle(ControllerUtils.setValue(promptConfig.getStyle(), promptConfigDTO.getStyle()));
		
		promptConfig.setSponsor1(ControllerUtils.setValue(promptConfig.getSponsor1(), promptConfigDTO.getSponsor1()));
		
		promptConfig.setSponsor2(ControllerUtils.setValue(promptConfig.getSponsor2(), promptConfigDTO.getSponsor2()));

        promptConfig = promptConfigDao.save(promptConfig);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PromptConfigDTO getPromptConfigDTOById(Integer promptConfigId) {
	
		PromptConfig promptConfig = promptConfigDao.getById(promptConfigId);
		
		PromptConfigConvertCriteriaDTO convertCriteria = new PromptConfigConvertCriteriaDTO();
		return(this.convertPromptConfigToPromptConfigDTO(promptConfig,convertCriteria));
	}
}
