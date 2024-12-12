package com.myimages.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="user_roles")
@Getter @Setter @NoArgsConstructor
public class UserRole {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="user_role_id")
	private Integer userRoleId;
    
  	@Column(name="user_profile_id")
	private Integer userProfileId;
    
  	@Column(name="role_name")
	private String roleName;
    
}
