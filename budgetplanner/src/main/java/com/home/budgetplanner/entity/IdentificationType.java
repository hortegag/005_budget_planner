package com.home.budgetplanner.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.Setter;
import lombok.AccessLevel;

@Entity
@Data( )
public class IdentificationType {
	
	@Id
	@GeneratedValue
	@Setter(AccessLevel.NONE)
	private Long id;
	
	private String name;
	private String nnmonic;
	private String description;
	
	

	public IdentificationType(String name, String nnmonic, String description) {
		super();
		this.name = name;
		this.nnmonic = nnmonic;
		this.description = description;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
