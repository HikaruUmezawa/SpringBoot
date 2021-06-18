package com.example.practice.domein.service;

import com.example.practice.CsvDataSetLoader;
import com.example.practice.PracticeApplication;
import com.example.practice.TestConfiguration;
import com.example.practice.domain.entity.Contact;
import com.example.practice.domain.service.ContactService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, //DIを利用できるようにする
        TransactionDbUnitTestExecutionListener.class, //DB操作の際のトランザクションを設定する場合は追加する
        DbUnitTestExecutionListener.class
})
@SpringBootTest(classes = {PracticeApplication.class})
@Import(TestConfiguration.class)
@DatabaseSetup(value = "/combinedTestData/")
@Transactional //メソッドの実行のたびにロールバックする
public class ContactServiceCombinedTest {

    @Autowired
    ContactService contactService;

    @Test
    public void testCreate(){
        //入れるデータ
        String name = "ロハス";
        String mail = "rojas@email.com";
        String content = "ロハスです。";

        Contact contact = new Contact();
        contact.setName(name);
        contact.setMail(mail);
        contact.setContent(content);

        contactService.save(contact);

        assertThat(contact.getName(),is(name));
        assertThat(contact.getMail(),is(mail));
        assertThat(contact.getContent(),is(content));
    }

    @Test
    public void testGetAll(){
        List<Contact> testList = contactService.getAll();

        int expectedNum = 9;

        assertThat(testList.size(),is(expectedNum));
    }

    @Test
    public void testFindById(){
        Contact testContact = contactService.findById(1);

        String expectedName = "近本";
        String expectedMail = "chikamoto@email.com";
        String expectedContent = "近本です。";

        assertThat(testContact.getName(),is(expectedName));
        assertThat(testContact.getMail(),is(expectedMail));
        assertThat(testContact.getContent(),is(expectedContent));
    }
}
