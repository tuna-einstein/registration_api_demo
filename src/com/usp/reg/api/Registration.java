package com.usp.reg.api;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Registration {

	public static enum Gender {
		MALE,
		FEMALE,
		OTHER
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String legalName;

	@Persistent
	private String spiritualName;

	@Persistent
	private int age;

	@Persistent
	private Gender gender;

	@Persistent
	private String addressLine1;

	@Persistent
	private String addressLine2;

	@Persistent
	private String city;

	@Persistent
	private String stateProvinceRegion;

	@Persistent
	private String zip;

	@Persistent
	private String country;

	@Persistent
	private String phoneNumber;

	@Persistent
	private String email;

	@Persistent
	private List<NameValue> keyValues;

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getSpiritualName() {
		return spiritualName;
	}

	public void setSpiritualName(String spiritualName) {
		this.spiritualName = spiritualName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvinceRegion() {
		return stateProvinceRegion;
	}

	public void setStateProvinceRegion(String stateProvinceRegion) {
		this.stateProvinceRegion = stateProvinceRegion;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<NameValue> getKeyValues() {
		return keyValues;
	}

	public void setKeyValues(List<NameValue> keyValues) {
		this.keyValues = keyValues;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
