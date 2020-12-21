package com.Alvaro.TFG.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Alvaro.TFG.Model.ContactFrec;

@Repository
public interface ContactFrecRepository extends JpaRepository<ContactFrec, Long>{

}
