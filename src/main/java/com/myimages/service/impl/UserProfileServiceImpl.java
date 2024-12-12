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
import com.myimages.dao.UserProfileDAO;
import com.myimages.domain.UserProfile;
import com.myimages.dto.UserProfileDTO;
import com.myimages.dto.UserProfileSearchDTO;
import com.myimages.dto.UserProfilePageDTO;
import com.myimages.dto.UserProfileConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.UserProfileService;
import com.myimages.util.ControllerUtils;

@Service
public class UserProfileServiceImpl extends GenericServiceImpl<UserProfile, Integer> implements UserProfileService {

    private final static Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	@Autowired
	UserProfileDAO userProfileDao;

	@Override
	public GenericDAO<UserProfile, Integer> getDAO() {
		return (GenericDAO<UserProfile, Integer>) userProfileDao;
	}
	
	public List<UserProfile> findAll () {
		List<UserProfile> userProfiles = userProfileDao.findAll();
		
		return userProfiles;	
		
	}

	public ResultDTO addUserProfile(UserProfileDTO userProfileDTO, RequestDTO requestDTO) {

		UserProfile userProfile = new UserProfile();

		userProfile.setUserProfileId(userProfileDTO.getUserProfileId());

		userProfile.setUserName(userProfileDTO.getUserName());

		userProfile.setUserEmail(userProfileDTO.getUserEmail());

		userProfile.setCreatedDate(userProfileDTO.getCreatedDate());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		userProfile = userProfileDao.save(userProfile);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<UserProfile> getAllUserProfiles(Pageable pageable) {
		return userProfileDao.findAll(pageable);
	}

	public Page<UserProfile> getAllUserProfiles(Specification<UserProfile> spec, Pageable pageable) {
		return userProfileDao.findAll(spec, pageable);
	}

	public ResponseEntity<UserProfilePageDTO> getUserProfiles(UserProfileSearchDTO userProfileSearchDTO) {
	
			Integer userProfileId = userProfileSearchDTO.getUserProfileId(); 
 			String userName = userProfileSearchDTO.getUserName(); 
 			String userEmail = userProfileSearchDTO.getUserEmail(); 
   			String sortBy = userProfileSearchDTO.getSortBy();
			String sortOrder = userProfileSearchDTO.getSortOrder();
			String searchQuery = userProfileSearchDTO.getSearchQuery();
			Integer page = userProfileSearchDTO.getPage();
			Integer size = userProfileSearchDTO.getSize();

	        Specification<UserProfile> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, userProfileId, "userProfileId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, userName, "userName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, userEmail, "userEmail"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("userName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("userEmail")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<UserProfile> userProfiles = this.getAllUserProfiles(spec, pageable);
		
		//System.out.println(String.valueOf(userProfiles.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(userProfiles.getTotalPages()));
		
		List<UserProfile> userProfilesList = userProfiles.getContent();
		
		UserProfileConvertCriteriaDTO convertCriteria = new UserProfileConvertCriteriaDTO();
		List<UserProfileDTO> userProfileDTOs = this.convertUserProfilesToUserProfileDTOs(userProfilesList,convertCriteria);
		
		UserProfilePageDTO userProfilePageDTO = new UserProfilePageDTO();
		userProfilePageDTO.setUserProfiles(userProfileDTOs);
		userProfilePageDTO.setTotalElements(userProfiles.getTotalElements());
		return ResponseEntity.ok(userProfilePageDTO);
	}

	public List<UserProfileDTO> convertUserProfilesToUserProfileDTOs(List<UserProfile> userProfiles, UserProfileConvertCriteriaDTO convertCriteria) {
		
		List<UserProfileDTO> userProfileDTOs = new ArrayList<UserProfileDTO>();
		
		for (UserProfile userProfile : userProfiles) {
			userProfileDTOs.add(convertUserProfileToUserProfileDTO(userProfile,convertCriteria));
		}
		
		return userProfileDTOs;

	}
	
	public UserProfileDTO convertUserProfileToUserProfileDTO(UserProfile userProfile, UserProfileConvertCriteriaDTO convertCriteria) {
		
		UserProfileDTO userProfileDTO = new UserProfileDTO();

		userProfileDTO.setUserProfileId(userProfile.getUserProfileId());

		userProfileDTO.setUserName(userProfile.getUserName());

		userProfileDTO.setUserEmail(userProfile.getUserEmail());

		userProfileDTO.setCreatedDate(userProfile.getCreatedDate());
		
		return userProfileDTO;
	}

	public ResultDTO updateUserProfile(UserProfileDTO userProfileDTO, RequestDTO requestDTO) {
		
		UserProfile userProfile = userProfileDao.getById(userProfileDTO.getUserProfileId());
		
		userProfile.setUserProfileId(ControllerUtils.setValue(userProfile.getUserProfileId(), userProfileDTO.getUserProfileId()));
		
		userProfile.setUserName(ControllerUtils.setValue(userProfile.getUserName(), userProfileDTO.getUserName()));
		
		userProfile.setUserEmail(ControllerUtils.setValue(userProfile.getUserEmail(), userProfileDTO.getUserEmail()));
		
		userProfile.setCreatedDate(ControllerUtils.setValue(userProfile.getCreatedDate(), userProfileDTO.getCreatedDate()));

        userProfile = userProfileDao.save(userProfile);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public UserProfileDTO getUserProfileDTOById(Integer userProfileId) {
	
		UserProfile userProfile = userProfileDao.getById(userProfileId);
		
		UserProfileConvertCriteriaDTO convertCriteria = new UserProfileConvertCriteriaDTO();
		return(this.convertUserProfileToUserProfileDTO(userProfile,convertCriteria));
	}
}
