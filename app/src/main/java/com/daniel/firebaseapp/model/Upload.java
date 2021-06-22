package com.daniel.firebaseapp.model;

public class Upload {
    private String id;
    private String nomeImagem;
    private String url;

    //firebase utiliza esse construtor -> envia dados
    public  Upload(){
    }

    public Upload(String id, String nomeImagem, String url) {
        this.id = id;
        this.nomeImagem = nomeImagem;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }
}
