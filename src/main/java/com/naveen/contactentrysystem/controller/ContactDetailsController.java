package com.naveen.contactentrysystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.contactentrysystem.dto.CallList;
import com.naveen.contactentrysystem.entity.Contacts;
import com.naveen.contactentrysystem.service.ContactDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/contacts")
public class ContactDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(ContactDetailsController.class);

	@Autowired
	ContactDetailsService contactDetailsService;

	@GetMapping
	public List<Contacts> getAllContacts() {

		return contactDetailsService.getAllContacts();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Contacts addContact(@RequestBody Contacts addContactRequest) {

		return contactDetailsService.addContact(addContactRequest);

	}

	@GetMapping("/{id}")
	public Contacts getContactById(@PathVariable int id) {

		return contactDetailsService.getContactById(id);
	}

	@PutMapping("/{id}")
	public Contacts updateContact(@PathVariable int id, @RequestBody Contacts updateContactRequest) {

		return contactDetailsService.updateContact(id, updateContactRequest);
	}

	@DeleteMapping("/{id}")
	public List<Contacts> deleteContactById(@PathVariable int id) {

		return contactDetailsService.deleteContactById(id);
	}

	@GetMapping("/call-list")
	public List<CallList> getCallList() {

		return contactDetailsService.getCallList();
	}

}
