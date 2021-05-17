package com.example.practice.domain.dao;

import com.example.practice.domain.entity.Contact;

import java.util.List;
import java.util.Map;

public interface ContactDao {
    void create (Contact contact);

    List<Contact> getAll();

    Contact findById(int id);
}
