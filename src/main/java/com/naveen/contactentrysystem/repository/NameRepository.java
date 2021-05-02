package com.naveen.contactentrysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naveen.contactentrysystem.entity.Name;

@Repository
public interface NameRepository extends JpaRepository<Name, Integer>{

}
