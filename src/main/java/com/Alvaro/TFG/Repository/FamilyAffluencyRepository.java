package com.Alvaro.TFG.Repository;

import com.Alvaro.TFG.Model.FamilyAffluency;
import com.Alvaro.TFG.Model.Teenager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyAffluencyRepository extends JpaRepository<FamilyAffluency, Long> {
    List<FamilyAffluency> findAllByTeenager(Teenager teenager);
}
