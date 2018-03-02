/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

import com.sg.model.Contact;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.sg.dao.SearchTerm.*;

/**
 *
 * @author Ben Norman
 */
public class ContactListDaoInMemImpl implements ContactListDao {

    // holds all of our Contact objects - simulates the database
    private Map<Long, Contact> contactMap = new HashMap<>();
    // used to assign ids to Contacts - simulates the auto increment
    // primary key for Contacts in a database
    private static long contactIdCounter = 0;

    @Override
    public Contact addContact(Contact contact) {
        // assign the current counter values as the contactid
        contact.setContactId(contactIdCounter);
        // increment the counter so it is ready for use for the 
        // next contact
        contactIdCounter++;
        contactMap.put(contact.getContactId(), contact);
        return contact;
    }

    @Override
    public void removeContact(long contactId) {
        contactMap.remove(contactId);
    }

    @Override
    public void updateContact(Contact contact) {
        contactMap.put(contact.getContactId(), contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        Collection<Contact> c = contactMap.values();
        return new ArrayList(c);
    }

    @Override
    public Contact getContactById(long contactId) {
        return contactMap.get(contactId);
    }

    @Override
    public List<Contact> searchContacts(Map<SearchTerm, String> criteria) {
        return contactMap.values().stream()
                .filter(createPredicate(FIRST_NAME,criteria.get(FIRST_NAME))
                        .and(createPredicate(LAST_NAME,criteria.get(LAST_NAME)))
                        .and(createPredicate(COMPANY,criteria.get(COMPANY)))
                        .and(createPredicate(PHONE,criteria.get(PHONE)))
                        .and(createPredicate(EMAIL,criteria.get(EMAIL))))
                .collect(Collectors.toList());
    }
    
    private Predicate<Contact> createPredicate(SearchTerm term, String field){
        if(field==null || field.isEmpty()){
            return (c) -> true;
        }
        switch(term){
            case FIRST_NAME: return (c) -> c.getFirstName().equals(field);
            case LAST_NAME: return (c) -> c.getLastName().equals(field);
            case COMPANY: return (c) -> c.getCompany().equals(field);
            case PHONE: return (c) -> c.getPhone().equals(field);
            case EMAIL: return (c) -> c.getEmail().equals(field);
        }
        throw new IllegalArgumentException("unknown search term");
    }
}
// reduction from 155 lines to 88 lines a reduction of 67 lines or 43% less code