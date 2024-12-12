package com.myimages.dao;

import java.util.List;

import com.myimages.dao.GenericDAO;
import com.myimages.domain.UserProfile;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserProfileDAO extends GenericDAO<UserProfile, Integer> {
  
	List<UserProfile> findAll();
	


}

