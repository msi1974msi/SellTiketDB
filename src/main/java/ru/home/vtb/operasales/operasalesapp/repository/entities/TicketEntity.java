package ru.home.vtb.operasales.operasalesapp.repository.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="opera")
    @ManyToOne
    private OperaEntity opera;

    @Column(name="status")
    private int status;

    @Column(name="version")
    private int version;

    @Column(name="row")
    private int row;

    @Column(name="place")
    private int place;

}
