package com.Alvaro.TFG.Repository;

import com.Alvaro.TFG.Model.EgocentricExtraConsume;
import com.Alvaro.TFG.Model.Teenager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EgocentricExtraConsumeRepository extends JpaRepository<EgocentricExtraConsume, Long> {
    List<EgocentricExtraConsume> findAllByTeenager(Teenager teenager);
}
