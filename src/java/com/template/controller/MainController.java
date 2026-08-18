package com.template.controller;

import com.template.model.ProfessorDTO;
import com.template.service.ProfessorService;
import com.template.util.FeedbackUtil;
import com.template.util.ProfessorFormUtil;
import com.template.validator.ProfessorValidator;

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

    private final ProfessorService professorService = new ProfessorService();

    @FXML
    public void initialize() {
        configurarColunas();
        carregarProfessor();
    }

    private void configurarColunas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplina"));
    }

    @FXML
    private void btnCadastrarAction() {
        String erroValidacao = ProfessorValidator.validarCampos(
                txtNome.getText(), txtEmail.getText(),
                txtDisciplina.getText(), numSalario.getText());

        if (erroValidacao != null) {
            FeedbackUtil.erro(lblMensagem, erroValidacao);
            mostrarErro("Erro de Validação", "Campo inválido", erroValidacao);
            return;
        }

        try {
            ProfessorDTO prof = ProfessorFormUtil.construir(
                    intId, txtNome, txtEmail, numSalario, txtDisciplina);

            professorService.cadastrar(prof);

            carregarProfessor();
            btnLimparAction();
            FeedbackUtil.sucesso(lblMensagem, "Professor cadastrado com sucesso!");
            informar("Sucesso", "Cadastro Realizado", "Professor cadastrado com sucesso.");

        } catch (Exception e) {
            FeedbackUtil.erro(lblMensagem, "Erro ao cadastrar professor.");
            mostrarErro("Erro no Cadastro", "Falha ao Inserir",
                    "Ocorreu um erro ao tentar salvar o professor no banco de dados.");
        }
    }

    @FXML
    private void btnEditarAction() {
        String erroId = ProfessorValidator.validarId(intId.getText());
        if (erroId != null) {
            FeedbackUtil.erro(lblMensagem, erroId);
            mostrarErro("Aviso de Seleção", "Nenhum Registro Selecionado", erroId);
            return;
        }

        String erroCampos = ProfessorValidator.validarCampos(
                txtNome.getText(), txtEmail.getText(),
                txtDisciplina.getText(), numSalario.getText());

        if (erroCampos != null) {
            FeedbackUtil.erro(lblMensagem, erroCampos);
            mostrarErro("Erro de Validação", "Campo inválido", erroCampos);
            return;
        }

        try {
            ProfessorDTO prof = ProfessorFormUtil.construir(
                    intId, txtNome, txtEmail, numSalario, txtDisciplina);

            professorService.atualizar(prof);

            carregarProfessor();
            btnLimparAction();
            FeedbackUtil.sucesso(lblMensagem, "Professor atualizado com sucesso!");
            informar("Sucesso", "Atualização Realizada", "Dados do professor atualizados com sucesso.");

        } catch (Exception e) {
            FeedbackUtil.erro(lblMensagem, "Erro ao atualizar professor.");
            mostrarErro("Erro na Edição", "Falha ao Atualizar",
                    "Ocorreu um erro ao atualizar os dados do professor.");
        }
    }

    @FXML
    private void btnExcluirAction() {
        String erroId = ProfessorValidator.validarId(intId.getText());
        if (erroId != null) {
            FeedbackUtil.erro(lblMensagem, erroId);
            mostrarErro("Aviso de Seleção", "Nenhum Registro Selecionado", erroId);
            return;
        }

        boolean confirmou = confirmacao(
                "Confirmação", "Excluir Professor",
                "Deseja realmente excluir este professor?");

        if (!confirmou)
            return;

        try {
            int id = Integer.parseInt(intId.getText().trim());
            professorService.excluir(id);

            carregarProfessor();
            btnLimparAction();
            FeedbackUtil.sucesso(lblMensagem, "Professor excluído com sucesso.");
            informar("Sucesso", "Exclusão Concluída", "Professor excluído com sucesso.");

        } catch (Exception e) {
            FeedbackUtil.erro(lblMensagem, "Erro ao excluir professor.");
            mostrarErro("Erro na Exclusão", "Falha ao Excluir",
                    "Não foi possível remover o professor do banco de dados.");
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
        FeedbackUtil.limpar(lblMensagem);
    }

    @FXML
    private void carregarCampos() {
        ProfessorDTO prof = tblProfessor.getSelectionModel().getSelectedItem();
        if (prof == null)
            return;

        intId.setText(String.valueOf(prof.getId()));
        txtNome.setText(prof.getNome());
        txtEmail.setText(prof.getEmail());
        txtDisciplina.setText(prof.getDisciplina());
        numSalario.setText(String.valueOf(prof.getSalario()));
    }

    private void carregarProfessor() {
        try {
            List<ProfessorDTO> lista = professorService.listar();
            ObservableList<ProfessorDTO> dados = FXCollections.observableArrayList(lista);
            tblProfessor.setItems(dados);
            atualizarContador();

        } catch (Exception e) {
            FeedbackUtil.erro(lblMensagem, "Erro ao carregar professores.");
            mostrarErro("Erro", "Falha de Conexão",
                    "Não foi possível carregar a lista de professores.");
        }
    }

    private void atualizarContador() {
        lblTotal.setText("Total de registros: " + tblProfessor.getItems().size());
    }
}