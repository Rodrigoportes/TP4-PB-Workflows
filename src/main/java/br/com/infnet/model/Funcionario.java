package br.com.infnet.model;

// Implementa Identifiable para ser usado no Repositório
public class Funcionario implements Identifiable {

    // Todos os campos são final para garantir Imutabilidade (Clean Code)
    private final int id;
    private final String nome;
    private final String cargo;   // Campo do TP2/TP3
    private final double salario; // Campo do TP1

    // Construtor Unificado
    public Funcionario(int id, String nome, String cargo, double salario) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    // Getters
    @Override
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    // Setters removidos

    @Override
    public String toString() {
        return "Funcionario{id=" + id + ", nome='" + nome + "', cargo='" + cargo + "', salario=" + salario + "}";
    }
}
