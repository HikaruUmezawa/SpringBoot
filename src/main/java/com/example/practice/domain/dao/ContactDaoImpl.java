package com.example.practice.domain.dao;

import com.example.practice.domain.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Repository
public class ContactDaoImpl implements ContactDao {
    //データベースにアクセスするためのクラス
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ContactDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    //DBに登録する
    public void create(Contact contact) {
        jdbcTemplate.update("INSERT INTO contact(name, mail, content) VALUES(?, ?, ?)",
                contact.getName(), contact.getMail(), contact.getContent());
    }

    @Override
    //全取得する
    public List<Contact> getAll(){
        //DBからid順に取得
        List<Map<String, Object>> contacts = jdbcTemplate.queryForList("SELECT * FROM contact ORDER BY id");
        //Listをつくる
        List<Contact> contactList = new ArrayList<>();
        //受け取ったMapのListをContactオブジェクトに格納
        for (Map<String,Object> eachContact: contacts){
            Contact contact = new Contact();
            contact.setId(Math.toIntExact((long) eachContact.get("id")));
            contact.setName((String) eachContact.get("name"));
            contact.setMail((String) eachContact.get("mail"));
            contact.setContent((String) eachContact.get("content"));
            contact.setCreated((Timestamp) eachContact.get("created"));
            contactList.add(contact);
        }
        return  contactList;
    }

    @Override
    //idごとに取得する
    public Contact findById(int id) {
        Map<String,Object> map = jdbcTemplate.queryForMap("SELECT * FROM contact WHERE id = ?",id);
        Contact contact = new Contact();
        contact.setId(Math.toIntExact((long) map.get("id")));
        contact.setName((String) map.get("name"));
        contact.setMail((String) map.get("mail"));
        contact.setContent((String) map.get("content"));
        contact.setCreated((Timestamp) map.get("created"));
        return contact;
    }
}