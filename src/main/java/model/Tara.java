package model;

import java.util.Objects;

public class Tara {
    private String numeTara;

    public Tara(String numeTara) {
        this.numeTara = numeTara;
    }

    public String getNumeTara() {
        return numeTara;
    }

    public void setNumeTara(String numeTara) {
        this.numeTara = numeTara;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tara tara = (Tara) o;
        return Objects.equals(numeTara, tara.numeTara);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeTara);
    }

    @Override
    public String toString() {
        return "Tara{" +
                "numeTara='" + numeTara + '\'' +
                '}';
    }


}
