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
import com.myimages.dao.InviteDAO;
import com.myimages.domain.Invite;
import com.myimages.dto.InviteDTO;
import com.myimages.dto.InviteSearchDTO;
import com.myimages.dto.InvitePageDTO;
import com.myimages.dto.InviteConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.InviteService;
import com.myimages.util.ControllerUtils;

@Service
public class InviteServiceImpl extends GenericServiceImpl<Invite, Integer> implements InviteService {

    private final static Logger logger = LoggerFactory.getLogger(InviteServiceImpl.class);

	@Autowired
	InviteDAO inviteDao;

	@Override
	public GenericDAO<Invite, Integer> getDAO() {
		return (GenericDAO<Invite, Integer>) inviteDao;
	}
	
	public List<Invite> findAll () {
		List<Invite> invites = inviteDao.findAll();
		
		return invites;	
		
	}

	public ResultDTO addInvite(InviteDTO inviteDTO, RequestDTO requestDTO) {

		Invite invite = new Invite();

		invite.setInviteId(inviteDTO.getInviteId());

		invite.setEventId(inviteDTO.getEventId());

		invite.setUserProfileId(inviteDTO.getUserProfileId());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		invite = inviteDao.save(invite);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Invite> getAllInvites(Pageable pageable) {
		return inviteDao.findAll(pageable);
	}

	public Page<Invite> getAllInvites(Specification<Invite> spec, Pageable pageable) {
		return inviteDao.findAll(spec, pageable);
	}

	public ResponseEntity<InvitePageDTO> getInvites(InviteSearchDTO inviteSearchDTO) {
	
			Integer inviteId = inviteSearchDTO.getInviteId(); 
 			Integer eventId = inviteSearchDTO.getEventId(); 
 			Integer userProfileId = inviteSearchDTO.getUserProfileId(); 
 			String sortBy = inviteSearchDTO.getSortBy();
			String sortOrder = inviteSearchDTO.getSortOrder();
			String searchQuery = inviteSearchDTO.getSearchQuery();
			Integer page = inviteSearchDTO.getPage();
			Integer size = inviteSearchDTO.getSize();

	        Specification<Invite> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, inviteId, "inviteId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, eventId, "eventId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, userProfileId, "userProfileId"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<Invite> invites = this.getAllInvites(spec, pageable);
		
		//System.out.println(String.valueOf(invites.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(invites.getTotalPages()));
		
		List<Invite> invitesList = invites.getContent();
		
		InviteConvertCriteriaDTO convertCriteria = new InviteConvertCriteriaDTO();
		List<InviteDTO> inviteDTOs = this.convertInvitesToInviteDTOs(invitesList,convertCriteria);
		
		InvitePageDTO invitePageDTO = new InvitePageDTO();
		invitePageDTO.setInvites(inviteDTOs);
		invitePageDTO.setTotalElements(invites.getTotalElements());
		return ResponseEntity.ok(invitePageDTO);
	}

	public List<InviteDTO> convertInvitesToInviteDTOs(List<Invite> invites, InviteConvertCriteriaDTO convertCriteria) {
		
		List<InviteDTO> inviteDTOs = new ArrayList<InviteDTO>();
		
		for (Invite invite : invites) {
			inviteDTOs.add(convertInviteToInviteDTO(invite,convertCriteria));
		}
		
		return inviteDTOs;

	}
	
	public InviteDTO convertInviteToInviteDTO(Invite invite, InviteConvertCriteriaDTO convertCriteria) {
		
		InviteDTO inviteDTO = new InviteDTO();

		inviteDTO.setInviteId(invite.getInviteId());

		inviteDTO.setEventId(invite.getEventId());

		inviteDTO.setUserProfileId(invite.getUserProfileId());
		
		return inviteDTO;
	}

	public ResultDTO updateInvite(InviteDTO inviteDTO, RequestDTO requestDTO) {
		
		Invite invite = inviteDao.getById(inviteDTO.getInviteId());
		
		invite.setInviteId(ControllerUtils.setValue(invite.getInviteId(), inviteDTO.getInviteId()));
		
		invite.setEventId(ControllerUtils.setValue(invite.getEventId(), inviteDTO.getEventId()));
		
		invite.setUserProfileId(ControllerUtils.setValue(invite.getUserProfileId(), inviteDTO.getUserProfileId()));

        invite = inviteDao.save(invite);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InviteDTO getInviteDTOById(Integer inviteId) {
	
		Invite invite = inviteDao.getById(inviteId);
		
		InviteConvertCriteriaDTO convertCriteria = new InviteConvertCriteriaDTO();
		return(this.convertInviteToInviteDTO(invite,convertCriteria));
	}
}
