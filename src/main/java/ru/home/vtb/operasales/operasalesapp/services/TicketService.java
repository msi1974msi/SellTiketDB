package ru.home.vtb.operasales.operasalesapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.home.vtb.operasales.operasalesapp.repository.entities.OperaEntity;
import ru.home.vtb.operasales.operasalesapp.repository.entities.TicketEntity;
import ru.home.vtb.operasales.operasalesapp.repository.interfaces.OperaRepository;
import ru.home.vtb.operasales.operasalesapp.repository.interfaces.TicketRepository;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TicketService {
    private final OperaRepository operaRepository;
    private static TicketRepository ticketRepository;
    private static TicketEntity ticket;

    @Autowired
    public TicketService(OperaRepository operaRepository, TicketRepository ticketRepository) {
        this.operaRepository = operaRepository;
        this.ticketRepository = ticketRepository;
    }

    // выполняется в транзакции верхнего уровня (под релизом)
    public static void initTickets(OperaEntity opera) {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++){
                ticket = new TicketEntity(null, opera, 0, 1, i, j);
                ticketRepository.save(ticket);
            }
        }
    }

    // выполняется в транзакции верхнего уровня (под релизом)
    public static void delTickets(OperaEntity opera) {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++){
                TicketEntity ticket = ticketRepository.findByOperaAndRowAndPlace( opera, i, j);
                if (ticket == null) {
                    // нет билета
                } else {
                    ticketRepository.delete(ticket);
                }
            }
        }
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout = 2,
            readOnly = false,
            rollbackFor = {IOException.class, FileNotFoundException.class, EOFException.class}
    )
    public static void saleTiket(OperaEntity opera, int nRow, int nPlace) {
       TicketEntity ticket = ticketRepository.findByOperaAndRowAndPlace( opera, nRow, nPlace);
        if (ticket == null) {
            System.out.println("\nТакого билета не существует!");
        } else {
            if ( ticket.getStatus() == 0) {
                //можно продать
                ticket.setStatus(1);
                ticketRepository.save(ticket);
                System.out.println("\n Билет на ряд" + nRow + " место " + nPlace + " успешно продан!");
            } else {
                System.out.println("\n Билет уже продан, перечитайте информацию по залу и выберете другой!");
            }

        }
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout = 2,
            readOnly = false,
            rollbackFor = {IOException.class, FileNotFoundException.class, EOFException.class}
    )
    public static void returnTiket(OperaEntity opera, int nRow, int nPlace) {
        TicketEntity ticket = ticketRepository.findByOperaAndRowAndPlace( opera, nRow, nPlace);
        if (ticket == null) {
            System.out.println("\nТакого билета не существует!");
        } else {
            if ( ticket.getStatus() == 1) {
                //можно сдать
                ticket.setStatus(0);
                ticketRepository.save(ticket);
                System.out.println("\n Билет на ряд" + nRow + " место " + nPlace + " успешно сдан и доступен для продажи!");
            } else {
                System.out.println("\n Внимание! билет уже сдан, возможно перед вами мошенник!");
            }

        }
    }

}
