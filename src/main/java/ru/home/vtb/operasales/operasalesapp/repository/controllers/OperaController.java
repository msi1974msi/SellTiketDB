package ru.home.vtb.operasales.operasalesapp.repository.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.home.vtb.operasales.operasalesapp.repository.entities.OperaEntity;
import ru.home.vtb.operasales.operasalesapp.services.OperaService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class OperaController {

    private static OperaService service;

    @Autowired
    public OperaController(OperaService service) {
        this.service = service;
    }

    public void getAllOperas() {
        List<String> names = service.getAllOperas();
        for (String label : names) {
            System.out.println(label);
        }
    }

    public void getOperaInfo(String label, String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String info;
        info = service.getOparaInfo(label, date);
        System.out.println(info);
    }

    public OperaEntity getOpera(String label, String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        OperaEntity opera;
        opera = service.getOpera(label, date);
        return opera;
    }

    public OperaEntity createOpera(OperaEntity opera) {
        service.createOpera(opera);
        return opera;
    }

    public OperaEntity changeOpera(OperaEntity opera) {
       service.changeOpera(opera);
       return opera;
    }

    public void deleteOpera(OperaEntity opera){
       // по логике - должны удалиться и билеты
       service.deleteOpera(opera);
    }

    public void deleteTickets(OperaEntity opera) {
        service.deleteTikets(opera);
    }
}
