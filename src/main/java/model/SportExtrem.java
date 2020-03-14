package model;

import java.time.LocalDate;
import java.util.Objects;

public class SportExtrem {
    String numeSport;
    LocalDate startDate;
    LocalDate endDate;
    float costMediuZi;

    public SportExtrem(String numeSport, LocalDate startDate, LocalDate endDate, float costMediuZi) {
        this.numeSport = numeSport;
        this.startDate = startDate;
        this.endDate = endDate;
        this.costMediuZi = costMediuZi;
    }

    public String getNumeSport() {
        return numeSport;
    }

    public void setNumeSport(String numeSport) {
        this.numeSport = numeSport;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public float getCostMediuZi() {
        return costMediuZi;
    }

    public void setCostMediuZi(float costMediuZi) {
        this.costMediuZi = costMediuZi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportExtrem that = (SportExtrem) o;
        return Float.compare(that.costMediuZi, costMediuZi) == 0 &&
                Objects.equals(numeSport, that.numeSport) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeSport, startDate, endDate, costMediuZi);
    }

    @Override
    public String toString() {
        return "SportExtrem{" +
                "numeSport='" + numeSport + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", costMediuZi=" + costMediuZi +
                '}';
    }
}
