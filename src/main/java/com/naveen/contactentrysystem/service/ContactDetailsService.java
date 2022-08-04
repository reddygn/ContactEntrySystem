package com.naveen.contactentrysystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.ContextNotEmptyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.naveen.contactentrysystem.controller.ContactDetailsController;
import com.naveen.contactentrysystem.dto.CallList;
import com.naveen.contactentrysystem.dto.NameDto;
import com.naveen.contactentrysystem.dto.PhoneDto;
import com.naveen.contactentrysystem.entity.Address;
import com.naveen.contactentrysystem.entity.Contacts;
import com.naveen.contactentrysystem.entity.Name;
import com.naveen.contactentrysystem.entity.Phone;
import com.naveen.contactentrysystem.repository.AddressRepository;
import com.naveen.contactentrysystem.repository.ContactsRepository;
import com.naveen.contactentrysystem.repository.NameRepository;
import com.naveen.contactentrysystem.repository.PhoneRepository;
import com.naveen.contactentrysystem.util.ContactEntrySystemException;

@Service
public class ContactDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(ContactDetailsController.class);

	@Autowired
	ContactsRepository contactsRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	PhoneRepository phoneRepository;

	@Autowired
	NameRepository nameRepository;

	public Contacts addContact(Contacts addContactRequest) {

		Contacts contact = new Contacts();

		try {

			addressRepository.saveAndFlush(addContactRequest.getAddress());
			nameRepository.saveAndFlush(addContactRequest.getName());
			addContactRequest.getPhone().forEach(p -> phoneRepository.saveAndFlush(p));

			contact = contactsRepository.saveAndFlush(addContactRequest);

		} catch (Exception e) {
			logger.error("Exception occured while fetching the contacts. Details: {}", e.getMessage(), e);
		}

		return contact;

	}

	public ResponseEntity<List<Contacts>> getAllContacts() {

		List<Contacts> contacts = new ArrayList<>();

		contacts = contactsRepository.findAll();

		if (contacts.size() > 0) {
			return new ResponseEntity<List<Contacts>>(contacts, HttpStatus.OK);

		} else {
			logger.error("There is no data available in the server");
			return new ResponseEntity<List<Contacts>>(contacts, HttpStatus.NOT_FOUND);

		}
	}

	public ResponseEntity<Contacts> getContactById(int id) throws Exception {

		Contacts response = contactsRepository.findById(id).orElse(null);

		if (response == null) {
			logger.error("No Record Found With The Given ID :: " + id);
			return new ResponseEntity<Contacts>(response, HttpStatus.NOT_FOUND);
		}

		else {
			return new ResponseEntity<Contacts>(response, HttpStatus.OK);

		}

	}

	public Contacts updateContact(int id, Contacts updateContactRequest) {

		Contacts contact = new Contacts();

		try {
			contact = contactsRepository.findById(id).orElse(null);

			if (contact != null) {

				int addressId = updateContactRequest.getAddress().getId();
				Address address = addressRepository.findById(addressId).orElse(null);

				address.setStreet(updateContactRequest.getAddress().getStreet());
				address.setCity(updateContactRequest.getAddress().getCity());
				address.setState(updateContactRequest.getAddress().getState());
				address.setZip(updateContactRequest.getAddress().getZip());
				addressRepository.saveAndFlush(address);

				int nameId = updateContactRequest.getName().getId();
				Name name = nameRepository.findById(nameId).orElse(null);
				name.setFirst(updateContactRequest.getName().getFirst());
				name.setLast(updateContactRequest.getName().getLast());
				name.setMiddle(updateContactRequest.getName().getMiddle());
				nameRepository.saveAndFlush(name);

				List<Phone> c = contact.getPhone();
				List<Phone> req = updateContactRequest.getPhone();

				for (Phone phone : c) {

					for (Phone phone2 : req) {

						if (phone.getId().equals(phone2.getId())) {

							phone.setNumber(phone2.getNumber());
							phone.setType(phone2.getType());
						}
					}
					phoneRepository.saveAndFlush(phone);

				}

				contact.setEmail(updateContactRequest.getEmail());
				contactsRepository.saveAndFlush(contact);

			}

		} catch (Exception e) {

			logger.error("Exception occured while updating the contact. Details: {}", e.getMessage(), e);

		}

		return contact;
	}

	public List<Contacts> deleteContactById(int id) {

		try {

			Contacts i = contactsRepository.findById(id).orElse(null);

			if (i != null) {

				addressRepository.deleteById(i.getAddress().getId());
				nameRepository.deleteById(i.getName().getId());
				i.getPhone().forEach(p -> phoneRepository.deleteById(p.getId()));
				contactsRepository.deleteById(i.getId());

			}

		} catch (Exception e) {
			logger.error("Exception occured while deleting the contacts. Details: {}", e.getMessage(), e);
		}

		return contactsRepository.findAll();
	}

	public List<CallList> getCallList() {

		List<CallList> callList = new ArrayList<>();
		CallList list = new CallList();
		NameDto nameDto = new NameDto();
		List<PhoneDto> ph = new ArrayList<>();

		try {

			List<Contacts> allContacts = contactsRepository.findAll();
			for (Contacts contacts : allContacts) {

				BeanUtils.copyProperties(contacts.getName(), nameDto);
				list.setName(nameDto);

				BeanUtils.copyProperties(contacts.getPhone(), ph);
				list.setPhone(ph);
				callList.add(list);
			}

		} catch (Exception e) {
			logger.error("Exception occured while fetching the call-list. Details: {}", e.getMessage(), e);

		}

		return callList;
	}

}
