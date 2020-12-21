package com.Alvaro.TFG.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "teenager")
public class Teenager implements Serializable{
	
	@Id
	@Column(name = "IdTeenager")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTeenager;
	
	@Column(name = "Name")
	private String name;

	@Column(name = "Alias")
	private String alias;

	@Column(name = "SurName")
	private String surName;
	
	@Column(name = "Age")
	private int age;
	
	@Column(name = "Place_Birth")
	private String placeBirth;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Date_Birth")
	private Date dateBirth;

	@Column(name = "Sex")
	private String sex;
	
	@Column(name = "Course")
	private String course;
	
	@Column(name = "Groupe")
	private String groupe;
	
	@Column(name = "Institute")
	private String institute;

	@Column(name = "Extra")
	private String extra;

	@Column(name = "coordX")
	private int coorX;

	@Column(name = "coordY")
	private int coordY;

	@JoinColumn(name = "IdProyect")
	@ManyToOne
	private Proyect proyect;
	
	@JoinColumn(name = "IdContactFrec")
	@OneToOne
	private ContactFrec contactFrec;
	
	@JoinColumn(name = "IdPlacesFrec")
	@OneToOne
	private PlacesFrec placesFrec;
	
	@JoinColumn(name = "IdConsume")
	@OneToOne
	private Consume consume;
	
	public Consume getConsume() {
		return consume;
	}

	public void setConsume(Consume consume) {
		this.consume = consume;
	}

	public int getIdTeenager() {
		return idTeenager;
	}

	public void setIdTeenager(int idTeenager) {
		this.idTeenager = idTeenager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPlaceBirth() {
		return placeBirth;
	}

	public void setPlaceBirth(String placeBirth) {
		this.placeBirth = placeBirth;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Proyect getProyect() {
		return proyect;
	}

	public void setProyect(Proyect proyect) {
		this.proyect = proyect;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public ContactFrec getContactFrec() {
		return contactFrec;
	}

	public void setContactFrec(ContactFrec contactFrec) {
		this.contactFrec = contactFrec;
	}

	public PlacesFrec getPlacesFrec() {
		return placesFrec;
	}

	public void setPlacesFrec(PlacesFrec placesFrec) {
		this.placesFrec = placesFrec;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getCoorX() {
		return coorX;
	}

	public void setCoorX(int coorX) {
		this.coorX = coorX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Teenager teenager = (Teenager) o;
		return idTeenager == teenager.idTeenager &&
				age == teenager.age &&
				Objects.equals(name, teenager.name) &&
				Objects.equals(surName, teenager.surName) &&
				Objects.equals(placeBirth, teenager.placeBirth) &&
				Objects.equals(dateBirth, teenager.dateBirth) &&
				Objects.equals(sex, teenager.sex) &&
				Objects.equals(course, teenager.course) &&
				Objects.equals(groupe, teenager.groupe) &&
				Objects.equals(institute, teenager.institute) &&
				Objects.equals(extra, teenager.extra) &&
				Objects.equals(proyect, teenager.proyect) &&
				Objects.equals(contactFrec, teenager.contactFrec) &&
				Objects.equals(placesFrec, teenager.placesFrec) &&
				Objects.equals(consume, teenager.consume);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idTeenager, name, surName, age, placeBirth, dateBirth, sex, course, groupe, institute, extra, proyect, contactFrec, placesFrec, consume);
	}

	public String color;
}
