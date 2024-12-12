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
@Table(name="historys")
@Getter @Setter @NoArgsConstructor
public class History {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="history_id")
	private Integer historyId;
    
  	@Column(name="user_profile_id")
	private Integer userProfileId;
    
  	@Column(name="image_action")
	private String imageAction;
    
  	@Column(name="action_date")
	private Date actionDate;
    
}
