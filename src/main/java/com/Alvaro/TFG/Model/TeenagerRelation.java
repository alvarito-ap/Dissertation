package com.Alvaro.TFG.Model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teenager_relation")
public class TeenagerRelation implements Serializable{

	@Id
	@Column(name = "IdRelation")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRelation;
	
	@JoinColumn(name = "IdPrimaryTeenager")
	@ManyToOne
	private Teenager primaryTeenager;
	
	@JoinColumn(name = "IdRelatedTeenager")
	@ManyToOne
	private Teenager relatedTeenager;

	@Column(name = "Time")
	private String time;

	@Column(name = "Consume")
	private String consume;

	public int getIdRelation() {
		return idRelation;
	}

	public void setIdRelation(int idRelation) {
		this.idRelation = idRelation;
	}

	public Teenager getPrimaryTeenager() {
		return primaryTeenager;
	}

	public void setPrimaryTeenager(Teenager primaryTeenager) {
		this.primaryTeenager = primaryTeenager;
	}

	public Teenager getRelatedTeenager() {
		return relatedTeenager;
	}

	public void setRelatedTeenager(Teenager relatedTeenager) {
		this.relatedTeenager = relatedTeenager;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getConsume() {
		return consume;
	}

	public void setConsume(String consume) {
		this.consume = consume;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TeenagerRelation that = (TeenagerRelation) o;
		return idRelation == that.idRelation &&
				Objects.equals(primaryTeenager, that.primaryTeenager) &&
				Objects.equals(relatedTeenager, that.relatedTeenager) &&
				Objects.equals(time, that.time) &&
				Objects.equals(consume, that.consume);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idRelation, primaryTeenager, relatedTeenager, time, consume);
	}
}
