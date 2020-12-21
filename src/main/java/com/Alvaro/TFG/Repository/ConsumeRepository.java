package com.Alvaro.TFG.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Alvaro.TFG.Model.Consume;

@Repository
public interface ConsumeRepository extends JpaRepository<Consume, Long>{

}
