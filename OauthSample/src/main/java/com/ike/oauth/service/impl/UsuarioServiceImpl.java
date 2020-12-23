package com.ike.oauth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ike.oauth.dao.UsuarioDAO;
import com.ike.oauth.entity.Usuario;
import com.ike.oauth.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioDAO usaurioDAO;

	@Override
	public List<Usuario> getUsuarios() {
		
		return (List<Usuario>)usaurioDAO.findAll();
	}

}
