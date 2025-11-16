package br.com.infnet.controller;

import br.com.infnet.model.Funcionario;
import br.com.infnet.service.FuncionarioService;
import br.com.infnet.view.FuncionarioView;
import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FuncionarioController {

    private final FuncionarioService service;

    // VARIÁVEL TEMPORÁRIA: Simula a geração de ID. Será removida na integração completa do Repositório.
    private int lastId = 1000;

    // NOVO: Construtor recebe o Service via Injeção de Dependência (DIP)
    public FuncionarioController(Javalin app, FuncionarioService service) {
        this.service = service;

        // Listar Funcionários (Mantido, usando a dependência injetada)
        app.get("/funcionarios", ctx ->
                ctx.html(this.service.listar().isEmpty() ?
                        FuncionarioView.renderList(this.service.listar()) :
                        FuncionarioView.renderList(this.service.listar())));

        // Formulário para Novo Funcionário (Mantido)
        app.get("/funcionarios/new", ctx ->
                ctx.html(FuncionarioView.renderForm(new HashMap<>())));

        // ROTA CREATE: app.post("/funcionarios")
        // (SRP: Validação movida para o Service)
        app.post("/funcionarios", ctx -> {
            String nome = ctx.formParam("nome");
            String cargo = ctx.formParam("cargo");
            double salario = 0.0; // Valor padrão para o campo unificado (não é solicitado na Web)

            try {
                // 1. Cria a nova instância unificada e imutável
                Funcionario novoFuncionario = new Funcionario(lastId++, nome, cargo, salario);

                // 2. Delega ao Service, que fará a validação interna
                this.service.addFuncionario(novoFuncionario);

                ctx.redirect("/funcionarios");
            } catch (IllegalArgumentException e) {
                // 3. Trata a exceção de validação (erro de negócio -> 400 Bad Request)
                ctx.status(400).result("Erro de validação: " + e.getMessage());
            }
        });

        // Formulário para Editar Funcionário (GET /edit/{id})
        app.get("/funcionarios/edit/{id}", ctx -> {
            int id = ctx.pathParamAsClass( "id", Integer.class).get();
            Optional<Funcionario> funcionarioOptional = this.service.findById(id);

            if (funcionarioOptional.isPresent()) {
                Funcionario funcionario = funcionarioOptional.get();

                Map<String, Object> model = new HashMap<>();
                model.put("id", funcionario.getId());
                model.put("nome", funcionario.getNome());
                model.put("cargo", funcionario.getCargo());
                // Adicionando o campo 'salario' para o modelo unificado, mesmo que não seja exibido.
                model.put("salario", funcionario.getSalario());

                ctx.html(FuncionarioView.renderForm(model));
            } else {
                ctx.status(404).result( "Funcionário não encontrado");
            }
        });

        // ROTA UPDATE: app.post("/funcionarios/edit/{id}")
        // (SRP: Validação movida para o Service)
        app.post("/funcionarios/edit/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            String nome = ctx.formParam("nome");
            String cargo = ctx.formParam("cargo");

            // Requer o valor atual do salário para criar a nova instância imutável
            Optional<Funcionario> fAntigo = this.service.findById(id);
            double salario = fAntigo.map(Funcionario::getSalario).orElse(0.0);

            try {
                // 1. Cria a nova instância unificada (imutável)
                Funcionario funcionarioAtualizado = new Funcionario(id, nome, cargo, salario);

                // 2. Delega ao Service, que fará a validação e atualização
                this.service.updateFuncionario(funcionarioAtualizado);

                ctx.redirect("/funcionarios");
            } catch (IllegalArgumentException e) {
                // 3. Trata a exceção de validação (erro de negócio -> 400 Bad Request)
                ctx.status(400).result("Erro de validação: " + e.getMessage());
            }
        });

        // Deletar Funcionário (Mantido)
        app.post("/funcionarios/delete/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            this.service.deleteFuncionario(id);
            ctx.redirect("/funcionarios");
        });
    }
}
