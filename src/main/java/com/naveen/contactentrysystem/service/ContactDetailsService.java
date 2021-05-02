package com.naveen.contactentrysystem.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class ContactDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

	public List<Contacts> getAllContacts() {

		List<Contacts> contacts = new ArrayList<>();

		try {
			contacts = contactsRepository.findAll();
		} catch (Exception e) {
			logger.error("Exception occured while fetching the contacts. Details: {}", e.getMessage(), e);
		}
		return contacts;
	}

	public Contacts getContactById(int id) {

		return contactsRepository.findById(id).orElse(null);
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
