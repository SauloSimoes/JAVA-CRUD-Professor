package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class MainController {

    @FXML
    private TableView<ProfessorDTO> tblProfessor;
    @FXML
    private TableColumn<ProfessorDTO, Integer> colId;
    @FXML
    private TableColumn<ProfessorDTO, String> colNome;
    @FXML
    private TableColumn<ProfessorDTO, String> colEmail;
    @FXML
    private TableColumn<ProfessorDTO, Double> colSalario;
    @FXML
    private TableColumn<ProfessorDTO, String> colDisciplina;

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
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplina"));

        carregarProfessor();
    }

    private void carregarProfessor() {
        try {
            ProfessorDAO ProfDAO = new ProfessorDAO();
            List<ProfessorDTO> listaProfessor = ProfDAO.listar();

            ObservableList<ProfessorDTO> dadosTabela = FXCollections.observableArrayList(listaProfessor);
            tblProfessor.setItems(dadosTabela);

            System.out.println("Tabela atualizada");

        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }

    @FXML
    private void carregarCampos() {
        ProfessorDTO objProfessorDTO = tblProfessor.getSelectionModel().getSelectedItem();

        if (objProfessorDTO != null) {
            intId.setText(String.valueOf(objProfessorDTO.getId()));
            txtNome.setText(objProfessorDTO.getNome());
            txtEmail.setText(objProfessorDTO.getEmail());
            txtDisciplina.setText(objProfessorDTO.getDisciplina());
            numSalario.setText(String.valueOf(objProfessorDTO.getSalario()));
        }
    }

    @FXML
    private void btnCadastrarAction() {
        try {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String disciplina = txtDisciplina.getText();
            double salario = Double.parseDouble(numSalario.getText());
            ProfessorDTO ProfDTO = new ProfessorDTO(0, nome, email, salario, disciplina);

            ProfessorDAO ProfDAO = new ProfessorDAO();
            ProfDAO.inserir(ProfDTO);

            System.out.println("Professor cadastrado");
            carregarProfessor();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    @FXML
    private void btnExcluirAction() {
        try {
            int id = Integer.parseInt(intId.getText());

            ProfessorDAO ProfDAO = new ProfessorDAO();
            ProfDAO.deletar(id);

            System.out.println("Professor excluído");
            carregarProfessor();
            btnLimparAction();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    @FXML
    private void btnLimparAction() {
        intId.clear();
        txtNome.clear();
        txtEmail.clear();
        txtDisciplina.clear();
        numSalario.clear();
    }

    @FXML
    private void btnEditarAction() {
        try {
            int id = Integer.parseInt(intId.getText());
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String disciplina = txtDisciplina.getText();
            double salario = Double.parseDouble(numSalario.getText());

            ProfessorDTO ProfDTO = new ProfessorDTO(id, nome, email, salario, disciplina);
            ProfessorDAO ProfDAO = new ProfessorDAO();
            ProfDAO.atualizar(ProfDTO);

            System.out.println("Professor editado");
            carregarProfessor();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}