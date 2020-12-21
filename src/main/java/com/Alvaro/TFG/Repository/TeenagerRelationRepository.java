package com.Alvaro.TFG.Repository;

import com.Alvaro.TFG.Model.Teenager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Alvaro.TFG.Model.TeenagerRelation;

import java.util.List;

@Repository
public interface TeenagerRelationRepository extends JpaRepository<TeenagerRelation, Long>{

    List<TeenagerRelation> findTeenagerRelationByPrimaryTeenager(Teenager primaryTeenager);
    List<TeenagerRelation> findTeenagerRelationsByPrimaryTeenagerAndTime(Teenager primaryTeenager, String time);
}
