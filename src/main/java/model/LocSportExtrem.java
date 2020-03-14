package model;

import java.awt.*;
import java.util.Objects;

public class LocSportExtrem extends Localitate {
    List sport;

    public LocSportExtrem(String numeTara, String numeRegiune, String numeLocalitate, List sport) {
        super(numeTara, numeRegiune, numeLocalitate);
        this.sport = sport;
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
        LocSportExtrem that = (LocSportExtrem) o;
        return Objects.equals(sport, that.sport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sport);
    }

    @Override
    public String toString() {
        return "LocSportExtrem{" +
                "sport=" + sport +
                '}';
    }
}
