# Documento de Arquitetura de Software
## GestRH – Sistema de Gestão de Recursos Humanos

**Sprint:** 4 – Arquitetura e Modelagem de Dados  
**Equipe:** Jose Gabriel, Otávio Henrique, Breno e Max  
**Data:** 19/11/2026  
**Versão:** 1.0

---

## 1. Introdução

Este documento descreve a arquitetura do sistema **GestRH**, as tecnologias escolhidas, a organização em camadas, o modelo de dados (DER) e o dicionário de dados. O objetivo é garantir que a equipe e os stakeholders compreendam as decisões técnicas e o fluxo de comunicação entre os componentes.

### 1.1 Objetivos da Arquitetura
- Separação clara entre Frontend, Backend e Banco de Dados
- Segurança baseada em autenticação JWT e controle de perfil
- Facilidade de deploy em ambientes gratuitos (Vercel + Render)
- Código organizado por features (feature-based) no backend
- Evolução incremental (Scrum)

---

## 2. Stack Tecnológica

| Camada              | Tecnologia                          | Justificativa |
|---------------------|-------------------------------------|---------------|
| **Frontend**        | React 18 + Vite + TypeScript        | Rápido de configurar, ecossistema maduro, ótimo suporte a SPAs e deploy fácil na Vercel. TypeScript reduz erros em tempo de desenvolvimento. |
| **Estilização**     | CSS Modules / CSS puro              | Simplicidade para o MVP. Evita dependências pesadas nesta fase inicial. |
| **Backend**         | Spring Boot 3.x (Java 21)           | Já implementado. Forte em segurança, validação e integração com JPA. Adequado para regras de negócio de RH. |
| **Segurança**       | Spring Security + JWT (jjwt 0.12.x) | Autenticação stateless, adequada para API REST consumida por SPA. |
| **Persistência**    | Spring Data JPA + Hibernate         | Mapeamento objeto-relacional maduro e produtividade alta. |
| **Banco de Dados**  | PostgreSQL                          | Banco relacional robusto, gratuito no Render, suporte completo a tipos e constraints. |
| **Deploy Frontend** | Vercel                              | Zero-config para projetos Vite/React, HTTPS e domínio gratuito. |
| **Deploy Backend**  | Render (Web Service + PostgreSQL)   | Suporte nativo a Java/Spring Boot e PostgreSQL free tier. |
| **Controle de Versão** | Git + GitHub                     | Padrão da disciplina e integração com deploys. |

### 2.1 Por que essa stack?
- O backend já está implementado e funcional em Spring Boot.
- React + Vite é a combinação mais rápida para entregar um MVP de frontend em poucas horas.
- PostgreSQL + Render + Vercel formam um caminho de deploy 100% gratuito e simples, alinhado ao escopo acadêmico.
- A arquitetura em camadas facilita a manutenção e a evolução nas próximas sprints.

---

## 3. Diagrama de Arquitetura em Camadas

```
┌─────────────────────────────────────────────────────────────┐
│                        USUÁRIO                              │
│              (Navegador – Desktop / Mobile)                 │
└──────────────────────────┬──────────────────────────────────┘
                           │ HTTPS
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                    FRONTEND (Vercel)                        │
│              React + Vite + TypeScript                      │
│  ┌─────────────┐  ┌──────────────┐  ┌──────────────────┐   │
│  │   Login     │  │  Dashboard   │  │  Funcionários    │   │
│  │   Page      │  │  Page        │  │  List / Create   │   │
│  └─────────────┘  └──────────────┘  └──────────────────┘   │
│                         │                                   │
│                   Axios / fetch                             │
│                   (Bearer JWT)                              │
└──────────────────────────┬──────────────────────────────────┘
                           │ REST API (JSON)
                           │ Authorization: Bearer <token>
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                    BACKEND (Render)                         │
│                   Spring Boot (Java 21)                     │
│  ┌───────────────────────────────────────────────────────┐ │
│  │              Camada de Apresentação (Controllers)     │ │
│  │  /auth  /funcionarios  /ausencias  /banco-horas       │ │
│  │  /dashboard                                           │ │
│  └───────────────────────────┬───────────────────────────┘ │
│                              │                             │
│  ┌───────────────────────────▼───────────────────────────┐ │
│  │              Camada de Negócio (Services)             │ │
│  │  AuthService, FuncionarioService, AusenciaService...  │ │
│  └───────────────────────────┬───────────────────────────┘ │
│                              │                             │
│  ┌───────────────────────────▼───────────────────────────┐ │
│  │         Camada de Persistência (Repositories)         │ │
│  │              Spring Data JPA                          │ │
│  └───────────────────────────┬───────────────────────────┘ │
│                              │                             │
│  ┌───────────────────────────▼───────────────────────────┐ │
│  │         Segurança transversal                         │ │
│  │  JwtAuthFilter + SecurityConfig + BCrypt              │ │
│  └───────────────────────────────────────────────────────┘ │
└──────────────────────────┬──────────────────────────────────┘
                           │ JDBC / Hibernate
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                 BANCO DE DADOS (Render)                     │
│                        PostgreSQL                           │
│   usuarios | funcionarios | registros_ausencia |            │
│   registros_horas                                           │
└─────────────────────────────────────────────────────────────┘
```

### 3.1 Fluxo de comunicação principal
1. Usuário acessa o frontend na Vercel.
2. Realiza login → Frontend envia `POST /auth/login` → Backend valida e retorna JWT.
3. Frontend armazena o token (localStorage) e envia em todas as requisições protegidas (`Authorization: Bearer <token>`).
4. Backend valida o JWT no filtro, aplica regras de autorização por perfil (ADMIN / FUNCIONARIO) e processa a requisição.
5. Dados são persistidos/consultados no PostgreSQL via JPA.

---

## 4. Diagrama Entidade-Relacionamento (DER)

```
┌──────────────────────┐         ┌──────────────────────────┐
│      USUARIOS        │         │      FUNCIONARIOS        │
├──────────────────────┤         ├──────────────────────────┤
│ PK id                │◄───┐    │ PK id                    │
│    nome              │    │    │    nome                  │
│    email (UK)        │    └────│ FK usuario_id (UK, NULL) │
│    senha             │         │    cargo                 │
│    perfil (ENUM)     │         │    data_admissao         │
│    criado_em         │         neutral ativo                 │
└──────────────────────┘         └────────────┬─────────────┘
                                              │
                     ┌────────────────────────┼────────────────────────┐
                     │                        │                        │
                     ▼                        ▼                        ▼
        ┌────────────────────────┐  ┌─────────────────────┐
        │  REGISTROS_AUSENCIA    │  │  REGISTROS_HORAS    │
        ├────────────────────────┤  ├─────────────────────┤
        │ PK id                  │  │ PK id               │
        │ FK funcionario_id      │  │ FK funcionario_id   │
        │    tipo (ENUM)         │  │    horas            │
        │    data_inicio         │  │    tipo (ENUM)      │
        │    data_fim            │  │    data             │
        │    status (ENUM)       │  └─────────────────────┘
        └────────────────────────┘
```

### 4.1 Cardinalidades
| Relacionamento                          | Cardinalidade | Observação |
|-----------------------------------------|---------------|------------|
| Usuario ↔ Funcionario                   | 1:1 (opcional)| Nem todo funcionário precisa de login |
| Funcionario → RegistroAusencia          | 1:N           | Um funcionário pode ter várias ausências |
| Funcionario → RegistroHoras             | 1:N           | Um funcionário pode ter vários registros de horas |

### 4.2 Enums utilizados
- **Perfil:** `ADMIN`, `FUNCIONARIO`
- **TipoAusencia:** `FALTA`, `FERIAS`
- **StatusAusencia:** `PENDENTE`, `APROVADA`, `REJEITADA`
- **TipoHoras:** conforme implementação no backend

---

## 5. Dicionário de Dados

### 5.1 Tabela: `usuarios`

| Campo       | Tipo             | Nulo | Chave     | Descrição / Restrições |
|-------------|------------------|------|-----------|------------------------|
| id          | BIGSERIAL        | Não  | PK        | Identificador único |
| nome        | VARCHAR          | Não  |           | Nome completo do usuário |
| email       | VARCHAR          | Não  | UK        | E-mail único usado no login |
| senha       | VARCHAR          | Não  |           | Hash BCrypt da senha |
| perfil      | VARCHAR (ENUM)   | Não  |           | ADMIN ou FUNCIONARIO |
| criado_em   | TIMESTAMP        | Sim  |           | Data/hora de criação (preenchido automaticamente) |

### 5.2 Tabela: `funcionarios`

| Campo          | Tipo             | Nulo | Chave     | Descrição / Restrições |
|----------------|------------------|------|-----------|------------------------|
| id             | BIGSERIAL        | Não  | PK        | Identificador único |
| nome           | VARCHAR          | Não  |           | Nome do colaborador |
| cargo          | VARCHAR          | Não  |           | Cargo / função |
| data_admissao  | DATE             | Não  |           | Data de admissão |
| ativo          | BOOLEAN          | Não  |           | Soft delete (default: true) |
| usuario_id     | BIGINT           | Sim  | FK, UK    | Referência opcional a `usuarios.id` |

### 5.3 Tabela: `registros_ausencia`

| Campo           | Tipo             | Nulo | Chave | Descrição / Restrições |
|-----------------|------------------|------|-------|------------------------|
| id              | BIGSERIAL        | Não  | PK    | Identificador único |
| funcionario_id  | BIGINT           | Não  | FK    | Referência a `funcionarios.id` |
| tipo            | VARCHAR (ENUM)   | Não  |       | FALTA ou FERIAS |
| data_inicio     | DATE             | Não  |       | Início do período |
| data_fim        | DATE             | Não  |       | Fim do período (≥ data_inicio) |
| status          | VARCHAR (ENUM)   | Não  |       | PENDENTE, APROVADA ou REJEITADA (default: PENDENTE) |

### 5.4 Tabela: `registros_horas`

| Campo           | Tipo             | Nulo | Chave | Descrição / Restrições |
|-----------------|------------------|------|-------|------------------------|
| id              | BIGSERIAL        | Não  | PK    | Identificador único |
| funcionario_id  | BIGINT           | Não  | FK    | Referência a `funcionarios.id` |
| horas           | NUMERIC          | Não  |       | Quantidade de horas |
| tipo            | VARCHAR (ENUM)   | Não  |       | Tipo do registro |
| data            | DATE             | Não  |       | Data do registro |

---

## 6. Endpoints da API (resumo)

| Método | Endpoint                          | Autenticação | Descrição |
|--------|-----------------------------------|--------------|-----------|
| POST   | `/auth/register`                  | Pública      | Cadastro de usuário |
| POST   | `/auth/login`                     | Pública      | Login → retorna JWT |
| GET    | `/funcionarios`                   | JWT          | Lista funcionários (filtro `?busca=`) |
| POST   | `/funcionarios`                   | JWT          | Cadastra funcionário |
| GET    | `/funcionarios/{id}`              | JWT          | Detalhe de funcionário |
| PUT    | `/funcionarios/{id}`              | JWT          | Atualiza funcionário |
| DELETE | `/funcionarios/{id}`              | JWT + ADMIN  | Inativa funcionário |
| GET    | `/ausencias`                      | JWT          | Lista todas as ausências |
| POST   | `/ausencias`                      | JWT          | Registra ausência |
| GET    | `/ausencias/funcionario/{id}`     | JWT          | Histórico por funcionário |
| POST   | `/banco-horas`                    | JWT          | Registra horas |
| GET    | `/banco-horas/funcionario/{id}`   | JWT          | Saldo de horas |
| GET    | `/dashboard/indicadores`          | JWT          | Indicadores do painel |

---

## 7. Decisões Arquiteturais Importantes

| Decisão | Justificativa |
|---------|---------------|
| Arquitetura feature-based no backend | Facilita localização de código e evolução por domínio (funcionario, ausencia, horas...) |
| JWT stateless | Ideal para SPA + API. Não exige sessão no servidor. |
| Soft delete em funcionários (`ativo`) | Preserva histórico de ausências e horas. |
| Vínculo opcional Usuario ↔ Funcionario | Nem todo colaborador precisa de acesso ao sistema. |
| Deploy separado (Vercel + Render) | Aproveita free tiers e isola frontend de backend. |
| Sem Docker nesta fase | Mantém o setup simples para apresentação acadêmica inicial. |

---

## 8. Próximos passos (Sprint 5+)

- Configurar CORS no backend
- Criar estrutura de pastas do frontend e padrões de projeto
- Implementar telas principais consumindo a API
- Adicionar testes unitários e de integração
- Evoluir relatórios e aprovação de ausências

---

**Documento elaborado para a Sprint 4 – Arquitetura e Modelagem de Dados**  
GestRH – Projeto de Engenharia de Software
