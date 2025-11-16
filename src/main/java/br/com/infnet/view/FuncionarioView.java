package br.com.infnet.view;
import br.com.infnet.model.Funcionario;

import java.util.List;
import java.util.Map;

public class FuncionarioView {

    public static String renderList(List<Funcionario> funcionarios) {
        StringBuilder html = new StringBuilder("""
                <!DOCTYPE html>
                <html lang="pt">
                <head>
                    <meta charset="UTF-8">
                    <title>Lista de Funcionários</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                </head>
                <body class="container mt-5">
                    <h1>Lista de Funcionários</h1>
                    <a href="/funcionarios/new" class="btn btn-primary mb-3">Adicionar Novo Funcionário</a>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Cargo</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                """);
        for (Funcionario funcionario : funcionarios) {
            html.append(String.format("""
                    <tr>
                        <td>%d</td>
                        <td>%s</td>
                        <td>%s</td>
                        <td>
                            <a href="/funcionarios/edit/%d" class="btn btn-sm btn-warning">Editar</a>
                            <form action="/funcionarios/delete/%d" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-sm btn-danger">Deletar</button>
                            </form>
                        </td>
                    </tr>
                    """, funcionario.getId(), funcionario.getNome(), funcionario.getCargo(), funcionario.getId(), funcionario.getId()));
        }
        html.append("""
                        </tbody>
                    </table>
                </body>
                </html>
                """);
        return html.toString();
    }

    public static String renderForm(Map<String, Object> model) {
        Object id = model.get("id");
        String action = id != null ? "/funcionarios/edit/" + id : "/funcionarios";
        String title = id != null ? "Editar Funcionário" : "Novo Funcionário";
        String nome = (String) model.getOrDefault("nome", "");
        String cargo = (String) model.getOrDefault("cargo", "");

        return String.format("""
                <!DOCTYPE html>
                <html lang="pt">
                <head>
                    <meta charset="UTF-8">
                    <title>%s</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                </head>
                <body class="container mt-5">
                    <h1>%s</h1>
                    <form action="%s" method="post">
                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome</label>
                            <input type="text" class="form-control" id="nome" name="nome" value="%s" required>
                        </div>
                        <div class="mb-3">
                            <label for="cargo" class="form-label">Cargo</label>
                            <input type="text" class="form-control" id="cargo" name="cargo" value="%s" required>
                        </div>
                        <button type="submit" class="btn btn-success">Salvar</button>
                        <a href="/funcionarios" class="btn btn-secondary">Cancelar</a>
                    </form>
                </body>
                </html>
                """, title, title, action, nome, cargo);
    }
}
