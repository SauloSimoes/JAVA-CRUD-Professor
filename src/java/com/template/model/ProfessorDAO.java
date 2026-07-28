package com.template.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfessorDAO {

    private static final Logger logger = Logger.getLogger(ProfessorDAO.class.getName());

    private Connection conn;

    public ProfessorDAO() {
        Conexao conexao = new Conexao();
        conn = conexao.conectaBD();
    }

    public void inserir(ProfessorDTO prof) throws SQLException {

        String sql = "INSERT INTO professores (nome, email, salario, disciplina) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) { //fecha automaticamente

            pstmt.setString(1, prof.getNome());
            pstmt.setString(2, prof.getEmail());
            pstmt.setDouble(3, prof.getSalario());
            pstmt.setString(4, prof.getDisciplina());

            pstmt.execute();



        } catch (SQLException e) {

            logger.log(Level.SEVERE, "Erro ao inserir professor", e);

            throw e;
        }
    }

    public List<ProfessorDTO> listar() throws SQLException {

        List<ProfessorDTO> listaProfessores = new ArrayList<>();

        String sql = "SELECT id, nome, email, salario, disciplina FROM professores";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                ProfessorDTO professor = new ProfessorDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getDouble("salario"),
                        resultSet.getString("disciplina")
                );

                listaProfessores.add(professor);
            }

            resultSet.close();


        } catch (SQLException e) {

            logger.log(Level.SEVERE, "Erro ao listar professores", e);

            throw e;
        }

        return listaProfessores;
    }

    public void atualizar(ProfessorDTO prof) throws SQLException {

        String sql = "UPDATE professores SET nome = ?, email = ?, salario = ?, disciplina = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, prof.getNome());
            pstmt.setString(2, prof.getEmail());
            pstmt.setDouble(3, prof.getSalario());
            pstmt.setString(4, prof.getDisciplina());
            pstmt.setInt(5, prof.getId());

            pstmt.execute();


        } catch (SQLException e) {

            logger.log(Level.SEVERE, "Erro ao atualizar professor", e);

            throw e;
        }
    }

    public void deletar(int id) throws SQLException {

        String sql = "DELETE FROM professores WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.execute();


        } catch (SQLException e) {

            logger.log(Level.SEVERE, "Erro ao deletar professor", e);

            throw e;
        }
    }
}