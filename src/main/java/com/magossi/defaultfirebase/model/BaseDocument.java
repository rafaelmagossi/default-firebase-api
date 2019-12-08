package com.magossi.defaultfirebase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDocument {
	private String id;

	private LocalDateTime createdAt;

	@JsonIgnore
	private String createdBy;

	@JsonIgnore
	private String updatedBy;

	@JsonIgnore
	private LocalDateTime updatedAt;
}
