package com.springbatchexample.springbatchexample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Customer")
@Data
public class Customer {
	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "CustomerId")
	private String customerId;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "Company")
	private String company;
	
	@Column(name = "City")
	private String city;
	
	@Column(name = "Country")
	private String country;
	
	@Column(name = "Phone")
	private String phone;
	
	@Column(name = "alternatenumber")
	private String alternatenumber;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "SubscriptionDate")
	private String subscriptionDate;
	
	@Column(name = "Website")
	private String website;

}
