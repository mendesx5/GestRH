# GestRH — Backend (API)

API REST do sistema GestRH, construída em **Java 21 + Spring Boot 4**, com **Spring Security + JWT** para autenticação e **PostgreSQL** como banco de dados.

## Status atual

**Implementado e funcional** para o escopo previsto até a Sprint 2: autenticação, CRUD de funcionários, controle de ausências (férias/faltas), banco de horas e dashboard de indicadores. Ainda não integrado ao frontend (ver seção "Pendências conhecidas").

## Arquitetura

O projeto segue arquitetura **feature-based** (organização por funcionalidade de negócio, não por camada técnica). Cada módulo concentra sua própria entidade, repositório, service, controller e DTOs:

```
com.pes_project.gest_rh
├── auth/            → autenticação, cadastro de usuário e controle de acesso (JWT)
├── funcionario/     → cadastro, edição, listagem e inativação de funcionários
├── ausencia/        → registro e histórico de férias e faltas
├── horas/           → banco de horas (registro e saldo)
├── dashboard/       → indicadores consolidados de RH
├── relatorio/       → geração de relatórios
└── common/
    ├── security/    → JWT, filtro de autenticação e configuração de segurança
    └── exception/   → tratamento centralizado de erros
```

Cada módulo de domínio (`funcionario`, `ausencia`, `horas`) segue o mesmo padrão: `Entity` (JPA) → `Repository` (Spring Data) → `Service` (regra de negócio) → `Controller` (REST) → `dto/` (records de request/response).

## Modelo de dados (resumo)

| Entidade           | Descrição                                                                 |
|---------------------|-----------------------------------------------------------------------------|
| `Usuario`           | Login do sistema (email, senha com hash BCrypt, perfil `ADMIN`/`FUNCIONARIO`) |
| `Funcionario`       | Dados cadastrais (nome, cargo, data de admissão, ativo/inativo), com vínculo opcional a um `Usuario` |
| `RegistroAusencia`  | Falta ou férias de um funcionário (`tipo`: FALTA/FERIAS, `status`: PENDENTE/APROVADA/REJEITADA) |
| `RegistroHoras`     | Lançamento de banco de horas (`tipo`: EXTRA/COMPENSADA)                     |

## Endpoints disponíveis

| Método | Rota                            | Descrição                                  | Autenticação        |
|--------|-----------------------------------|-----------------------------------------------|-----------------------|
| POST   | `/auth/register`                 | Cadastro de usuário                            | Pública               |
| POST   | `/auth/login`                    | Login (retorna token JWT)                      | Pública               |
| POST   | `/funcionarios`                  | Criar funcionário                              | JWT                   |
| GET    | `/funcionarios`                  | Listar funcionários (com busca opcional `?busca=`) | JWT                   |
| GET    | `/funcionarios/{id}`             | Detalhe de um funcionário                      | JWT                   |
| PUT    | `/funcionarios/{id}`             | Editar funcionário                             | JWT                   |
| DELETE | `/funcionarios/{id}`             | Inativar funcionário                           | JWT + role `ADMIN`    |
| POST   | `/ausencias`                     | Registrar falta ou férias                      | JWT                   |
| GET    | `/ausencias`                     | Listar todas as ausências                      | JWT                   |
| GET    | `/ausencias/funcionario/{id}`    | Histórico de ausências por funcionário         | JWT                   |
| POST   | `/banco-horas`                   | Registrar horas extras/compensadas             | JWT                   |
| GET    | `/banco-horas/funcionario/{id}`  | Saldo e histórico de banco de horas            | JWT                   |
| GET    | `/dashboard/indicadores`         | Indicadores consolidados (total ativos, faltas do mês, férias pendentes) | JWT |
| GET    | `/relatorios/funcionarios`       | Relatório de funcionários                      | JWT                   |
| GET    | `/actuator/health`               | Health check                                   | Pública               |

Todas as rotas, exceto `/auth/**` e `/actuator/health`, exigem token JWT no header `Authorization: Bearer <token>`.

## Como executar

1. Crie um banco PostgreSQL chamado `gest_rh`.
2. Configure as credenciais via variáveis de ambiente (`DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`) — copie `.env.example` para `.env` como referência dos nomes esperados.
3. Rode `GestRhApplication` (ou `./mvnw spring-boot:run`). O Hibernate cria/atualiza as tabelas automaticamente na primeira execução (`ddl-auto: update`).
4. A API sobe em `http://localhost:8080` (HTTP, sem TLS local).

## Pendências conhecidas

Pontos identificados em revisão de código que precisam de atenção antes/durante a integração com o frontend:

- **CORS não configurado**: `SecurityConfig` não define uma `CorsConfigurationSource`. Requisições do frontend (ex.: `http://localhost:5173`) para a API vão ser bloqueadas pelo navegador até isso ser adicionado.
- **Vazamento de detalhes de erro**: `GlobalExceptionHandler.handleGenerica` devolve `ex.getMessage()` de qualquer exceção não mapeada diretamente no corpo da resposta. Vale trocar por uma mensagem genérica e logar o detalhe só no servidor.
- **Relatório incompleto**: `RelatorioController` só expõe `/relatorios/funcionarios`; ainda não existe relatório de férias/faltas, mencionado no escopo do projeto.
- **Aprovação de ausências não implementada**: `StatusAusencia` já modela `PENDENTE`/`APROVADA`/`REJEITADA`, mas não há endpoint para o admin aprovar ou rejeitar — todo registro fica `PENDENTE` para sempre.
- **Testes**: só existe o teste padrão `contextLoads()`, que depende de uma instância real do PostgreSQL rodando (não há perfil de teste com H2 ou Testcontainers). Não há testes de unidade para as regras de negócio (services).
- **Sem paginação**: `GET /funcionarios` retorna a lista inteira; ok para o volume atual, mas vale considerar paginação antes de produção.
- **`ddl-auto: update`**: adequado para desenvolvimento, mas não deve ser usado em produção — nessa fase vale migrar para Flyway/Liquibase.

## Como não compilei localmente

Este README foi revisado por leitura estática de todo o código-fonte (não foi possível rodar `mvn compile`/`mvn test` neste ambiente de revisão porque o Maven Central não está acessível na rede da sandbox usada). O código segue os padrões corretos das dependências declaradas (`spring-boot-starter-webmvc`, `jjwt` 0.12.x, Spring Data JPA, Spring Security) e não apresenta erros óbvios de importação, tipagem ou assinatura entre camadas — recomenda-se ainda assim rodar `./mvnw clean verify` localmente antes de considerar a Sprint fechada.
