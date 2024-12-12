package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.UserProfile;
import com.myimages.dto.UserProfileDTO;
import com.myimages.dto.UserProfileSearchDTO;
import com.myimages.dto.UserProfilePageDTO;
import com.myimages.dto.UserProfileConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface UserProfileService extends GenericService<UserProfile, Integer> {

	List<UserProfile> findAll();

	ResultDTO addUserProfile(UserProfileDTO userProfileDTO, RequestDTO requestDTO);

	ResultDTO updateUserProfile(UserProfileDTO userProfileDTO, RequestDTO requestDTO);

    Page<UserProfile> getAllUserProfiles(Pageable pageable);

    Page<UserProfile> getAllUserProfiles(Specification<UserProfile> spec, Pageable pageable);

	ResponseEntity<UserProfilePageDTO> getUserProfiles(UserProfileSearchDTO userProfileSearchDTO);
	
	List<UserProfileDTO> convertUserProfilesToUserProfileDTOs(List<UserProfile> userProfiles, UserProfileConvertCriteriaDTO convertCriteria);

	UserProfileDTO getUserProfileDTOById(Integer userProfileId);


}
