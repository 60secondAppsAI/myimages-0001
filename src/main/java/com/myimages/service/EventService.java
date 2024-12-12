package com.myimages.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.myimages.domain.Event;
import com.myimages.dto.EventDTO;
import com.myimages.dto.EventSearchDTO;
import com.myimages.dto.EventPageDTO;
import com.myimages.dto.EventConvertCriteriaDTO;
import com.myimages.service.GenericService;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface EventService extends GenericService<Event, Integer> {

	List<Event> findAll();

	ResultDTO addEvent(EventDTO eventDTO, RequestDTO requestDTO);

	ResultDTO updateEvent(EventDTO eventDTO, RequestDTO requestDTO);

    Page<Event> getAllEvents(Pageable pageable);

    Page<Event> getAllEvents(Specification<Event> spec, Pageable pageable);

	ResponseEntity<EventPageDTO> getEvents(EventSearchDTO eventSearchDTO);
	
	List<EventDTO> convertEventsToEventDTOs(List<Event> events, EventConvertCriteriaDTO convertCriteria);

	EventDTO getEventDTOById(Integer eventId);


}
