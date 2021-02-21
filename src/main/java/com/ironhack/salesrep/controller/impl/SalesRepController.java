package com.ironhack.salesrep.controller.impl;

import com.ironhack.salesrep.controller.interfaces.ISalesRepController;
import com.ironhack.salesrep.dto.CompleteSalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepDTO;
import com.ironhack.salesrep.dto.SalesRepNameDTO;
import com.ironhack.salesrep.service.impl.SalesRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class SalesRepController implements ISalesRepController {

    @Autowired
    private SalesRepService service;

    @GetMapping("/salesrep/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompleteSalesRepDTO getSalesRepById(@PathVariable(name = "id") Integer id) {
        return service.findById(id);
    }

    @PostMapping("/salesrep")
    @ResponseStatus(HttpStatus.CREATED)
    public SalesRepDTO saveSalesRep(@RequestBody @Valid SalesRepNameDTO name) {
        return service.saveSalesRep(name);
    }

    @PutMapping("/salesrep/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SalesRepDTO updateSalesRep(@RequestBody @Valid SalesRepNameDTO name, @PathVariable(name = "id") Integer id) {
        return service.updateSalesRep(name, id);
    }

    @DeleteMapping("/salesrep/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateSalesRep(@PathVariable(name = "id") Integer id) {
        service.deleteSalesRep(id);
    }
}
