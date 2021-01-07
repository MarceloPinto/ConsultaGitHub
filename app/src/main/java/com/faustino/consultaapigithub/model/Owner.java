package com.faustino.consultaapigithub.model;

public class Owner {

    private String login; //Nome do autor
    private String avatar_url; //Imagem do autor

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
