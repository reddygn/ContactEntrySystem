package com.naveen.contactentrysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naveen.contactentrysystem.entity.Contacts;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Integer>{


}
