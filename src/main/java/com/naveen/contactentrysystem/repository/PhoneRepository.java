package com.naveen.contactentrysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naveen.contactentrysystem.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer>{

}
