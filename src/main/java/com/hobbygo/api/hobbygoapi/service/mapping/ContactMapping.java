package com.hobbygo.api.hobbygoapi.service.mapping;

import com.hobbygo.api.hobbygoapi.model.entity.Contact;
import com.hobbygo.api.hobbygoapi.restapi.dto.ContactDto;

import java.util.ArrayList;
import java.util.List;

public class ContactMapping {

    public static List<Contact> mapContactDtoList2ContactList(List<ContactDto> contactsDto) {
        List<Contact> contactList = new ArrayList<>();

        for(ContactDto contactDto: contactsDto)
            contactList.add(mapContactDto2Contact(contactDto));

        return contactList;
    }

    public static Contact mapContactDto2Contact(ContactDto contactDto) {
        Contact contact =  new Contact(contactDto.getName(),contactDto.getEmail(), contactDto.getPhone());
        if(contactDto.getSend())
            contact.mark2send();

        return contact;
    }
}
