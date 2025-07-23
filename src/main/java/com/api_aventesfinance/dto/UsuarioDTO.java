package com.api_aventesfinance.dto;


import java.io.Serializable;

import com.api_aventesfinance.model.Usuario;





public class UsuarioDTO  implements Serializable{

	private Long userId;
	private String userLogin;
	private String userNome;
	private String userSenha;   
	private String userImg;


	public UsuarioDTO(Usuario usuario) 
	{
		this.userId = usuario.getId();
		this.userLogin = usuario.getLogin();
		this.userNome = usuario.getNome();
		this.userSenha = usuario.getSenha();
		this.userImg = usuario.getImg();

	}

	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUserLogin() {
		return userLogin;
	}	
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserNome() {
		return userNome;
	}

	public void setUserNome(String userNome) {
		this.userNome = userNome;
	}

	public String getUserSenha() {
		return userSenha;
	}

	public void setUserSenha(String userSenha) {
		this.userSenha = userSenha;
	}
}