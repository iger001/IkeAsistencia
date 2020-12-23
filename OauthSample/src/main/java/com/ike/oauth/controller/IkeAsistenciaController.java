package com.ike.oauth.controller;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ike.oauth.entity.Usuario;
import com.ike.oauth.service.UsuarioService;
import com.ike.oauth.service.impl.UserService;

@RestController
public class IkeAsistenciaController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/echo")
	public String echo() {
		return "Hola Mundo";
	}
	
	@GetMapping("/echo/{nombre}")
	public String echoNombre(@PathVariable String nombre) {
		return "Hola " + nombre;
	}
	
	@GetMapping("/sha256/{cadena}")
	public String sha256Cadena(@PathVariable String cadena) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(cadena.getBytes());
		return "cadena : " + cadena + "\n" + "sha-256 : " + Base64.getEncoder().encodeToString(hash);
	}
	
	@GetMapping("/bcrypt/{cadena}")
	public String bcryptCadena(@PathVariable String cadena) throws Exception {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return "cadena : " + cadena + "\n" + "bcrypt : " + bcrypt.encode(cadena);
	}
	
	@GetMapping("/bcryptValidator/{cadena}/{cadenaBcrypt}")
	public String bcryptValidatorCadena(@PathVariable String cadena, @PathVariable String cadenaBcrypt) throws Exception {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		boolean flag = bcrypt.matches(cadena, cadenaBcrypt);
		return "La validación fue " + ((flag)?"Exitosa":"No exitosa");
	}
	
	@PostMapping("/bcryptValidatorParametro")
	public String bcryptValidatorCadenaParametro(@RequestParam String cadena, @RequestParam String cadenaBcrypt) throws Exception {
		//String cadena = "";
		//String cadenaBcrypt = "";
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		boolean flag = bcrypt.matches(cadena, cadenaBcrypt);
		return "La validación fue " + ((flag)?"Exitosa":"No exitosa");
	}
	
	
	@GetMapping("/usuarios")
	public List<Usuario> getUsuarios() {
		
		List<Usuario> usuarios = usuarioService.getUsuarios(); 
		
		for(Usuario u : usuarios) {
			System.out.println(u.getNombreUsuario());
			System.out.println("u : " + u);
		}
		
		return usuarios;
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		//String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZXJhcmRvIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaWF0IjoxNjA4MDE1NDQxLCJleHAiOjE2MDgwMTU3NDF9.nLhqQEShQu7nCfKuR2pJost_E5jlLCT_Es9eU4_w37U";
		return userService.signin(username, password);
	}

	@GetMapping("/refresh")
	@PreAuthorize("hasRole('Administrador') or hasRole('Consulta')")
	public String refresh(HttpServletRequest req) {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZXJhcmRvIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaWF0IjoxNjA4MDE1NDQxLCJleHAiOjE2MDgwMTU3NDF9.nLhqQEShQu7nCfKuR2pJost_E5jlLCT_Es9eU4_w37U";
		return token;// return userService.refresh(req.getRemoteUser());
	}

}
