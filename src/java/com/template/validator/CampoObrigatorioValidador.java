package com.template.validator;

import java.util.regex.Pattern;

public class CampoObrigatorioValidador implements Validador<String> {
    private final String nomeCampo;
    private final String valor;

    public CampoObrigatorioValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        return this.valor != null && !this.valor.trim().isEmpty();
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve ser preenchido.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

class EmailValidador implements Validador<String> {
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.\\w+$";
    private final Pattern pattern = Pattern.compile(EMAIL_REGEX);
    private final String email;

    public EmailValidador(String email) {
        this.email = email;
    }

    @Override
    public boolean validar(String valorAtual) {
        return this.email != null && pattern.matcher(this.email).matches();
    }

    @Override
    public String getMensagemErro() {
        return "Digite um e-mail válido (exemplo@dominio.com)!";
    }

    @Override
    public String getValor() {
        return email;
    }
}