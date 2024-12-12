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
@Table(name="prompt_configs")
@Getter @Setter @NoArgsConstructor
public class PromptConfig {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="prompt_config_id")
	private Integer promptConfigId;
    
  	@Column(name="owner_id")
	private Integer ownerId;
    
  	@Column(name="created_on_id")
	private Date createdOnId;
    
  	@Column(name="color1")
	private String color1;
    
  	@Column(name="color2")
	private String color2;
    
  	@Column(name="color3")
	private String color3;
    
  	@Column(name="style")
	private String style;
    
  	@Column(name="sponsor1")
	private String sponsor1;
    
  	@Column(name="sponsor2")
	private String sponsor2;
    
}
