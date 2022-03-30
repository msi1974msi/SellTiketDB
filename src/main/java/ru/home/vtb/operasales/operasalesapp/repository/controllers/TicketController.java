package ru.home.vtb.operasales.operasalesapp.repository.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.home.vtb.operasales.operasalesapp.repository.entities.OperaEntity;
import ru.home.vtb.operasales.operasalesapp.services.TicketService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class TicketController {

    private static TicketService service;

    @Autowired
    public TicketController(TicketService service) {
       this.service = service;
    }

    public void initTickets(OperaEntity opera){
        // ушло в создание релиза :-)))
        System.out.println("инициализация билетов при добавлении релиза");
    }

    public void sellTicket(OperaEntity opera, int nRow, int nPlace){
        service.saleTiket(opera , nRow, nPlace);
    }

    public void returnTicket(OperaEntity opera, int nRow, int nPlace){
        service.returnTiket(opera , nRow, nPlace);
    }

}
