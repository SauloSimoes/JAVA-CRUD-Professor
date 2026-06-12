package com.template;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

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
    private Label lblMensagem;

    @FXML
    private Label lblTotal;

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

            ProfessorDAO profDAO = new ProfessorDAO();
            List<ProfessorDTO> listaProfessor = profDAO.listar();

            ObservableList<ProfessorDTO> dadosTabela = FXCollections.observableArrayList(listaProfessor);

            tblProfessor.setItems(dadosTabela);

            //UX 1: contador
            atualizarContador();

            //System.out.println("Tabela atualizada");

        } catch (Exception e) {

            // UI 2: cores diferentes
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Erro ao carregar professores.");
        }
    }

    // UX 1: contador
    private void atualizarContador() {
        lblTotal.setText("Total de registros: " + tblProfessor.getItems().size());
    }

    @FXML
    private void carregarCampos() {

        ProfessorDTO profDTO = tblProfessor.getSelectionModel().getSelectedItem();

        if (profDTO != null) {

            intId.setText(String.valueOf(profDTO.getId()));
            txtNome.setText(profDTO.getNome());
            txtEmail.setText(profDTO.getEmail());
            txtDisciplina.setText(profDTO.getDisciplina());
            numSalario.setText(String.valueOf(profDTO.getSalario()));
        }
    }

    @FXML
    private void btnCadastrarAction() {

        try {

            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String disciplina = txtDisciplina.getText();
            double salario = Double.parseDouble(numSalario.getText());

            ProfessorDTO profDTO = new ProfessorDTO(0, nome, email, salario, disciplina);

            ProfessorDAO profDAO = new ProfessorDAO();
            profDAO.inserir(profDTO);

            //System.out.println("Professor cadastrado");

            carregarProfessor();

            // UX 3: limpar campos
            btnLimparAction();

            // UI 2: cor diferente
            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Professor cadastrado com sucesso!");

        } catch (Exception e) {

            // UI 2: cor diferente
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Erro ao cadastrar professor.");
        }
    }

    @FXML
    private void btnExcluirAction() {

        // UX 2: confirmacao
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Excluir Professor");
        alert.setContentText("Deseja realmente excluir este professor?");

        Optional<ButtonType> resultado = alert.showAndWait(); //diz para esperar o usuario clicar em algo

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

            try {

                int id = Integer.parseInt(intId.getText());

                ProfessorDAO profDAO = new ProfessorDAO();
                profDAO.deletar(id);

                //System.out.println("Professor excluído");

                carregarProfessor();

                // UX 3: limpar campos
                btnLimparAction();

                // UI 2: cor !=
                lblMensagem.setStyle("-fx-text-fill: green;");
                lblMensagem.setText("Professor excluído com sucesso!");

            } catch (Exception e) {

                // UI 2: cor !=
                lblMensagem.setStyle("-fx-text-fill: red;");
                lblMensagem.setText("Erro ao excluir professor.");
            }
        }
    }

    @FXML
    private void btnLimparAction() {

        intId.clear();
        txtNome.clear();
        txtEmail.clear();
        txtDisciplina.clear();
        numSalario.clear();

        txtNome.requestFocus();

        // UI 2: limpa a label mensagem
        lblMensagem.setText("");
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

            // UI 2: cor !=
            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Professor atualizado com sucesso!");

        } catch (Exception e) {

            // UI 2: cor !=
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Erro ao atualizar professor.");
        }
    }
}