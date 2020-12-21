package com.Alvaro.TFG.Repository;

import com.Alvaro.TFG.Model.Proyect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Alvaro.TFG.Model.Teenager;

import java.util.List;

@Repository
public interface TeenagerRepository extends JpaRepository<Teenager, Long>{

    List<Teenager> findTeenagersByProyect(Proyect proyect);
    List<Teenager> findTeenagerByIdTeenager(int id);
}
