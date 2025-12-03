package com.project.userticket.dto;

import jakarta.validation.constraints.NotBlank;

public class UsuarioRequestDTO {
	@NotBlank(message = "Nombres es obligatorio")
	private String nombres;

	@NotBlank(message = "Apellidos es obligatorio")
	private String apellidos;

	@NotBlank(message = "Username es obligatorio")
	private String username;

	@NotBlank(message = "Password es obligatorio")
	private String password;

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
