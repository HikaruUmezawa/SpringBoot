package com.example.practice.app.contact;

import com.example.practice.CsvDataSetLoader;
import com.example.practice.PracticeApplication;
import com.example.practice.TestConfiguration;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;
import java.util.List;

@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, //DIを利用できるようにする
        TransactionDbUnitTestExecutionListener.class, //DB操作の際のトランザクションを設定する場合は追加する
        DbUnitTestExecutionListener.class
})
@AutoConfigureMockMvc
@SpringBootTest(classes = PracticeApplication.class)
@Import(TestConfiguration.class)
@DatabaseSetup(value = "/combinedTestData/")
@Transactional //メソッドの実行のたびにロールバックする

public class ContactControllerCombinedTest {
    //テスト用のクライアント
    @Autowired
    private WebClient client;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        client = MockMvcWebClientBuilder.webAppContextSetup(context).build();
    }

    @Test
    void contactList処理でページを取得() throws Exception {
        String url = "http://localhost/contact/list";
        HtmlPage page = client.getPage(url);

        //id="id"の要素をすべて取得
        List<DomElement> list = page.getElementsById("id");
        assertThat(list.size(), is(9));
    }

    @Test
    void contactDetail処理でIDが1のページを取得() throws Exception {
        String url = "http://localhost/contact/detail/1";
        HtmlPage page = client.getPage(url);

        //id = "name","mail"を取得
        String receivedName = page.getHtmlElementById("name").getTextContent();
        String receivedMail = page.getHtmlElementById("mail").getTextContent();
        String expectedName = "近本";
        String expectedMail = "chikamoto@email.com";
        assertEquals(receivedName, expectedName);
        assertEquals(receivedMail, expectedMail);
    }
}
