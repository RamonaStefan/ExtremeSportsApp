package app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SportExtrem {
    String numeSport;
    String startDate;
    String endDate;
    double costMediuZi;

    @JsonCreator
    public SportExtrem(@JsonProperty("numeSport") String numeSport,
                       @JsonProperty("startDate") String startDate,
                       @JsonProperty("endDate") String endDate,
                       @JsonProperty("costMediuZi") double costMediuZi) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getCostMediuZi() {
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
        return Double.compare(that.costMediuZi, costMediuZi) == 0 &&
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
