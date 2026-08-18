package com.template.service;

import com.template.model.ProfessorDAO;
import com.template.model.ProfessorDTO;

import java.sql.SQLException;
import java.util.List;


public class ProfessorService {

    private final ProfessorDAO professorDAO;

    public ProfessorService() {
        this.professorDAO = new ProfessorDAO();
    }

  
    public List<ProfessorDTO> listar() throws SQLException {
        return professorDAO.listar();
    }

    
    public void cadastrar(ProfessorDTO professor) throws SQLException {
        professorDAO.inserir(professor);
    }

   
    public void atualizar(ProfessorDTO professor) throws SQLException {
        professorDAO.atualizar(professor);
    }

    public void excluir(int id) throws SQLException {
        professorDAO.deletar(id);
    }
}
