package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.UserRole;
import com.myimages.dto.UserRoleDTO;
import com.myimages.dto.UserRoleSearchDTO;
import com.myimages.dto.UserRolePageDTO;
import com.myimages.dto.UserRoleConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface UserRoleService extends GenericService<UserRole, Integer> {

	List<UserRole> findAll();

	ResultDTO addUserRole(UserRoleDTO userRoleDTO, RequestDTO requestDTO);

	ResultDTO updateUserRole(UserRoleDTO userRoleDTO, RequestDTO requestDTO);

    Page<UserRole> getAllUserRoles(Pageable pageable);

    Page<UserRole> getAllUserRoles(Specification<UserRole> spec, Pageable pageable);

	ResponseEntity<UserRolePageDTO> getUserRoles(UserRoleSearchDTO userRoleSearchDTO);
	
	List<UserRoleDTO> convertUserRolesToUserRoleDTOs(List<UserRole> userRoles, UserRoleConvertCriteriaDTO convertCriteria);

	UserRoleDTO getUserRoleDTOById(Integer userRoleId);


}
