package com.Alvaro.TFG.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Alvaro.TFG.Model.Proyect;

import java.util.List;

@Repository
public interface ProyectRepository extends JpaRepository<Proyect, Long>{

    List<Proyect> findProyectsByUsersUsername(String username);

    @Query("SELECT p from Proyect p where p.idProyect = :id")
    List<Proyect> findProyectByIdProyect(@Param("id") int id);
}
