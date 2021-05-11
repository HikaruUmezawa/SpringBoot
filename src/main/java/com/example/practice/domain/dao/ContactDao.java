package com.example.practice.domain.dao;

import com.example.practice.domain.entity.Contact;

import java.util.List;

public interface ContactDao {
    void create (Contact contact);

    List<Contact> getAll();
}
