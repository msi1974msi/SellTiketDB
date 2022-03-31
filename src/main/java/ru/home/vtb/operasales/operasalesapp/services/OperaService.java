package ru.home.vtb.operasales.operasalesapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.home.vtb.operasales.operasalesapp.repository.entities.OperaEntity;
import ru.home.vtb.operasales.operasalesapp.repository.interfaces.OperaRepository;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OperaService {

    private final OperaRepository repository;

    @Autowired
    public OperaService(OperaRepository repository) {
        this.repository = repository;
    }

    public List<String> getAllOperas() {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        List<OperaEntity> operas = repository.findAll();
        List<String> names = new ArrayList<>();
        for (OperaEntity opera : operas) {
            names.add("\nНаименование: " + opera.getLabel() +
                      "\nДата проведения: " + df.format(opera.getOperaDate()) +
                      "\nОписание: " + opera.getDescription() +
                      "\nВозрастная категория: " + opera.getCategory() +
                      "\nСвободных билетов: " + opera.freeTickest()
            );
        }
        return names;
    }


    public String getOparaInfo(String name, String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        OperaEntity opera = repository.findByLabelAndOperaDate(name, df.parse(date));
        if (opera == null) {
            return "Нет такого релиза";
        }
        return opera.toString();
    }

    public OperaEntity getOpera(String name, String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        OperaEntity opera = repository.findByLabelAndOperaDate(name, df.parse(date));
        return opera;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout = 2,
            readOnly = false,
            rollbackFor = {IOException.class, FileNotFoundException.class, EOFException.class}
    )
    public OperaEntity createOpera(OperaEntity opera) {
        // делаю без проверок - гипотетически это на фронтэнд
        // ну или потом, если время будет
        repository.save(opera);
        // инициализация билетов
        TicketService.initTickets(opera);
        return opera;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout = 2,
            readOnly = false,
            rollbackFor = {IOException.class, FileNotFoundException.class, EOFException.class}
    )
    public OperaEntity changeOpera(OperaEntity opera) {
        // делаю без проверок - гипотетически это на фронтэнд
        // ну или потом, если время будет
        // для теста танзакции можно и ошибку кинуть
        repository.save(opera);
        return opera;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            timeout = 2,
            readOnly = false,
            rollbackFor = {IOException.class, FileNotFoundException.class, EOFException.class}
    )
    public void deleteOpera(OperaEntity opera) {
        // сначала билетики удаляем!
        repository.delete(opera);
    }


    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        timeout = 2,
        readOnly = false,
        rollbackFor = {IOException.class, FileNotFoundException.class, EOFException.class}
    )
    public void deleteTikets(OperaEntity opera) {
        TicketService.delTickets(opera);
    }

}
