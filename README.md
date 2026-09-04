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

| Camada          | Tecnologia                    |
|-----------------|-------------------------------|
| Backend         | Java + Spring Boot (API REST) |
| Frontend        | TypeScript + ReactJS          |
| Banco de Dados  | PostgreSQL                    |
| Metodologia     | Scrum (Sprints semanais)      |

## Metodologia

O desenvolvimento segue o framework **Scrum**, com entregas incrementais organizadas em **Sprints semanais**. Cada Sprint é encerrada com uma Sprint Review, na qual a equipe apresenta o incremento desenvolvido, atualiza a documentação e relata o que deu certo, o que deu errado e os impedimentos da semana.

## 👥 Equipe

| Nome            | Contribuições na Sprint 1  |
|-----------------|----------------------------|
| Jose Gabriel    |                            |
| Otávio Henrique |                            |
| Breno           |                            |
| Max             |                            |

> **Papéis Scrum:** os papéis de Product Owner, Scrum Master e Dev Team ainda estão sendo formalizados entre os integrantes e serão definidos oficialmente na Sprint 2, conforme apontado na retrospectiva da Sprint 1.

## Status do Projeto

**Sprint atual:** Sprint 1 — Concepção e Visão do Produto ✅ Concluída

Entregas realizadas nesta Sprint:
- [x] Documento de Visão e Escopo
- [x] Repositório Git criado e configurado
- [x] Alinhamento inicial da equipe

Próxima Sprint (Sprint 2): levantamento e especificação de requisitos funcionais e não-funcionais, além da definição formal dos papéis Scrum de cada integrante.

## 📄 Documentação

- `docs/Visao_e_Escopo.pdf` — Documento de Visão e Escopo
- `docs/Relatorio_Sprint_01.pdf` — Relatório de Fechamento da Sprint 1

---

Projeto desenvolvido para a disciplina de Projeto e Engenharia de Software.