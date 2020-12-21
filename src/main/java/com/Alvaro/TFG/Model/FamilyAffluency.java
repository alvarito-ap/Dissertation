package com.Alvaro.TFG.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "family_affluency")
public class FamilyAffluency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "member")
    private String member;

    @Column(name = "place")
    private String place;

    @JoinColumn(name = "idTeenager")
    @ManyToOne
    private Teenager teenager;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyAffluency that = (FamilyAffluency) o;
        return id == that.id &&
                Objects.equals(member, that.member) &&
                Objects.equals(place, that.place) &&
                Objects.equals(teenager, that.teenager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, place, teenager);
    }
}
