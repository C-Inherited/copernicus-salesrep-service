package com.ironhack.salesrep.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class SalesRepNameDTO {

    @NotEmpty
    private String name;

    public SalesRepNameDTO() {
    }

    public SalesRepNameDTO(String name) {
        this.name = name;
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
        SalesRepNameDTO that = (SalesRepNameDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
