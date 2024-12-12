package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.Settings;
import com.myimages.dto.SettingsDTO;
import com.myimages.dto.SettingsSearchDTO;
import com.myimages.dto.SettingsPageDTO;
import com.myimages.dto.SettingsConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SettingsService extends GenericService<Settings, Integer> {

	List<Settings> findAll();

	ResultDTO addSettings(SettingsDTO settingsDTO, RequestDTO requestDTO);

	ResultDTO updateSettings(SettingsDTO settingsDTO, RequestDTO requestDTO);

    Page<Settings> getAllSettingss(Pageable pageable);

    Page<Settings> getAllSettingss(Specification<Settings> spec, Pageable pageable);

	ResponseEntity<SettingsPageDTO> getSettingss(SettingsSearchDTO settingsSearchDTO);
	
	List<SettingsDTO> convertSettingssToSettingsDTOs(List<Settings> settingss, SettingsConvertCriteriaDTO convertCriteria);

	SettingsDTO getSettingsDTOById(Integer settingsId);


}
