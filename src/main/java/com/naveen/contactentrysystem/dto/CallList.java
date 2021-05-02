package com.naveen.contactentrysystem.dto;

import java.util.List;

public class CallList {

	private NameDto name;
	private List<PhoneDto> phone;

	public NameDto getName() {
		return name;
	}

	public void setName(NameDto name) {
		this.name = name;
	}

	public List<PhoneDto> getPhone() {
		return phone;
	}

	public void setPhone(List<PhoneDto> phone) {
		this.phone = phone;
	}

}
