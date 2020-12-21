package com.Alvaro.TFG.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "proyect")
public class Proyect implements Serializable{

	@Id
	@Column(name = "IdProyect")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProyect;
	
	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@ManyToMany(mappedBy = "proyects")
	private List<User> users = new ArrayList<>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getIdProyect() {
		return idProyect;
	}

	public void setIdProyect(int idProyect) {
		this.idProyect = idProyect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idProyect;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Proyect other = (Proyect) obj;
		if (idProyect != other.idProyect)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
