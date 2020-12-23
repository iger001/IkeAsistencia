package com.ike.oauth.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.ike.oauth.dao.UsuarioDAO;
import com.ike.oauth.entity.Usuario;
import com.ike.oauth.exception.CustomException;
import com.ike.oauth.security.JwtTokenProvider;

@Service
public class UserService {

  @Autowired
  private UsuarioDAO userRepository;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  public String signin(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      Usuario usuario = userRepository.findByNombreUsuarioIgnoreCase(username);
      return jwtTokenProvider.createToken(username, usuario.getRoles());
    } catch (AuthenticationException e) {
    	//e.printStackTrace();
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public Usuario search(String username) {
    Usuario usuario = userRepository.findByNombreUsuarioIgnoreCase(username);
    if (usuario == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return usuario;
  }

  public Usuario whoami(HttpServletRequest req) {
	Usuario usuario = userRepository.findByNombreUsuarioIgnoreCase(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	if(usuario == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
	return usuario;
  }

  public String refresh(String nomUsuario) {
    return jwtTokenProvider.createToken(nomUsuario, userRepository.findByNombreUsuarioIgnoreCase(nomUsuario).getRoles());
  }

}
