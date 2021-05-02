package com.naveen.contactentrysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naveen.contactentrysystem.entity.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

}
