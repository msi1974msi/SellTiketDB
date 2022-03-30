package ru.home.vtb.operasales.operasalesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.home.vtb.operasales.operasalesapp.operas.Opera;
import ru.home.vtb.operasales.operasalesapp.repository.controllers.OperaController;
import ru.home.vtb.operasales.operasalesapp.repository.controllers.TicketController;
import ru.home.vtb.operasales.operasalesapp.repository.entities.OperaEntity;
import ru.home.vtb.operasales.operasalesapp.services.OperaService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class OperasalesAppApplication {

    public static void main(String[] args) throws ParseException {

       final ConfigurableApplicationContext ctx = SpringApplication.run(OperasalesAppApplication.class, args);
       SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
       OperaEntity opera;
       Date date;

       System.out.println("прочитаем список мероприятий");
       //прочитаем список мероприятий
       ctx.getBean(OperaController.class).getAllOperas();

//       // найдём  релиз и покажем его (несуществующий)
//       System.out.println("\nнайдём  релиз и покажем его:");
//       ctx.getBean(OperaController.class).getOperaInfo("Test1","29.03.2022");
//       System.out.println("\n");
//
//       // установим релиз для работы
//       System.out.println("\nустановим релиз для работы");
//       opera = ctx.getBean(OperaController.class).getOpera("Test","29.03.2022");
//       if (opera == null) {
//           System.out.println("\nНет такого релиза");
//       } else {
//           System.out.println("\nРелиз установлен, можно работать!");
//       }
//
//       // заведём новый релиз
//       System.out.println("\nзаведём новый релиз Test add  01.04.2022");
//       opera = ctx.getBean(OperaController.class).getOpera("Test add","01.04.2022");
//       if (opera == null) {
//           System.out.println("\nНет такого релиза");
//           //заводим
//           date = df.parse("01.04.2022");
//           opera = ctx.getBean(OperaController.class).createOpera(
//              new OperaEntity(null, 1, 1, date, "Test add", "Проверка добавления", "18+", null)
//           );
//           ctx.getBean(OperaController.class).getOperaInfo("Test add","01.04.2022");
//       } else {
//           System.out.println("\nТакой релиз уже есть!");
//       }
//
//       //поменяем описание заведённого релиза
//       // понятно, что нужны переменные старый и новые, а также понимаем, что некоторые поля неизменяемые
//       System.out.println("\nпоменяем описание заведённого релиза");
//       opera = ctx.getBean(OperaController.class).getOpera("Test add","01.04.2022");
//       if (opera == null) {
//           System.out.println("\nНет такого релиза - внести изменения нельзя!");
//       } else {
//           int id = opera.getId();
//           int status = opera.getStatus();
//           // при каждом сохранении, у нас меняется версия объекта
//           int newVersion = opera.getVersion() + 1;
//           Date newDate = opera.getOperaDate();
//           String newLabel = opera.getLabel();
//           String newDesc = "Изменённое описание " + newVersion +" ver";
//           String newCategory = opera.getCategory();
//           opera = ctx.getBean(OperaController.class).changeOpera(
//                   new OperaEntity(id, status, newVersion, newDate, newLabel, newDesc, newCategory)
//           );
//           //посмотрим, что получилось
//           ctx.getBean(OperaController.class).getOperaInfo("Test add","01.04.2022");
//       }
//
//
        // заведём новый релиз
        System.out.println("\nзаведём новый релиз Test delete  01.05.2022");
        opera = ctx.getBean(OperaController.class).getOpera("Test delete","01.05.2022");
        if (opera == null) {
            System.out.println("\nНет такого релиза");
            //заводим
            date = df.parse("01.05.2022");
            opera = ctx.getBean(OperaController.class).createOpera(
                    new OperaEntity(null, 1, 1, date, "Test delete", "Проверка удаления", "18+", null)
            );
            // выведем результат заведения
            ctx.getBean(OperaController.class).getOperaInfo("Test delete","01.05.2022");
        } else {
            System.out.println("\nТакой релиз уже есть!");
        }
        // теперь удалим
        // для удаления сначала определим его и  удалим
        // оказалось, что удаление чего-то как-то сбрасывает контекст
        // поэтому двумя командами в методе не получилось (надо ещё подумать)
        // я не разобрался, но переопределение восстанавливает связи и операцию можно выполнить
        // по порядку, но разными методами - работает!
        System.out.println("\nТеперь его удалим.");
        //сначала билеты
        opera = ctx.getBean(OperaController.class).getOpera("Test delete","01.05.2022");
        ctx.getBean(OperaController.class).deleteTickets(opera);
        //потом сам релиз
        opera = ctx.getBean(OperaController.class).getOpera("Test delete","01.05.2022");
        ctx.getBean(OperaController.class).deleteOpera(opera);
        opera = ctx.getBean(OperaController.class).getOpera("Test delete","01.05.2022");
        if (opera == null) {
            System.out.println("\nРелиз был удалён!");
        } else {
            System.out.println("\nТакого релиза уже нет!");
        }


        // заведём новый релиз
        System.out.println("\nзаведём новый релиз Борис Годунов  01.05.2022");
        opera = ctx.getBean(OperaController.class).getOpera("Борис Годунов","01.05.2022");
        if (opera == null) {
            System.out.println("\nНет такого релиза");
            //заводим
            date = df.parse("01.05.2022");
            opera = ctx.getBean(OperaController.class).createOpera(
                    new OperaEntity(null, 1, 1, date, "Борис Годунов", "Душещипательная история про русского царя", "18+", null)
            );
            // выведем результат заведения
            ctx.getBean(OperaController.class).getOperaInfo("Борис Годунов","01.05.2022");
        } else {
            System.out.println("\nТакой релиз уже есть!");
        }


        System.out.println("\nПоказ билетов");
        ctx.getBean(OperaController.class).getOperaInfo("Борис Годунов","01.05.2022");

        System.out.println("\n\n Продадим пару билетов");
        // установим релиз
        opera = ctx.getBean(OperaController.class).getOpera("Борис Годунов","01.05.2022");
        // вызовем продажу
        ctx.getBean(TicketController.class).sellTicket(opera, 1,10);
        ctx.getBean(TicketController.class).sellTicket(opera, 1,4);
        ctx.getBean(TicketController.class).sellTicket(opera, 1,5);
        //отобразим зал после операции
        ctx.getBean(OperaController.class).getOperaInfo("Борис Годунов","01.05.2022");
        // сдадим билет
        ctx.getBean(TicketController.class).returnTicket(opera, 1,10);
        //отобразим зал после операции
        ctx.getBean(OperaController.class).getOperaInfo("Борис Годунов","01.05.2022");

    }

}
