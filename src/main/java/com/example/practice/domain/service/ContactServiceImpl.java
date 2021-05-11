package com.example.practice.domain.service;

import com.example.practice.domain.dao.ContactDao;
import com.example.practice.domain.entity.Contact;
import com.example.practice.domain.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDao dao;

    public ContactServiceImpl(ContactDao dao){
        this.dao = dao;
    }

    @Override
    public void save(Contact contact){
        dao.create(contact);
    }

    @Override
    public List<Contact> getAll(){
      return dao.getAll();
    }

}
