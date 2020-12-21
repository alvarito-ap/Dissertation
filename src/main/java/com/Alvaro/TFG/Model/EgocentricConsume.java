package com.Alvaro.TFG.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "egocentric_consume")
public class EgocentricConsume implements Serializable, EgocentricRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "x")
    private int coordX;

    @Column(name = "y")
    private int coordY;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "frecuency")
    private String frecuency;

    @Column(name = "place")
    private String place;

    @JoinColumn(name = "IdTeenager")
    @ManyToOne
    private Teenager teenager;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(String frecuency) {
        this.frecuency = frecuency;
    }

    public Teenager getTeenager() {
        return teenager;
    }

    public void setTeenager(Teenager teenager) {
        this.teenager = teenager;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
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
        EgocentricConsume that = (EgocentricConsume) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(frecuency, that.frecuency) &&
                Objects.equals(teenager, that.teenager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, frecuency, teenager);
    }
}
