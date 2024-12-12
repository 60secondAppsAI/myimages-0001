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
import com.myimages.dao.EventDAO;
import com.myimages.domain.Event;
import com.myimages.dto.EventDTO;
import com.myimages.dto.EventSearchDTO;
import com.myimages.dto.EventPageDTO;
import com.myimages.dto.EventConvertCriteriaDTO;
import com.myimages.dto.common.RequestDTO;
import com.myimages.dto.common.ResultDTO;
import com.myimages.service.EventService;
import com.myimages.util.ControllerUtils;

@Service
public class EventServiceImpl extends GenericServiceImpl<Event, Integer> implements EventService {

    private final static Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	EventDAO eventDao;

	@Override
	public GenericDAO<Event, Integer> getDAO() {
		return (GenericDAO<Event, Integer>) eventDao;
	}
	
	public List<Event> findAll () {
		List<Event> events = eventDao.findAll();
		
		return events;	
		
	}

	public ResultDTO addEvent(EventDTO eventDTO, RequestDTO requestDTO) {

		Event event = new Event();

		event.setEventId(eventDTO.getEventId());

		event.setEventName(eventDTO.getEventName());

		event.setEventDate(eventDTO.getEventDate());

		event.setLocation(eventDTO.getLocation());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		event = eventDao.save(event);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Event> getAllEvents(Pageable pageable) {
		return eventDao.findAll(pageable);
	}

	public Page<Event> getAllEvents(Specification<Event> spec, Pageable pageable) {
		return eventDao.findAll(spec, pageable);
	}

	public ResponseEntity<EventPageDTO> getEvents(EventSearchDTO eventSearchDTO) {
	
			Integer eventId = eventSearchDTO.getEventId(); 
 			String eventName = eventSearchDTO.getEventName(); 
   			String location = eventSearchDTO.getLocation(); 
 			String sortBy = eventSearchDTO.getSortBy();
			String sortOrder = eventSearchDTO.getSortOrder();
			String searchQuery = eventSearchDTO.getSearchQuery();
			Integer page = eventSearchDTO.getPage();
			Integer size = eventSearchDTO.getSize();

	        Specification<Event> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, eventId, "eventId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, eventName, "eventName"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, location, "location"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("eventName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("location")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Event> events = this.getAllEvents(spec, pageable);
		
		//System.out.println(String.valueOf(events.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(events.getTotalPages()));
		
		List<Event> eventsList = events.getContent();
		
		EventConvertCriteriaDTO convertCriteria = new EventConvertCriteriaDTO();
		List<EventDTO> eventDTOs = this.convertEventsToEventDTOs(eventsList,convertCriteria);
		
		EventPageDTO eventPageDTO = new EventPageDTO();
		eventPageDTO.setEvents(eventDTOs);
		eventPageDTO.setTotalElements(events.getTotalElements());
		return ResponseEntity.ok(eventPageDTO);
	}

	public List<EventDTO> convertEventsToEventDTOs(List<Event> events, EventConvertCriteriaDTO convertCriteria) {
		
		List<EventDTO> eventDTOs = new ArrayList<EventDTO>();
		
		for (Event event : events) {
			eventDTOs.add(convertEventToEventDTO(event,convertCriteria));
		}
		
		return eventDTOs;

	}
	
	public EventDTO convertEventToEventDTO(Event event, EventConvertCriteriaDTO convertCriteria) {
		
		EventDTO eventDTO = new EventDTO();

		eventDTO.setEventId(event.getEventId());

		eventDTO.setEventName(event.getEventName());

		eventDTO.setEventDate(event.getEventDate());

		eventDTO.setLocation(event.getLocation());
		
		return eventDTO;
	}

	public ResultDTO updateEvent(EventDTO eventDTO, RequestDTO requestDTO) {
		
		Event event = eventDao.getById(eventDTO.getEventId());
		
		event.setEventId(ControllerUtils.setValue(event.getEventId(), eventDTO.getEventId()));
		
		event.setEventName(ControllerUtils.setValue(event.getEventName(), eventDTO.getEventName()));
		
		event.setEventDate(ControllerUtils.setValue(event.getEventDate(), eventDTO.getEventDate()));
		
		event.setLocation(ControllerUtils.setValue(event.getLocation(), eventDTO.getLocation()));

        event = eventDao.save(event);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public EventDTO getEventDTOById(Integer eventId) {
	
		Event event = eventDao.getById(eventId);
		
		EventConvertCriteriaDTO convertCriteria = new EventConvertCriteriaDTO();
		return(this.convertEventToEventDTO(event,convertCriteria));
	}
}
