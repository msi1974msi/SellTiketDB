package ru.home.vtb.operasales.operasalesapp.operas;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Opera {
    private String operaName;
    private String operaDate;
    private String category;
    private String description;
    // условная схема зала 10X10. понятно, что карта зала в "жизни" сложнее
    // порядок хранения не важен - важен ключ
    private Map<String, Integer> tikets = new HashMap<>(); // key = rowXcol

    public Opera(String name, String sDate, String category, String description) {
        this.operaName = name;
        this.operaDate = sDate;
        this.category = category;
        this.description = description;
        // первичное заведение билетов
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++){
                tikets.put( (i+1) + "X" + (j+1) , 0);
            }
        }
    }

    public void changeOpera(String name, String sDate, String category, String description){
        this.operaName = name;
        this.operaDate = sDate;
        this.category = category;
        this.description = description;
    }


    public int countfreetikets(){
        int counter = 0;
        Iterator<Map.Entry<String, Integer>> itr = tikets.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Integer> entry =  itr.next();
            String key = entry.getKey();
            int value = entry.getValue();
            if (value == 0) {
                counter++;
            }
        }
        return counter;
    }

    public synchronized int saleTiket(int row, int place) {
        String key = row + "X" + place;
        int value;
        int result = 0;

        if (tikets.containsKey(key) ) {
            value = tikets.get(key);
            if (value == 0) {
                //System.out.println("Билет доступен для покупки");
                tikets.replace(key,1);
                result = 1;
            } else {
                //System.out.println("Билет уже недоступен для покупки!");
                result = 2;
            }

        } else {
            result = 3 ;
            //System.out.println("Запрошенного билета не существует!");
        }

        return result;
    }

    public synchronized void dropTiket(int row, int place) {
        String key = row + "X" + place;
        int value;
        if (tikets.containsKey(key) ) {
            value = tikets.get(key);
            if (value == 1) {
                //System.out.println("Билет можно вернуть");
                tikets.replace(key,0);
            } else {
                System.out.println("Билет уже был сдан, откуда у вас ещё одна копия?");
            }

        } else {
            System.out.println("Этот билет точно не из той Оперы!");
        }
    }

    public String getOperaName(){
        return this.operaName;
    }

    public String getCategory(){
        return this.category;
    }

    public String getDescription(){
        return this.description;
    }

    @Override
    public String toString(){
        StringBuilder sOpera = new StringBuilder();
//
        sOpera.append("Наименование: " + this.operaName +  "\n");
        sOpera.append("Время проведения: " + this.operaDate + "\n");
        sOpera.append("Возростная категория: " + this.category + "\n");
        sOpera.append("Количество свободных билетов = " + this.countfreetikets() + "\n");
        sOpera.append("----------------------------------------------------------------------------\n");
        sOpera.append("| Ряд |          места (Х - занято, O - свободно)\n");
        sOpera.append("----------------------------------------------------------------------------\n");
        for (int i = 0; i < 10; i++) {
            sOpera.append(String.format("| %2d  ", i+1));
            for (int j = 0; j < 10; j++){
              int value = tikets.get((i+1) + "X" + (j+1));
              sOpera.append(String.format("| %2d", j+1)+"("+ (value == 1 ? "X": "O") + ")");
            }
            sOpera.append("\n");
            sOpera.append("----------------------------------------------------------------------------\n");
        }
        //sOpera.append("Всего свободных билетов: "+count);

        return String.valueOf(sOpera);
    }
}
