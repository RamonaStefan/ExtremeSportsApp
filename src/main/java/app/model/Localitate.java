package app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Localitate extends Regiune  implements Comparable<Localitate>  {
    private String numeLocalitate;
    ArrayList<SportExtrem> sport;

    @JsonCreator
    public Localitate(@JsonProperty("numeTara") String numeTara,
                      @JsonProperty("numeRegiune") String numeRegiune,
                      @JsonProperty("numeLocalitate") String numeLocalitate,
                      @JsonProperty("sport") ArrayList<SportExtrem> sport) {
        super(numeTara, numeRegiune);
        this.numeLocalitate = numeLocalitate;
        this.sport = sport;
    }

    public String getNumeLocalitate() {
        return numeLocalitate;
    }

    public ArrayList<SportExtrem> getSport() {
        return sport;
    }

    public void setSport(ArrayList<SportExtrem> sport) {
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

    @Override
    public int compareTo(Localitate u) {
        Collections.sort(sport);
        Collections.sort(u.getSport());
        double cost1 = sport.get(0).getCostMediuZi();
        double cost2 = u.getSport().get(0).getCostMediuZi();
        if (cost1 == 0 || cost2 == 0) {
            return 0;
        }
        if (cost1 == cost2) {
            return 0;
        }
        if(cost1 < cost2) {
            return -1;
        }
        return 1;
    }

}