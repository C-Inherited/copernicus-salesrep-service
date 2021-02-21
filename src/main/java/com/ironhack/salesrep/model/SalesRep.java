package com.ironhack.salesrep.model;

import javax.persistence.*;

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

}
