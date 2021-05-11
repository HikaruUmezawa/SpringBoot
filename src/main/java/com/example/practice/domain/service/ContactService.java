package com.example.practice.domain.service;

import com.example.practice.domain.entity.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {
    void save(Contact contact);

    List<Contact> getAll();
}
