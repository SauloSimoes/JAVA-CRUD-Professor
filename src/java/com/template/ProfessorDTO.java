package com.template;

public class ProfessorDTO {
    private int id;
    private String nome;
    private String email;
    private double salario;
    private String disciplina;

    public ProfessorDTO(int id, String nome, String email, double salario, String disciplina) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.salario = salario;
        this.disciplina = disciplina;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }



}

