package com.Alvaro.TFG.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "places_frec")
public class PlacesFrec implements Serializable{

	@Id
	@Column(name = "IdPlacesFrec")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPlacesFrec;
	
	@Column(name = "Data")
	private String datos;

	public int getIdPlacesFrec() {
		return idPlacesFrec;
	}

	public void setIdPlacesFrec(int idPlacesFrec) {
		this.idPlacesFrec = idPlacesFrec;
	}

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datos == null) ? 0 : datos.hashCode());
		result = prime * result + idPlacesFrec;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlacesFrec other = (PlacesFrec) obj;
		if (datos == null) {
			if (other.datos != null)
				return false;
		} else if (!datos.equals(other.datos))
			return false;
		if (idPlacesFrec != other.idPlacesFrec)
			return false;
		return true;
	}
	
	
}
