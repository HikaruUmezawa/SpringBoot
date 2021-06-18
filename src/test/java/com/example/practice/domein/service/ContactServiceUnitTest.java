package com.example.practice.domein.service;

import com.example.practice.PracticeApplication;
import com.example.practice.domain.dao.ContactDao;
import com.example.practice.domain.entity.Contact;
import com.example.practice.domain.service.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = PracticeApplication.class)
public class ContactServiceUnitTest {

    @Mock
    ContactDao contactDao;

    @InjectMocks
    ContactServiceImpl contactService;

    @Test
    public void testCreate() {
        Contact contact = new Contact();

        contactService.save(contact);

        verify(contactDao, times(1)).create(contact);
    }


    @Test
    public void testGetAll() {
        //テスト用リストの作成
        List<Contact> contactList = new ArrayList<>();
        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        Contact contact3 = new Contact();

        contactList.add(contact1);
        contactList.add(contact2);
        contactList.add(contact3);

        //テスト用戻り値を指定
        when(contactDao.getAll()).thenReturn(contactList);

        List<Contact> testList;
        testList = contactService.getAll();

        verify(contactDao, times(1)).getAll();

        assertThat(testList.size(), is(3));
    }

    @Test
    public void testFindById() {
        //テスト用リスト作成
        List<Contact> contactList = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setId(1);

        contactList.add(contact1);

        //戻り値の指定
        when(contactDao.findById(1)).thenReturn(contact1);

        Contact testContact;
        testContact = contactService.findById(1);

        verify(contactDao, times(1)).findById(1);

        assertThat(testContact.getId(), is(1));
    }

}
