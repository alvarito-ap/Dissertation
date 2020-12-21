package com.Alvaro.TFG.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "contact_frec")
public class ContactFrec implements Serializable{
	
	@Id
	@Column(name = "IdContactFrec")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idContactFrec;
	
	@Column(name = "Datos")
	private String datos;

	

	public int getIdContactFrec() {
		return idContactFrec;
	}

	public void setIdContactFrec(int idContactFrec) {
		this.idContactFrec = idContactFrec;
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
		result = prime * result + idContactFrec;
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
		ContactFrec other = (ContactFrec) obj;
		if (datos == null) {
			if (other.datos != null)
				return false;
		} else if (!datos.equals(other.datos))
			return false;
		if (idContactFrec != other.idContactFrec)
			return false;
		return true;
	}

	
}
