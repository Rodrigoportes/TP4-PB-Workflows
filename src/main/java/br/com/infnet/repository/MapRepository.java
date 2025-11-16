package br.com.infnet.repository;
import br.com.infnet.model.Identifiable;
import java.util.*;

public class MapRepository<T extends Identifiable> implements IRepository<T> {

    private final Map<Integer, T> storage = new HashMap<>();

    @Override
    public void cadastrar(T entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public T buscar(int id) {
        return storage.get(id);
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void atualizar(T entity) {
        if (storage.containsKey(entity.getId())) {
            storage.put(entity.getId(), entity);
        }
    }

    @Override
    public void remover(int id) {
        storage.remove(id);
    }

    public void clear() {
        storage.clear();
    }
}