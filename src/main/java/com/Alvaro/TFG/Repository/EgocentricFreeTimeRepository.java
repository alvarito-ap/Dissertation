package com.Alvaro.TFG.Repository;

import com.Alvaro.TFG.Model.EgocentricFreeTime;
import com.Alvaro.TFG.Model.Teenager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EgocentricFreeTimeRepository extends JpaRepository<EgocentricFreeTime, Long> {
    List<EgocentricFreeTime> findAllByTeenager(Teenager teenager);
}
