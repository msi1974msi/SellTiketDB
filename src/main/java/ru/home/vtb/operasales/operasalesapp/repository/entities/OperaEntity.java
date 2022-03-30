package ru.home.vtb.operasales.operasalesapp.repository.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Table(name = "opera")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperaEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="status")
    private int status;

    @Column(name="version")
    private int version;

    @Column(name="operadate")
    private Date operaDate;

    @Column(name="label", length = 254)
    private String label;

    @Column(name="description", length = 1000)
    private String description;

    @Column(name="category", length = 50)
    private String category;

    @OneToMany(mappedBy = "opera", fetch = FetchType.EAGER)
    private Collection<TicketEntity> tickets;

    @Override
    public String toString() {
        StringBuilder sOpera = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        sOpera.append("Наименование: " + label +  "\n");
        sOpera.append("Время проведения: " + df.format(operaDate) + "\n");
        sOpera.append("Возростная категория: " + category + "\n");
        sOpera.append("Количество свободных билетов = " + freeTickest() + "\n");
        sOpera.append("----------------------------------------------------------------------------\n");
        sOpera.append("| Ряд |          места (Х - занято, O - свободно)\n");
        sOpera.append("----------------------------------------------------------------------------\n");
        // далее по начитанной коллекции
        for (int i = 1; i < 11; i++) {
            sOpera.append(String.format("| %2d  ", i));
            for (int j = 1; j < 11; j++){
                for (TicketEntity tiket : tickets) {
                   if ( tiket.getRow() == i && tiket.getPlace() == j) {
                        sOpera.append(String.format("| %2d", j)+"("+ (tiket.getStatus() == 1 ? "X": "O") + ")");
                   }
                }
            }
            sOpera.append("\n");
            sOpera.append("----------------------------------------------------------------------------\n");
        }

        return String.valueOf(sOpera);
    }

    public int freeTickest() {
        int cnt = 0;
        for (TicketEntity tiket : tickets) {
            if ( tiket.getStatus() == 0) {
               cnt++;
            }
        }
        return cnt;
    }
}
