package br.com.infnet.service;

import br.com.infnet.model.Funcionario; // Usa o modelo unificado
import br.com.infnet.repository.IRepository; // Usa a interface
import java.util.List;
import java.util.Optional;

// O Service agora foca APENAS na lógica de negócio e validação.
public class FuncionarioService {

    private static final int MAX_LENGTH = 50;

    // Dependência injetada
    private final IRepository<Funcionario> repository;

    // Construtor com Injeção de Dependência
    public FuncionarioService(IRepository<Funcionario> repository) {
        this.repository = repository;
    }

    // --- Lógica de Validação (Movida do Controller) ---
    // Esta é uma regra de negócio e pertence ao Service.
    private void validarFuncionario(String nome, String cargo) {
        // Regra 1: Validação Fail Early para nulos/vazios
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo é obrigatório.");
        }

        // Regra 2: Validação contra Fuzzing/Limite de Caracteres
        if (nome.length() > MAX_LENGTH || cargo.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("O limite de " + MAX_LENGTH + " caracteres foi excedido para Nome ou Cargo.");
        }
    }

    // --- CRUD Operations (Integrando Validação) ---

    public void addFuncionario(Funcionario funcionario) {
        // O Service valida antes de persistir
        validarFuncionario(funcionario.getNome(), funcionario.getCargo());
        repository.cadastrar(funcionario);
    }

    public Optional<Funcionario> findById(int id) {
        return Optional.ofNullable(repository.buscar(id));
    }

    public void updateFuncionario(Funcionario funcionario) {
        // O Service valida antes de persistir
        validarFuncionario(funcionario.getNome(), funcionario.getCargo());
        repository.atualizar(funcionario);
    }

    public void deleteFuncionario(int id) {
        repository.remover(id);
    }

    public List<Funcionario> listar(){
        return repository.listar();
    }
}
