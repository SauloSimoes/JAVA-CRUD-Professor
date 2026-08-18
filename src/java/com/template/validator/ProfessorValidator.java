package com.template.validator;

import java.util.regex.Pattern;

public class ProfessorValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private ProfessorValidator() {
    }

    public static String validarCampos(String nome, String email, String disciplina, String salarioTexto) {
        if (nome == null || nome.trim().isEmpty()) {
            return "O campo Nome não pode estar vazio.";
        }

        if (email == null || email.trim().isEmpty()) {
            return "O campo E-mail não pode estar vazio.";
        }

        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return "Por favor, insira um endereço de e-mail válido (ex: usuario@dominio.com).";
        }

        if (disciplina == null || disciplina.trim().isEmpty()) {
            return "O campo Disciplina não pode estar vazio.";
        }

        if (salarioTexto == null || salarioTexto.trim().isEmpty()) {
            return "O campo Salário não pode estar vazio.";
        }

        try {
            double salario = Double.parseDouble(salarioTexto.trim());
            if (salario < 0) {
                return "O salário não pode ser um número negativo.";
            }
        } catch (NumberFormatException e) {
            return "Informe um valor numérico válido para o salário.";
        }

        return null;
    }

    public static String validarId(String idTexto) {
        if (idTexto == null || idTexto.trim().isEmpty()) {
            return "Selecione um professor na tabela ou informe o ID.";
        }

        try {
            Integer.parseInt(idTexto.trim());
            return null;
        } catch (NumberFormatException e) {
            return "O ID informado deve ser um número inteiro válido.";
        }
    }
}