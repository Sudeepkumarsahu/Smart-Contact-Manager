package com.scm.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

import lombok.var;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepo contactRepo;

    public ContactServiceImpl(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @Override
    public Contact save(Contact contact) {
        String contactid = UUID.randomUUID().toString();
        contact.setContactId(contactid);
        return contactRepo.save(contact);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();

    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));

        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);

    }

    @Override
    public Contact update(Contact contact) {

        var contactOld = contactRepo.findById(contact.getContactId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact Not Found"));

        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPhone(contact.getPhone());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        return contactRepo.save(contactOld);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contact> searchByName(String name, int size, int page, String sortBy, String order, User user) {
        // TODO Auto-generated method stub
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByNameContainingAndUser(name, user, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByEmailContainingAndUser(email, user, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String order,
            User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByPhoneContainingAndUser(phoneNumber, user, pageable);
    }

}
