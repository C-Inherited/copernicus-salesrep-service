package com.ironhack.salesrep.controller.interfaces;

import com.ironhack.salesrep.dto.CompleteSalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepNameDTO;

public interface ISalesRepController {

    public CompleteSalesRepDTO getSalesRepById(Integer id);

    public SalesRepDTO saveSalesRep(SalesRepNameDTO name);

    public SalesRepDTO updateSalesRep(SalesRepNameDTO name, Integer id);

    public void deleteSalesRep(Integer id);
}
