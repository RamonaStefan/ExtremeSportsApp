package model;

import java.util.Objects;

public class Localitate extends Regiune {
    private String numeLocalitate;

    public Localitate(String numeTara, String numeRegiune, String numeLocalitate) {
        super(numeTara, numeRegiune);
        this.numeLocalitate = numeLocalitate;
    }

    public String getNumeLocalitate() {
        return numeLocalitate;
    }

    public void setNumeLocalitate(String numeLocalitate) {
        this.numeLocalitate = numeLocalitate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Localitate that = (Localitate) o;
        return Objects.equals(numeLocalitate, that.numeLocalitate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeLocalitate);
    }

    @Override
    public String toString() {
        return "Localitate{" +
                "numeLocalitate='" + numeLocalitate + '\'' +
                '}';
    }
}
