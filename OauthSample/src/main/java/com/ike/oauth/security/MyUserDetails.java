package com.ike.oauth.security;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ike.oauth.dao.UsuarioDAO;
import com.ike.oauth.entity.Usuario;

@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UsuarioDAO usuarioDAO;

  @Override
  public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
    final Usuario usuario = usuarioDAO.findByNombreUsuarioIgnoreCase(nombreUsuario);

    if (usuario == null) {
      throw new UsernameNotFoundException("User '" + nombreUsuario + "' not found");
    }
    
    List<GrantedAuthority> authorities = usuario.getRoles()
			.stream()
			.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
			.peek(authority -> System.out.println("Role : " + authority.getAuthority()))
			.collect(Collectors.toList());
    
    return org.springframework.security.core.userdetails.User//
        .withUsername(nombreUsuario)//
        .password(usuario.getContrasena())//
        .authorities(authorities)//
        .accountExpired(usuario.getVigencia().getTime()<new Date().getTime())//si la vigencia es mayor a la actual manda true y deshabilita el acceso
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
