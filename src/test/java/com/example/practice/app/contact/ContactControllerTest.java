package com.example.practice.app.contact;

import com.example.practice.PracticeApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = PracticeApplication.class)
public class ContactControllerTest {
    //Mockオブジェクト
    @Autowired
    private MockMvc mockMvc;

    @Test
        //getリクエスト 問い合わせ画面
    void contactForm処理でviewとしてcontactが渡される() throws Exception {
        this.mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"));
    }

    @Test
    void フォームに正しい値が入力されている場合は確認画面へ() throws Exception {
        ContactForm contactForm = new ContactForm();
        contactForm.setName("test");
        contactForm.setMail("test@email.com");
        contactForm.setContent("テストです。");

        this.mockMvc.perform((post("/confirm")).flashAttr("contactForm", contactForm))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("contactForm", contactForm))
                .andExpect(view().name("confirm"));
    }

    @Test
    void フォームに正しい値が入力されていない場合は問い合わせ画面へ() throws Exception {
        ContactForm contactForm = new ContactForm();
        contactForm.setName("");
        contactForm.setMail("");
        contactForm.setContent("");

        this.mockMvc.perform((post("/confirm")).flashAttr("contactForm", contactForm))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attribute("contactForm", contactForm))
                .andExpect(view().name("contact"));
    }

    @Test
    void メールアドレス形式にんっていない場合は問い合わせ画面へ() throws Exception {
        ContactForm contactForm = new ContactForm();
        contactForm.setName("test");
        contactForm.setMail("mail");
        contactForm.setContent("テストです");

        this.mockMvc.perform((post("/confirm")).flashAttr("contactForm", contactForm))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attribute("contactForm", contactForm))
                .andExpect(view().name("contact"));
    }

    @Test
    void 確認画面で戻るボタン押下で問い合わせ画面へ() throws Exception {
        ContactForm contactForm = new ContactForm();
        contactForm.setName("test");
        contactForm.setMail("test@email.com");
        contactForm.setContent("テストです。");

        this.mockMvc.perform((post("/contact")).flashAttr("contactForm", contactForm))
                .andExpect(status().isOk())
                .andExpect(model().attribute("contactForm", contactForm))
                .andExpect(view().name("contact"));
    }

    @Test
        //getリクエスト　問い合わせ一覧画面
    void contactList処理でviewとしてlistが渡される() throws Exception {
        this.mockMvc.perform(get("/contact/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"));
    }

}
