package com.example.practice.domein.dao;

import com.example.practice.CsvDataSetLoader;
import com.example.practice.PracticeApplication;
import com.example.practice.TestConfiguration;
import com.example.practice.domain.dao.ContactDaoImpl;
import com.example.practice.domain.entity.Contact;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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
@Transactional //メソッドの実行のたびにロールバックする
public class ContactDaoTest {

    @Autowired
    private ContactDaoImpl contactDao;

    @Test
    @DatabaseSetup(value = "/testData/") //初期状態のDB
    @ExpectedDatabase(value = "/expectedData/", assertionMode = DatabaseAssertionMode.NON_STRICT) //期待されるDB
    //createメソッドのテスト
    public void testCreate() throws Exception {
        //保存するデータ準備
        Contact contact = new Contact();
        contact.setName("test3");
        contact.setMail("test3@email.com");
        contact.setContent("テスト3です。");

        contactDao.create(contact);
    }

    //getAllメソッドのテスト
    @Test
    @DatabaseSetup(value = "/testData/")
    public void testGetAll() throws Exception {
        List<Contact> contactList = contactDao.getAll();

        assertThat(contactList.size(), is(2));
    }

    //findByIdメソッドのテスト
    @Test
    @DatabaseSetup(value = "/testData/")
    public void testFindById() throws Exception {
        Contact contact = contactDao.findById(1);

        assertThat(contact.getId(), is(1));
        assertThat(contact.getName(), is("test1"));
        assertThat(contact.getMail(), is("test1@email.com"));
        assertThat(contact.getContent(), is("テスト1です。"));
    }
}
