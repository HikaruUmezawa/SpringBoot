package com.example.practice.app.contact;

import com.example.practice.domain.entity.Contact;
import com.example.practice.domain.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    //serviceクラスをインスタンス化
    @Autowired
    private ContactService service;

    //お問い合わせページ
    @GetMapping("/contact")
    public String contactForm(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }

    //問い合わせ送信、確認画面へ
    @PostMapping("/confirm")
    //バリデーション済みの値を取得
    public String contactSubmit(@Validated ContactForm contactForm, BindingResult result) {
        if (result.hasErrors()){
            //入力エラーがあった場合
            return "contact";
        }
        return "confirm";
    }

    //確認画面で戻るボタンが押されたとき
    @PostMapping("/contact")
    public String contactBack(ContactForm contactForm){
        return "contact";
    }


    //問い合わせ保存処理
    @PostMapping("/contact/save")
    public String contactSave(ContactForm contactForm){
        Contact contact = new Contact();
        contact.setName((String) contactForm.getName());
        contact.setMail((String) contactForm.getMail());
        contact.setContent((String) contactForm.getContent());
        service.save(contact);

        return "complete";
    }

    //問い合わせ一覧
    @GetMapping("/contact/list")
    public  String contactList(Model model){
        model.addAttribute("contacts", service.getAll());

        return "list";
    }

    //問い合わせ詳細
    @GetMapping("/contact/detail/{id}")
    public String contactDetail(@PathVariable("id") int id, Model model){
        model.addAttribute("contact",service.findById(id));

        return "detail";
    }


}
