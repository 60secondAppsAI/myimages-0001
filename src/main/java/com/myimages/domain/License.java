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
@Table(name="licenses")
@Getter @Setter @NoArgsConstructor
public class License {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="license_id")
	private Integer licenseId;
    
  	@Column(name="owner_id")
	private Integer ownerId;
    
  	@Column(name="license_type")
	private String licenseType;
    
  	@Column(name="valid_until")
	private Date validUntil;
    
}
