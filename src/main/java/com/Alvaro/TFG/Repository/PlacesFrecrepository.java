package com.Alvaro.TFG.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Alvaro.TFG.Model.PlacesFrec;

@Repository
public interface PlacesFrecrepository extends JpaRepository<PlacesFrec, Long>{

}
