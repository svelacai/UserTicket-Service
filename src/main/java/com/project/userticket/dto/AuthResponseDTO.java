package com.project.userticket.dto;

public class AuthResponseDTO {
	private String token;
	private String type = "Bearer";

	public AuthResponseDTO(String token) {
		this.token = token;
		this.type = "Bearer";
	}

	public AuthResponseDTO(String token, String type) {
		this.token = token;
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
