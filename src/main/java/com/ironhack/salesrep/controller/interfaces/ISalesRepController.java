package com.ironhack.salesrep.controller.interfaces;

import com.ironhack.salesrep.dto.CompleteSalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepNameDTO;
import org.springframework.web.bind.annotation.RequestHeader;

public interface ISalesRepController {

    public CompleteSalesRepDTO getSalesRepById(Integer id, String authorizationHeader);

    public SalesRepDTO saveSalesRep(SalesRepNameDTO name, String authorizationHeader);

    public SalesRepDTO updateSalesRep(SalesRepNameDTO name, Integer id, String authorizationHeader);

    public void deleteSalesRep(Integer id, String authorizationHeader);
}
