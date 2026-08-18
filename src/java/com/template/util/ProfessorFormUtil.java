package com.template.util;

import com.template.model.ProfessorDTO;
import javafx.scene.control.TextField;

public class ProfessorFormUtil {

    public static ProfessorDTO construir(TextField intId,
            TextField txtNome,
            TextField txtEmail,
            TextField numSalario,
            TextField txtDisciplina) {

        int id = intId.getText() == null || intId.getText().trim().isEmpty()
                ? 0
                : Integer.parseInt(intId.getText().trim());

        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String disciplina = txtDisciplina.getText().trim();
        double salario = Double.parseDouble(numSalario.getText().trim());

        return new ProfessorDTO(id, nome, email, salario, disciplina);
    }
}
