# GestRH - Sistema de Gestão de Recursos Humanos

Projeto da disciplina de Projeto e Engenharia de Software, desenvolvido com a metodologia ágil **Scrum**, em Sprints semanais.

## Sobre o Projeto

O **GestRH** é um sistema web voltado para a gestão de recursos humanos em **pequenas e médias empresas** que ainda não possuem um sistema estruturado para esse fim.

###  - O problema

Pequenas e médias empresas costumam controlar informações de funcionários (dados cadastrais, cargos, férias e faltas) em planilhas soltas ou até em papel. Esse processo manual é sujeito a erros, dificulta a consulta rápida de informações e não oferece nenhuma visão consolidada da equipe para o gestor tomar decisões.

### - A solução

O GestRH centraliza os dados dos colaboradores em um único sistema, com controle de acesso e relatórios básicos que dão ao gestor uma visão clara do quadro de funcionários. O sistema permite cadastrar funcionários, controlar férias e faltas, e visualizar indicadores básicos de RH em um painel simples, com autenticação e níveis de acesso diferenciados para **administrador** e **funcionário**.

### - Público-alvo

Pequenas e médias empresas que ainda não possuem um sistema de RH estruturado.

## Principais Benefícios

- Centralização das informações dos funcionários em um só lugar, eliminando planilhas dispersas.
- Redução de erros manuais no controle de férias e faltas.
- Consulta rápida e organizada dos dados de cada colaborador.
- Painel com indicadores básicos que apoiam a tomada de decisão do gestor.
- Controle de acesso, garantindo que cada usuário veja apenas o que é permitido ao seu perfil.

## Escopo do Projeto

### Dentro do escopo

- Cadastro de usuários com autenticação e controle de acesso (perfil Administrador/RH e perfil Funcionário).
- Cadastro e edição de dados dos funcionários.
- Registro e controle de férias e faltas dos funcionários.
- Painel dashboard com indicadores básicos (total de funcionários, faltas do mês, férias pendentes).
- Geração de relatórios simples (lista de funcionários e relatório de férias/faltas).
- Banco de horas dos funcionários.

### Fora do escopo

- Folha de pagamento completa, com cálculo de salários, impostos e benefícios.
- Integração com sistemas de ponto eletrônico ou biometria.
- Aplicativo nativo para iOS/Android.
- Módulo de recrutamento e seleção de candidatos.

## Stacks

| Camada          | Tecnologia                                             |
|-----------------|--------------------------------------------------------|
| Backend         | Java + Spring Boot (API REST), Spring Security + JWT   |
| Frontend        | TypeScript + ReactJS                                   |
| Banco de Dados  | PostgreSQL                                             |
| Metodologia     | Scrum (Sprints semanais)                               |

## Backend

O backend está **implementado e funcional**, seguindo uma arquitetura **feature-based** (organização por funcionalidade, não por camada técnica). Cada módulo de negócio concentra sua própria entidade, repositório, service, controller e DTOs:

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

### Endpoints disponíveis

| Método | Rota                          | Descrição                                  |
|--------|--------------------------------|---------------------------------------------|
| POST   | `/auth/register`               | Cadastro de usuário                         |
| POST   | `/auth/login`                  | Login (retorna token JWT)                   |
| POST   | `/funcionarios`                | Criar funcionário                           |
| GET    | `/funcionarios`                | Listar funcionários (com busca opcional)    |
| GET    | `/funcionarios/{id}`           | Detalhe de um funcionário                   |
| PUT    | `/funcionarios/{id}`           | Editar funcionário                          |
| DELETE | `/funcionarios/{id}`           | Inativar funcionário (somente ADMIN)        |
| POST   | `/ausencias`                   | Registrar falta ou férias                   |
| GET    | `/ausencias`                   | Listar todas as ausências                   |
| GET    | `/ausencias/funcionario/{id}`  | Histórico de ausências por funcionário      |
| POST   | `/banco-horas`                 | Registrar horas extras/compensadas          |
| GET    | `/banco-horas/funcionario/{id}`| Saldo e histórico de banco de horas         |
| GET    | `/dashboard/indicadores`       | Indicadores consolidados (RH)               |
| GET    | `/relatorios/funcionarios`     | Relatório de funcionários                   |

Todas as rotas, exceto `/auth/**`, exigem token JWT no header `Authorization: Bearer <token>`.

### Como executar

1. Crie um banco PostgreSQL chamado `gest_rh`.
2. Configure as credenciais via variáveis de ambiente (`DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`) — veja o `.env` na raiz do módulo backend.
3. Rode `GestRhApplication` (ou `./mvnw spring-boot:run`). O Hibernate cria as tabelas automaticamente na primeira execução.
4. A API sobe em `http://localhost:8080`.

## Metodologia

O desenvolvimento segue o framework **Scrum**, com entregas incrementais organizadas em **Sprints semanais**. Cada Sprint é encerrada com uma Sprint Review, na qual a equipe apresenta o incremento desenvolvido, atualiza a documentação e relata o que deu certo, o que deu errado e os impedimentos da semana.

## 👥 Equipe

| Nome            | Cargo (Scrum)   | Contribuições na Sprint 2                              |
|-----------------|-----------------|----------------------------------------------------------|
| Jose Gabriel    | Backend / Dev   | Requisitos funcionais e não-funcionais do backend; implementação da API (auth, funcionários, ausências, banco de horas, dashboard) em arquitetura feature-based |
| Otávio Henrique | Frontend / Dev  | Requisitos funcionais e não-funcionais do frontend |
| Breno           | Product Owner   | Técnicas de elicitação de requisitos; atualização do Documento de Visão e Escopo |
| Max             | Scrum Master    | Relatório de Fechamento da Sprint 2; organização da apresentação |

## Status do Projeto

**Sprint atual:** Sprint 2 — Levantamento e Especificação de Requisitos ✅ Concluída

Entregas realizadas nesta Sprint:
- [x] Documento de Requisitos com RF e RNF (backend e frontend)
- [x] Documento de Visão e Escopo atualizado
- [x] Papéis Scrum formalizados entre os integrantes
- [x] API backend implementada em arquitetura feature-based e testada localmente: autenticação (JWT), cadastro de funcionários, férias/faltas, banco de horas e dashboard de indicadores
- [ ] Telas de frontend integradas à API

Sprint anterior (Sprint 1): concepção e visão do produto, com definição do nicho, problema, solução e escopo inicial. ✅ Concluída

Próxima Sprint (Sprint 3): planejamento do produto com Épicos e User Stories no Product Backlog, prototipação de baixa fidelidade das principais telas e início da integração do frontend com a API já disponível.

## 📄 Documentação

- `docs/Visao_e_Escopo.pdf` - Documento de Visão e Escopo
- `docs/Relatorio_Sprint_01.pdf` - Relatório de Fechamento da Sprint 1
- `docs/Documento_Requisitos.pdf` - Documento de Requisitos (não implementado)
- `docs/Relatorio_Sprint_02.pdf` - Relatório de Fechamento da Sprint 2 (não implementado)

---

Projeto desenvolvido para a disciplina de Projeto e Engenharia de Software.