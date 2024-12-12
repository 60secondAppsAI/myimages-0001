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
import com.myimages.dao.OrganizationDAO;
import com.myimages.domain.Organization;
import com.myimages.dto.OrganizationDTO;
import com.myimages.dto.OrganizationSearchDTO;
import com.myimages.dto.OrganizationPageDTO;
import com.myimages.dto.OrganizationConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.OrganizationService;
import com.myimages.util.ControllerUtils;

@Service
public class OrganizationServiceImpl extends GenericServiceImpl<Organization, Integer> implements OrganizationService {

    private final static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	@Autowired
	OrganizationDAO organizationDao;

	@Override
	public GenericDAO<Organization, Integer> getDAO() {
		return (GenericDAO<Organization, Integer>) organizationDao;
	}
	
	public List<Organization> findAll () {
		List<Organization> organizations = organizationDao.findAll();
		
		return organizations;	
		
	}

	public ResultDTO addOrganization(OrganizationDTO organizationDTO, RequestDTO requestDTO) {

		Organization organization = new Organization();

		organization.setOrganizationId(organizationDTO.getOrganizationId());

		organization.setOrganizationName(organizationDTO.getOrganizationName());

		organization.setContactInformation(organizationDTO.getContactInformation());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		organization = organizationDao.save(organization);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Organization> getAllOrganizations(Pageable pageable) {
		return organizationDao.findAll(pageable);
	}

	public Page<Organization> getAllOrganizations(Specification<Organization> spec, Pageable pageable) {
		return organizationDao.findAll(spec, pageable);
	}

	public ResponseEntity<OrganizationPageDTO> getOrganizations(OrganizationSearchDTO organizationSearchDTO) {
	
			Integer organizationId = organizationSearchDTO.getOrganizationId(); 
 			String organizationName = organizationSearchDTO.getOrganizationName(); 
 			String contactInformation = organizationSearchDTO.getContactInformation(); 
 			String sortBy = organizationSearchDTO.getSortBy();
			String sortOrder = organizationSearchDTO.getSortOrder();
			String searchQuery = organizationSearchDTO.getSearchQuery();
			Integer page = organizationSearchDTO.getPage();
			Integer size = organizationSearchDTO.getSize();

	        Specification<Organization> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, organizationId, "organizationId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, organizationName, "organizationName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, contactInformation, "contactInformation"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("organizationName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("contactInformation")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Organization> organizations = this.getAllOrganizations(spec, pageable);
		
		//System.out.println(String.valueOf(organizations.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(organizations.getTotalPages()));
		
		List<Organization> organizationsList = organizations.getContent();
		
		OrganizationConvertCriteriaDTO convertCriteria = new OrganizationConvertCriteriaDTO();
		List<OrganizationDTO> organizationDTOs = this.convertOrganizationsToOrganizationDTOs(organizationsList,convertCriteria);
		
		OrganizationPageDTO organizationPageDTO = new OrganizationPageDTO();
		organizationPageDTO.setOrganizations(organizationDTOs);
		organizationPageDTO.setTotalElements(organizations.getTotalElements());
		return ResponseEntity.ok(organizationPageDTO);
	}

	public List<OrganizationDTO> convertOrganizationsToOrganizationDTOs(List<Organization> organizations, OrganizationConvertCriteriaDTO convertCriteria) {
		
		List<OrganizationDTO> organizationDTOs = new ArrayList<OrganizationDTO>();
		
		for (Organization organization : organizations) {
			organizationDTOs.add(convertOrganizationToOrganizationDTO(organization,convertCriteria));
		}
		
		return organizationDTOs;

	}
	
	public OrganizationDTO convertOrganizationToOrganizationDTO(Organization organization, OrganizationConvertCriteriaDTO convertCriteria) {
		
		OrganizationDTO organizationDTO = new OrganizationDTO();

		organizationDTO.setOrganizationId(organization.getOrganizationId());

		organizationDTO.setOrganizationName(organization.getOrganizationName());

		organizationDTO.setContactInformation(organization.getContactInformation());
		
		return organizationDTO;
	}

	public ResultDTO updateOrganization(OrganizationDTO organizationDTO, RequestDTO requestDTO) {
		
		Organization organization = organizationDao.getById(organizationDTO.getOrganizationId());
		
		organization.setOrganizationId(ControllerUtils.setValue(organization.getOrganizationId(), organizationDTO.getOrganizationId()));
		
		organization.setOrganizationName(ControllerUtils.setValue(organization.getOrganizationName(), organizationDTO.getOrganizationName()));
		
		organization.setContactInformation(ControllerUtils.setValue(organization.getContactInformation(), organizationDTO.getContactInformation()));

        organization = organizationDao.save(organization);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public OrganizationDTO getOrganizationDTOById(Integer organizationId) {
	
		Organization organization = organizationDao.getById(organizationId);
		
		OrganizationConvertCriteriaDTO convertCriteria = new OrganizationConvertCriteriaDTO();
		return(this.convertOrganizationToOrganizationDTO(organization,convertCriteria));
	}
}
