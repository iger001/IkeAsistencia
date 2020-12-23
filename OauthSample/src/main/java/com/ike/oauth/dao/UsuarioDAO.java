package com.ike.oauth.dao;

import org.springframework.data.repository.CrudRepository;

import com.ike.oauth.entity.Usuario;

public interface UsuarioDAO extends CrudRepository<Usuario, Long>{
	
	public Usuario findByCorreoIgnoreCase(String correo);
	
	public Usuario findByNombreUsuarioIgnoreCase(String nombreUsuario);

}
