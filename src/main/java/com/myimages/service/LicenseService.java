package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.License;
import com.myimages.dto.LicenseDTO;
import com.myimages.dto.LicenseSearchDTO;
import com.myimages.dto.LicensePageDTO;
import com.myimages.dto.LicenseConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface LicenseService extends GenericService<License, Integer> {

	List<License> findAll();

	ResultDTO addLicense(LicenseDTO licenseDTO, RequestDTO requestDTO);

	ResultDTO updateLicense(LicenseDTO licenseDTO, RequestDTO requestDTO);

    Page<License> getAllLicenses(Pageable pageable);

    Page<License> getAllLicenses(Specification<License> spec, Pageable pageable);

	ResponseEntity<LicensePageDTO> getLicenses(LicenseSearchDTO licenseSearchDTO);
	
	List<LicenseDTO> convertLicensesToLicenseDTOs(List<License> licenses, LicenseConvertCriteriaDTO convertCriteria);

	LicenseDTO getLicenseDTOById(Integer licenseId);


}
