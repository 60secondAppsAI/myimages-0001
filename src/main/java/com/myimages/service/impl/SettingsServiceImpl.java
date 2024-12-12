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
import com.myimages.dao.SettingsDAO;
import com.myimages.domain.Settings;
import com.myimages.dto.SettingsDTO;
import com.myimages.dto.SettingsSearchDTO;
import com.myimages.dto.SettingsPageDTO;
import com.myimages.dto.SettingsConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.SettingsService;
import com.myimages.util.ControllerUtils;

@Service
public class SettingsServiceImpl extends GenericServiceImpl<Settings, Integer> implements SettingsService {

    private final static Logger logger = LoggerFactory.getLogger(SettingsServiceImpl.class);

	@Autowired
	SettingsDAO settingsDao;

	@Override
	public GenericDAO<Settings, Integer> getDAO() {
		return (GenericDAO<Settings, Integer>) settingsDao;
	}
	
	public List<Settings> findAll () {
		List<Settings> settingss = settingsDao.findAll();
		
		return settingss;	
		
	}

	public ResultDTO addSettings(SettingsDTO settingsDTO, RequestDTO requestDTO) {

		Settings settings = new Settings();

		settings.setSettingsId(settingsDTO.getSettingsId());

		settings.setUserProfileId(settingsDTO.getUserProfileId());

		settings.setTheme(settingsDTO.getTheme());

		settings.setNotificationEnabled(settingsDTO.getNotificationEnabled());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		settings = settingsDao.save(settings);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Settings> getAllSettingss(Pageable pageable) {
		return settingsDao.findAll(pageable);
	}

	public Page<Settings> getAllSettingss(Specification<Settings> spec, Pageable pageable) {
		return settingsDao.findAll(spec, pageable);
	}

	public ResponseEntity<SettingsPageDTO> getSettingss(SettingsSearchDTO settingsSearchDTO) {
	
			Integer settingsId = settingsSearchDTO.getSettingsId(); 
 			Integer userProfileId = settingsSearchDTO.getUserProfileId(); 
 			String theme = settingsSearchDTO.getTheme(); 
 			String notificationEnabled = settingsSearchDTO.getNotificationEnabled(); 
 			String sortBy = settingsSearchDTO.getSortBy();
			String sortOrder = settingsSearchDTO.getSortOrder();
			String searchQuery = settingsSearchDTO.getSearchQuery();
			Integer page = settingsSearchDTO.getPage();
			Integer size = settingsSearchDTO.getSize();

	        Specification<Settings> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, settingsId, "settingsId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, userProfileId, "userProfileId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, theme, "theme"); 
			
			spec = ControllerUtils.andIfNecessary(spec, notificationEnabled, "notificationEnabled"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("theme")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("notificationEnabled")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Settings> settingss = this.getAllSettingss(spec, pageable);
		
		//System.out.println(String.valueOf(settingss.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(settingss.getTotalPages()));
		
		List<Settings> settingssList = settingss.getContent();
		
		SettingsConvertCriteriaDTO convertCriteria = new SettingsConvertCriteriaDTO();
		List<SettingsDTO> settingsDTOs = this.convertSettingssToSettingsDTOs(settingssList,convertCriteria);
		
		SettingsPageDTO settingsPageDTO = new SettingsPageDTO();
		settingsPageDTO.setSettingss(settingsDTOs);
		settingsPageDTO.setTotalElements(settingss.getTotalElements());
		return ResponseEntity.ok(settingsPageDTO);
	}

	public List<SettingsDTO> convertSettingssToSettingsDTOs(List<Settings> settingss, SettingsConvertCriteriaDTO convertCriteria) {
		
		List<SettingsDTO> settingsDTOs = new ArrayList<SettingsDTO>();
		
		for (Settings settings : settingss) {
			settingsDTOs.add(convertSettingsToSettingsDTO(settings,convertCriteria));
		}
		
		return settingsDTOs;

	}
	
	public SettingsDTO convertSettingsToSettingsDTO(Settings settings, SettingsConvertCriteriaDTO convertCriteria) {
		
		SettingsDTO settingsDTO = new SettingsDTO();

		settingsDTO.setSettingsId(settings.getSettingsId());

		settingsDTO.setUserProfileId(settings.getUserProfileId());

		settingsDTO.setTheme(settings.getTheme());

		settingsDTO.setNotificationEnabled(settings.getNotificationEnabled());
		
		return settingsDTO;
	}

	public ResultDTO updateSettings(SettingsDTO settingsDTO, RequestDTO requestDTO) {
		
		Settings settings = settingsDao.getById(settingsDTO.getSettingsId());
		
		settings.setSettingsId(ControllerUtils.setValue(settings.getSettingsId(), settingsDTO.getSettingsId()));
		
		settings.setUserProfileId(ControllerUtils.setValue(settings.getUserProfileId(), settingsDTO.getUserProfileId()));
		
		settings.setTheme(ControllerUtils.setValue(settings.getTheme(), settingsDTO.getTheme()));
		
		settings.setNotificationEnabled(ControllerUtils.setValue(settings.getNotificationEnabled(), settingsDTO.getNotificationEnabled()));

        settings = settingsDao.save(settings);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SettingsDTO getSettingsDTOById(Integer settingsId) {
	
		Settings settings = settingsDao.getById(settingsId);
		
		SettingsConvertCriteriaDTO convertCriteria = new SettingsConvertCriteriaDTO();
		return(this.convertSettingsToSettingsDTO(settings,convertCriteria));
	}
}
