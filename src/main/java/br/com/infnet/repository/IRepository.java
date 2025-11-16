package br.com.infnet.repository;

import java.util.List;

public interface IRepository<T> {
    void cadastrar(T entity);
    T buscar(int id);
    List<T> listar();
    void atualizar(T entity);
    void remover(int id);
}