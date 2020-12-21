package com.Alvaro.TFG.Model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Consume")
public class Consume implements Serializable{
	
	@Id
	@Column(name = "IdConsume")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idConsume;
	
	@Column(name = "Tobacco")
	private boolean tobacco;
	
	@Column(name = "Cannabis")
	private boolean cannabis;
	
	@Column(name = "Stimulants")
	private boolean stimulants;
	
	@Column(name = "Sedatives")
	private boolean sedatives;

	@Column(name = "Alcohol_First")
	private int alcoholFirst;

	@Column(name = "Alcohol_Frequency")
	private String alcoholfrequency;

	@Column(name = "Alcohol_How_Many")
	private String alcoholHowMany;
	
	@Column(name = "Age_First_Consumed")
	private int ageFirstConsume;
	
	@Column(name = "Days_Consumed_last_30_Days")
	private String daysConsumedLast30Days;
	
	@Column(name = "Alcohol_And_Sex")
	private boolean alcoholAndSex;

	public int getIdConsume() {
		return idConsume;
	}

	public void setIdConsume(int idConsume) {
		this.idConsume = idConsume;
	}

	public boolean isTobacco() {
		return tobacco;
	}

	public void setTobacco(boolean tobacco) {
		this.tobacco = tobacco;
	}

	public boolean isCannabis() {
		return cannabis;
	}

	public void setCannabis(boolean cannabis) {
		this.cannabis = cannabis;
	}

	public boolean isStimulants() {
		return stimulants;
	}

	public void setStimulants(boolean stimulants) {
		this.stimulants = stimulants;
	}

	public boolean isSedatives() {
		return sedatives;
	}

	public void setSedatives(boolean sedatives) {
		this.sedatives = sedatives;
	}

	public int getAlcoholFirst() {
		return alcoholFirst;
	}

	public void setAlcoholFirst(int alcoholFirst) {
		this.alcoholFirst = alcoholFirst;
	}

	public String getAlcoholfrequency() {
		return alcoholfrequency;
	}

	public void setAlcoholfrequency(String alcoholfrequency) {
		this.alcoholfrequency = alcoholfrequency;
	}

	public String getAlcoholHowMany() {
		return alcoholHowMany;
	}

	public void setAlcoholHowMany(String alcoholHowMany) {
		this.alcoholHowMany = alcoholHowMany;
	}

	public int getAgeFirstConsume() {
		return ageFirstConsume;
	}

	public void setAgeFirstConsume(int ageFirstConsume) {
		this.ageFirstConsume = ageFirstConsume;
	}

	public String getDaysConsumedLast30Days() {
		return daysConsumedLast30Days;
	}

	public void setDaysConsumedLast30Days(String daysConsumedLast30Days) {
		this.daysConsumedLast30Days = daysConsumedLast30Days;
	}

	public boolean isAlcoholAndSex() {
		return alcoholAndSex;
	}

	public void setAlcoholAndSex(boolean alcoholAndSex) {
		this.alcoholAndSex = alcoholAndSex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Consume consume = (Consume) o;
		return idConsume == consume.idConsume &&
				tobacco == consume.tobacco &&
				cannabis == consume.cannabis &&
				stimulants == consume.stimulants &&
				sedatives == consume.sedatives &&
				alcoholFirst == consume.alcoholFirst &&
				ageFirstConsume == consume.ageFirstConsume &&
				alcoholAndSex == consume.alcoholAndSex &&
				Objects.equals(alcoholfrequency, consume.alcoholfrequency) &&
				Objects.equals(alcoholHowMany, consume.alcoholHowMany) &&
				Objects.equals(daysConsumedLast30Days, consume.daysConsumedLast30Days);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idConsume, tobacco, cannabis, stimulants, sedatives, alcoholFirst, alcoholfrequency, alcoholHowMany, ageFirstConsume, daysConsumedLast30Days, alcoholAndSex);
	}
}
