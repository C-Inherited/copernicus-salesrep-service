package com.ironhack.salesrep.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class SalesRep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salesRepId;
    private String name;

    public SalesRep() {
    }

    public SalesRep(String name) {
        this.name = name;
    }

    public Integer getSalesRepId() {
        return salesRepId;
    }

    public void setSalesRepId(Integer salesRepId) {
        this.salesRepId = salesRepId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesRep salesRep = (SalesRep) o;
        return Objects.equals(salesRepId, salesRep.salesRepId) && Objects.equals(name, salesRep.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salesRepId, name);
    }
}
