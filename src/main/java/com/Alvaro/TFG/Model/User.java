package com.Alvaro.TFG.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User implements Serializable{
	
	@Id
	@Column(name = "IdUser")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUser;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "User")
	private String username;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "Status")
	private boolean status;
	
	@JoinColumn(name = "IdRole")
	@ManyToOne
	private Role role;
	
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(name = "proyect_user",
				joinColumns = @JoinColumn(name = "IdUser"),
				inverseJoinColumns = @JoinColumn(name = "IdProyect")
	)
	private List<Proyect> proyects = new ArrayList<>();
	
	public void addProyect(Proyect proyecto) {
        proyects.add(proyecto);
        proyecto.getUsers().add(this);
    }
 
    public void removeProyect(Proyect proyecto) {
        proyects.remove(proyecto);
        proyecto.getUsers().remove(this);
    }

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Proyect> getProyects() {
		return proyects;
	}

	public void setProyects(List<Proyect> proyects) {
		this.proyects = proyects;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idUser;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (idUser != other.idUser)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (status != other.status)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	

}
