package model;

import java.awt.*;
import java.util.Objects;

public class Localitate extends Regiune {
    private String numeLocalitate;
    List sport;

    public Localitate(String numeTara, String numeRegiune, String numeLocalitate, List sport) {
        super(numeTara, numeRegiune);
        this.numeLocalitate = numeLocalitate;
        this.sport = sport;
    }

    public String getNumeLocalitate() {
        return numeLocalitate;
    }

    public void setNumeLocalitate(String numeLocalitate) {
        this.numeLocalitate = numeLocalitate;
    }

    public List getSport() {
        return sport;
    }

    public void setSport(List sport) {
        this.sport = sport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Localitate that = (Localitate) o;
        return Objects.equals(numeLocalitate, that.numeLocalitate) &&
                Objects.equals(sport, that.sport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeLocalitate, sport);
    }

    @Override
    public String toString() {
        return "Localitate{" +
                "numeLocalitate='" + numeLocalitate + '\'' +
                ", sport=" + sport +
                '}';
    }
}
