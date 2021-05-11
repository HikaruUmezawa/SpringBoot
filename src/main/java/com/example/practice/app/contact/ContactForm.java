package com.example.practice.app.contact;

import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ContactForm {

    @NotBlank(message = "お名前を入力してください")
    @Size(max = 64,message = "64文字以内で入力してください")
    private String name;

    @NotBlank(message = "メールアドレスを入力してください")
    @Size(max = 128,message = "128文字以内で入力してください")
    @Email(message = "メールアドレスを正しく入力してください")
    private String mail;

    @NotBlank(message = "お問い合わせ内容を入力してください")
    @Size(max = 512,message = "512文字以内で入力してください")
    private String content;

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
