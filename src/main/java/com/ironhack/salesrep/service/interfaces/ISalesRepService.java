package com.ironhack.salesrep.service.interfaces;

import com.ironhack.salesrep.dto.*;
public interface ISalesRepService {

    public CompleteSalesRepDTO findById(Integer salesRepId);

    public SalesRepDTO saveSalesRep(SalesRepNameDTO name);

    public SalesRepDTO updateSalesRep(SalesRepNameDTO name, Integer salesRepId);

    public void deleteSalesRep(Integer salesRepId);
}
