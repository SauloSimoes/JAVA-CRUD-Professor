package com.template.validator;

import static com.template.util.DialogUtil.mostrarErro;
import java.util.regex.Pattern;

public class ProfessorValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");


    public static boolean validarCampos(String nome, String email, String disciplina, String salarioTexto) {
        if (nome == null || nome.trim().isEmpty()) {
            mostrarErro("Erro de Validação", "Campo Obrigatório", "O campo Nome não pode estar vazio.");
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            mostrarErro("Erro de Validação", "Campo Obrigatório", "O campo E-mail não pode estar vazio.");
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            mostrarErro("Erro de Validação", "E-mail Inválido", "Por favor, insira um endereço de e-mail válido (ex: usuario@dominio.com).");
            return false;
        }

        if (disciplina == null || disciplina.trim().isEmpty()) {
            mostrarErro("Erro de Validação", "Campo Obrigatório", "O campo Disciplina não pode estar vazio.");
            return false;
        }

        if (salarioTexto == null || salarioTexto.trim().isEmpty()) {
            mostrarErro("Erro de Validação", "Campo Obrigatório", "O campo Salário não pode estar vazio.");
            return false;
        }

        try {
            double salario = Double.parseDouble(salarioTexto.trim());
            if (salario < 0) {
                mostrarErro("Erro de Validação", "Valor Inválido", "O salário não pode ser um número negativo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarErro("Erro de Validação", "Valor Inválido", "Informe um valor numérico válido para o salário.");
            return false;
        }

        return true;
    }

    public static boolean validarId(String idTexto) {
        if (idTexto == null || idTexto.trim().isEmpty()) {
            mostrarErro("Aviso de Seleção", "Nenhum Registro Selecionado", "Selecione um professor na tabela ou informe o ID.");
            return false;
        }

        try {
            Integer.parseInt(idTexto.trim());
            return true;
        } catch (NumberFormatException e) {
            mostrarErro("Erro de Validação", "ID Inválido", "O ID informado deve ser um número inteiro válido.");
            return false;
        }
    }
}