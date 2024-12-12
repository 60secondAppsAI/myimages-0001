package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.Invite;
import com.myimages.dto.InviteDTO;
import com.myimages.dto.InviteSearchDTO;
import com.myimages.dto.InvitePageDTO;
import com.myimages.dto.InviteConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InviteService extends GenericService<Invite, Integer> {

	List<Invite> findAll();

	ResultDTO addInvite(InviteDTO inviteDTO, RequestDTO requestDTO);

	ResultDTO updateInvite(InviteDTO inviteDTO, RequestDTO requestDTO);

    Page<Invite> getAllInvites(Pageable pageable);

    Page<Invite> getAllInvites(Specification<Invite> spec, Pageable pageable);

	ResponseEntity<InvitePageDTO> getInvites(InviteSearchDTO inviteSearchDTO);
	
	List<InviteDTO> convertInvitesToInviteDTOs(List<Invite> invites, InviteConvertCriteriaDTO convertCriteria);

	InviteDTO getInviteDTOById(Integer inviteId);


}
