package com.template.controller;

import com.template.model.ProfessorDAO;
import com.template.model.ProfessorDTO;
import static com.template.util.DialogUtil.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

            ObservableList<ProfessorDTO> dadosTabelaProf = FXCollections.observableArrayList(listaProfessor);
            tblProfessor.setItems(dadosTabelaProf);

            // UX 1: contador
            atualizarContador();

        } catch (Exception e) {
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Erro ao carregar professores");

            mostrarErro("Erro", "Falha de Conexão", "Não foi possível carregar a lista de professores");
        }
    }

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

            carregarProfessor();
            btnLimparAction();

            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Professor cadastrado com sucesso!");
            informar("Sucesso", "Cadastro Realizado", "Professor cadastrado com sucesso");

        } catch (NumberFormatException e) {
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Salário inválido");
            mostrarErro("Erro de Validação", "Valor Inválido", "Informe um valor numérico válido para o salário");

        } catch (Exception e) {
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Erro ao cadastrar professor");
            mostrarErro("Erro no Cadastro", "Falha ao Inserir", "Ocorreu um erro ao tentar salvar o professor no banco de dados");
        }
    }

    @FXML
    private void btnEditarAction() {
        try {
            int id = Integer.parseInt(intId.getText());
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String disciplina = txtDisciplina.getText();
            double salario = Double.parseDouble(numSalario.getText());

            ProfessorDTO profDTO = new ProfessorDTO(id, nome, email, salario, disciplina);

            ProfessorDAO profDAO = new ProfessorDAO();
            profDAO.atualizar(profDTO);

            carregarProfessor();
            btnLimparAction();

            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Professor atualizado com sucesso!");
            informar("Sucesso", "Atualização Realizada", "Dados do professor atualizados com sucesso");

        } catch (NumberFormatException e) {
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Campos ID ou Salário inválidos.");
            mostrarErro("Erro de Validação", "Valores Inválidos", "verifique se o ID e o Salário contêm números válidos");

        } catch (Exception e) {
            lblMensagem.setStyle("-fx-text-fill: red;");
            lblMensagem.setText("Erro ao atualizar professor.");
            mostrarErro("Erro na Edição", "Falha ao Atualizar", "Ocorreu um erro ao atualizar os dados do professor");
        }
    }

    @FXML
    private void btnExcluirAction() {
        if (intId.getText().trim().isEmpty()) {
            mostrarErro("Aviso", "Nenhum Registro Selecionado", "Selecione um professor na tabela ou digite um ID para excluir");
            return;
        }

        boolean confirmou = confirmacao(
                "Confirmação",
                "Excluir Professor",
                "Deseja realmente excluir este professor?"
        );

        if (confirmou) {
            try {
                int id = Integer.parseInt(intId.getText());

                ProfessorDAO profDAO = new ProfessorDAO();
                profDAO.deletar(id);

                carregarProfessor();
                btnLimparAction();

                lblMensagem.setStyle("-fx-text-fill: green;");
                lblMensagem.setText("Professor excluído com sucesso");
                informar("Sucesso", "Exclusão Concluída", "Professor excluído com sucesso");

            } catch (NumberFormatException e) {
                lblMensagem.setStyle("-fx-text-fill: red;");
                lblMensagem.setText("ID inválido para exclusão");
                mostrarErro("Erro de Validação", "ID Inválido", "O ID informado não é um número inteiro válido");

            } catch (Exception e) {
                lblMensagem.setStyle("-fx-text-fill: red;");
                lblMensagem.setText("Erro ao excluir professor");
                mostrarErro("Erro na Exclusão", "Falha ao Excluir", "Não foi possível remover o professor do banco de dados");
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
        lblMensagem.setText("");
    }
}