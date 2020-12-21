package com.Alvaro.TFG.Repository;

import com.Alvaro.TFG.Model.EgocentricConsume;
import com.Alvaro.TFG.Model.Teenager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EgocentricConsumeRepository extends JpaRepository<EgocentricConsume, Long> {
    List<EgocentricConsume> findAllByTeenager(Teenager teenager);
}
