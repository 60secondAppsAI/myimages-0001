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
import com.myimages.dao.LicenseDAO;
import com.myimages.domain.License;
import com.myimages.dto.LicenseDTO;
import com.myimages.dto.LicenseSearchDTO;
import com.myimages.dto.LicensePageDTO;
import com.myimages.dto.LicenseConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.LicenseService;
import com.myimages.util.ControllerUtils;

@Service
public class LicenseServiceImpl extends GenericServiceImpl<License, Integer> implements LicenseService {

    private final static Logger logger = LoggerFactory.getLogger(LicenseServiceImpl.class);

	@Autowired
	LicenseDAO licenseDao;

	@Override
	public GenericDAO<License, Integer> getDAO() {
		return (GenericDAO<License, Integer>) licenseDao;
	}
	
	public List<License> findAll () {
		List<License> licenses = licenseDao.findAll();
		
		return licenses;	
		
	}

	public ResultDTO addLicense(LicenseDTO licenseDTO, RequestDTO requestDTO) {

		License license = new License();

		license.setLicenseId(licenseDTO.getLicenseId());

		license.setOwnerId(licenseDTO.getOwnerId());

		license.setLicenseType(licenseDTO.getLicenseType());

		license.setValidUntil(licenseDTO.getValidUntil());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		license = licenseDao.save(license);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<License> getAllLicenses(Pageable pageable) {
		return licenseDao.findAll(pageable);
	}

	public Page<License> getAllLicenses(Specification<License> spec, Pageable pageable) {
		return licenseDao.findAll(spec, pageable);
	}

	public ResponseEntity<LicensePageDTO> getLicenses(LicenseSearchDTO licenseSearchDTO) {
	
			Integer licenseId = licenseSearchDTO.getLicenseId(); 
 			Integer ownerId = licenseSearchDTO.getOwnerId(); 
 			String licenseType = licenseSearchDTO.getLicenseType(); 
   			String sortBy = licenseSearchDTO.getSortBy();
			String sortOrder = licenseSearchDTO.getSortOrder();
			String searchQuery = licenseSearchDTO.getSearchQuery();
			Integer page = licenseSearchDTO.getPage();
			Integer size = licenseSearchDTO.getSize();

	        Specification<License> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, licenseId, "licenseId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, ownerId, "ownerId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, licenseType, "licenseType"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("licenseType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<License> licenses = this.getAllLicenses(spec, pageable);
		
		//System.out.println(String.valueOf(licenses.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(licenses.getTotalPages()));
		
		List<License> licensesList = licenses.getContent();
		
		LicenseConvertCriteriaDTO convertCriteria = new LicenseConvertCriteriaDTO();
		List<LicenseDTO> licenseDTOs = this.convertLicensesToLicenseDTOs(licensesList,convertCriteria);
		
		LicensePageDTO licensePageDTO = new LicensePageDTO();
		licensePageDTO.setLicenses(licenseDTOs);
		licensePageDTO.setTotalElements(licenses.getTotalElements());
		return ResponseEntity.ok(licensePageDTO);
	}

	public List<LicenseDTO> convertLicensesToLicenseDTOs(List<License> licenses, LicenseConvertCriteriaDTO convertCriteria) {
		
		List<LicenseDTO> licenseDTOs = new ArrayList<LicenseDTO>();
		
		for (License license : licenses) {
			licenseDTOs.add(convertLicenseToLicenseDTO(license,convertCriteria));
		}
		
		return licenseDTOs;

	}
	
	public LicenseDTO convertLicenseToLicenseDTO(License license, LicenseConvertCriteriaDTO convertCriteria) {
		
		LicenseDTO licenseDTO = new LicenseDTO();

		licenseDTO.setLicenseId(license.getLicenseId());

		licenseDTO.setOwnerId(license.getOwnerId());

		licenseDTO.setLicenseType(license.getLicenseType());

		licenseDTO.setValidUntil(license.getValidUntil());
		
		return licenseDTO;
	}

	public ResultDTO updateLicense(LicenseDTO licenseDTO, RequestDTO requestDTO) {
		
		License license = licenseDao.getById(licenseDTO.getLicenseId());
		
		license.setLicenseId(ControllerUtils.setValue(license.getLicenseId(), licenseDTO.getLicenseId()));
		
		license.setOwnerId(ControllerUtils.setValue(license.getOwnerId(), licenseDTO.getOwnerId()));
		
		license.setLicenseType(ControllerUtils.setValue(license.getLicenseType(), licenseDTO.getLicenseType()));
		
		license.setValidUntil(ControllerUtils.setValue(license.getValidUntil(), licenseDTO.getValidUntil()));

        license = licenseDao.save(license);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public LicenseDTO getLicenseDTOById(Integer licenseId) {
	
		License license = licenseDao.getById(licenseId);
		
		LicenseConvertCriteriaDTO convertCriteria = new LicenseConvertCriteriaDTO();
		return(this.convertLicenseToLicenseDTO(license,convertCriteria));
	}
}
