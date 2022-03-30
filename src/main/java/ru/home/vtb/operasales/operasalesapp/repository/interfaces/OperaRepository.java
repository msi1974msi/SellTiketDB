package ru.home.vtb.operasales.operasalesapp.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.vtb.operasales.operasalesapp.repository.entities.OperaEntity;

import java.util.Date;

public interface OperaRepository extends JpaRepository<OperaEntity, Long> {

    OperaEntity findById(int id);

    OperaEntity findByLabelAndOperaDate(String label, Date date);
}
