package ru.home.vtb.operasales.operasalesapp.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import ru.home.vtb.operasales.operasalesapp.operas.Opera;


@Component
@Aspect
public class EmailNotifier {


    @Pointcut("@annotation(ru.home.vtb.operasales.operasalesapp.annotation.SendMail)")
    public void operaServiceMail(){}

    @AfterReturning("operaServiceMail()")
    public void afternewopera(JoinPoint point) {
        final Object[] parameterValues = point.getArgs();
        StringBuilder sOpera = new StringBuilder();
        String method = point.getSignature().getName();
        //System.out.println(point.getSignature().getName());
        // сформируем текст письма. Тут мы завзались на параметры конкретного метода, с которым работали
        switch (method) {
            case "newOpera":
                sOpera.append("E-mail subj: Внимание! Появился новый релиз!\n");
                for (int i = 0; i < parameterValues.length; i++) {
                    switch (i) {
                        case 0: sOpera.append("Наименование:    ");
                            break;
                        case 1: sOpera.append("Дата проведения: ");
                            break;
                        case 2: sOpera.append("Возрастная категория: ");
                            break;
                        case 3: sOpera.append("Описание: ");
                            break;
                    }
                    sOpera.append(parameterValues[i] + "\n");
                }
                break;
            case "saleTiket":
                sOpera.append("E-mail subj: Внимание! Продан билет!\n");
                Opera opera;
                opera = (Opera) parameterValues[0];
                //System.out.println(opera.getOperaName());
                sOpera.append("Наименование: " + opera.getOperaName() + " ");
                for (int i = 1; i < parameterValues.length; i++){
                    //System.out.println(i + " " + parameterValues[i]);
                    switch (i) {
                        case 1: sOpera.append("Ряд: "+parameterValues[i] + " ");
                            break;
                        case 2: sOpera.append("Место: " + parameterValues[i]);
                            break;
                    }
                }
                break;
            case "changeOpera":
                sOpera.append("E-mail subj: Внимание! В мероприятие " + parameterValues[0] + " от " + parameterValues[1] +" внесены изменения!\n");
                for (int i = 2; i < parameterValues.length; i++){
                    switch (i) {
                        case 2: sOpera.append("Наименование:    ");
                            break;
                        case 3: sOpera.append("Дата проведения: ");
                            break;
                        case 4: sOpera.append("Возрастная категория: ");
                            break;
                        case 5: sOpera.append("Описание: ");
                            break;
                    }
                    sOpera.append(parameterValues[i] + "\n");
                }
        }

      send(String.valueOf(sOpera));
    }

    public void send(String msg) {
        // типо отправки сообщения
        System.out.println(msg);
    }
}
