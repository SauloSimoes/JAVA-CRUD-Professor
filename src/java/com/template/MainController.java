package com.template;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class MainController {

    @FXML
    private Button btnCadastrarAction;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;

    @FXML
    private TextField intId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtDisciplina;

    @FXML
    private TextField numSalario;

    @FXML
    private TableView<ProfessorDTO> tblVolei;

    @FXML
    private TableColumn<ProfessorDTO, Integer> colId;

    @FXML
    private TableColumn<ProfessorDTO, String> colNome;

    @FXML
    private void initialize() {

        System.out.println("FXML loaded successfully!");
    }

    @FXML
    private void btnCadastrarAction() {

        try {

            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String disciplina = txtDisciplina.getText();

            double salario = Double.parseDouble(numSalario.getText());

            ProfessorDTO professor = new ProfessorDTO(
                    0,
                    nome,
                    email,
                    salario,
                    disciplina
            );

            ProfessorDAO dao = new ProfessorDAO();

            dao.inserir(professor);

            limparCampos();

            System.out.println("Professor salvo");

        } catch (Exception e) {

            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void limparCampos() {

        intId.clear();
        txtNome.clear();
        txtEmail.clear();
        txtDisciplina.clear();
        numSalario.clear();
    }
}