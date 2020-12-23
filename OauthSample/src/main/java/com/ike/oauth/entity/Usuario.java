package com.ike.oauth.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"idusuario\"")
	private Long idUsuario;
	
	@Column(name = "nombreusuario")
	private String nombreUsuario;
	
	@Column(name = "correo")
	private String correo;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "apellidopaterno")
	private String apellidoPaterno;
	
	@Column(name = "apellidomaterno")
	private String apellidoMaterno;
	
	@Column(name = "contrasena")
	private String contrasena;
	
	@Column(name = "estatus")
	private String estatus;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	(
			name="Usuario_Rol",
			joinColumns = @JoinColumn(name = "idusuario"),
			inverseJoinColumns = @JoinColumn(name = "idrol")			
	)
	private List<Rol> roles;
	
	private Date vigencia;
}
