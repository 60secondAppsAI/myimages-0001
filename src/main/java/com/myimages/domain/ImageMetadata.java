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
@Table(name="image_metadatas")
@Getter @Setter @NoArgsConstructor
public class ImageMetadata {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="image_metadata_id")
	private Integer imageMetadataId;
    
  	@Column(name="image_id")
	private Integer imageId;
    
  	@Column(name="resolution")
	private String resolution;
    
  	@Column(name="format")
	private String format;
    
}
