package br.com.infnet;

import br.com.infnet.controller.FuncionarioController;
import br.com.infnet.model.Funcionario;
import br.com.infnet.repository.IRepository;
import br.com.infnet.repository.MapRepository; // Implementação de persistência (TP1)
import br.com.infnet.service.FuncionarioService;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        startApp(7000);
    }

    public static Javalin startApp(int port) {

        // ------------------------------------------------------------------
        // COMPOSIÇÃO DE DEPENDÊNCIAS (DIP)

        // 1. Repositório (Base de dados em memória do TP1)
        // A camada de Main decide qual implementação usar.
        IRepository<Funcionario> repository = new MapRepository<>();

        // DADOS INICIAIS: Adiciona alguns dados para o sistema Web funcionar
        repository.cadastrar(new Funcionario(1, "João Silva", "Engenheiro de Software", 5000.0));
        repository.cadastrar(new Funcionario(2, "Clara Oliveira", "Analista de RH", 4000.0));

        // 2. Serviço (Injetando a Abstração IRepository)
        FuncionarioService service = new FuncionarioService(repository);

        // ------------------------------------------------------------------

        // 3. Inicialização do Javalin
        Javalin app = Javalin.create(config ->{
            // Configurações básicas
        }).start(port);

        // 4. Controller (Injetando o App e o Service)
        new FuncionarioController(app, service);

        // Rota de redirecionamento
        app.get("/", ctx -> ctx.redirect("/funcionarios"));

        System.out.println("Aplicação iniciada em: http://localhost:" + port + "/");
        return app;
    }
}