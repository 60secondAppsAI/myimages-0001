package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.Organization;
import com.myimages.dto.OrganizationDTO;
import com.myimages.dto.OrganizationSearchDTO;
import com.myimages.dto.OrganizationPageDTO;
import com.myimages.dto.OrganizationConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface OrganizationService extends GenericService<Organization, Integer> {

	List<Organization> findAll();

	ResultDTO addOrganization(OrganizationDTO organizationDTO, RequestDTO requestDTO);

	ResultDTO updateOrganization(OrganizationDTO organizationDTO, RequestDTO requestDTO);

    Page<Organization> getAllOrganizations(Pageable pageable);

    Page<Organization> getAllOrganizations(Specification<Organization> spec, Pageable pageable);

	ResponseEntity<OrganizationPageDTO> getOrganizations(OrganizationSearchDTO organizationSearchDTO);
	
	List<OrganizationDTO> convertOrganizationsToOrganizationDTOs(List<Organization> organizations, OrganizationConvertCriteriaDTO convertCriteria);

	OrganizationDTO getOrganizationDTOById(Integer organizationId);


}
