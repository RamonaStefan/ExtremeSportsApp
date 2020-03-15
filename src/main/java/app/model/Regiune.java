package app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Regiune extends Tara{
    private String numeRegiune;

    @JsonCreator
    public Regiune(@JsonProperty("numeTara") String numeTara,
                   @JsonProperty("numeRegiune") String numeRegiune) {
        super(numeTara);
        this.numeRegiune = numeRegiune;
    }

    public String getNumeRegiune() {
        return numeRegiune;
    }

    public void setNumeRegiune(String numeRegiune) {
        this.numeRegiune = numeRegiune;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Regiune regiune = (Regiune) o;
        return Objects.equals(numeRegiune, regiune.numeRegiune);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeRegiune);
    }

    @Override
    public String toString() {
        return "Regiune{" +
                "numeRegiune='" + numeRegiune + '\'' +
                '}';
    }
}
