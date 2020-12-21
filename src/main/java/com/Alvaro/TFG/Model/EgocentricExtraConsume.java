package com.Alvaro.TFG.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "egocentric_extra_consume")
public class EgocentricExtraConsume implements Serializable, EgocentricRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "x")
    private int coordX;

    @Column(name = "y")
    private int coordY;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "placeFirstMeet")
    private String placeFirstMeet;

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

    public String getPlaceFirstMeet() {
        return placeFirstMeet;
    }

    public void setPlaceFirstMeet(String placeFirstMeet) {
        this.placeFirstMeet = placeFirstMeet;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Teenager getTeenager() {
        return teenager;
    }

    public void setTeenager(Teenager teenager) {
        this.teenager = teenager;
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
        EgocentricExtraConsume that = (EgocentricExtraConsume) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(placeFirstMeet, that.placeFirstMeet) &&
                Objects.equals(place, that.place) &&
                Objects.equals(teenager, that.teenager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, placeFirstMeet, place, teenager);
    }
}
