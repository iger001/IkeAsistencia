package com.ike.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IkeAsistenciaController {
	
	@GetMapping("/echo")
	public String echo() {
		return "Hola Mundo";
	}
	
	@GetMapping("/echo/{nombre}")
	public String echoNombre(@PathVariable String nombre) {
		return "Hola " + nombre;
	}

}
