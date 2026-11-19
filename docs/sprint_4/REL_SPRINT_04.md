# Relatório de Fechamento – Sprint 4
## GestRH – Sistema de Gestão de Recursos Humanos

**Sprint:** 4 – Arquitetura e Modelagem de Dados  
**Período:** Semana da Sprint 4  
**Equipe:** Jose Gabriel, Otávio Henrique, Breno e Max  

---

## 1. Objetivo da Sprint

Definir e documentar a arquitetura do sistema, o modelo de dados (DER) e o dicionário de dados, além de atualizar documentos de requisitos e backlog conforme feedback das sprints anteriores.

## 2. Entregas realizadas

| Entrega | Status | Observação |
|---------|--------|------------|
| Documento de Arquitetura ([DOC-ARQ]) | ✅ | Stack, diagramas em camadas, DER e dicionário de dados |
| Diagrama de arquitetura em camadas | ✅ | Frontend (Vercel) ↔ Backend (Render) ↔ PostgreSQL |
| Diagrama Entidade-Relacionamento | ✅ | Tabelas: usuarios, funcionarios, registros_ausencia, registros_horas |
| Dicionário de dados | ✅ | Campos, tipos e restrições de todas as tabelas |
| Atualização de requisitos / backlog | 🔄 | Parcial – alinhado com o que já está implementado no backend |
| Relatório de Sprint | ✅ | Este documento |
| **Bônus:** Frontend MVP | ✅ | Login, Dashboard, Funcionários e Ausências (React + Vite) |
| **Bônus:** CORS no backend | ✅ | Configurado para localhost e Vercel |

## 3. Decisões arquiteturais

1. **Frontend:** React + Vite + TypeScript – produtividade alta e deploy simples na Vercel.
2. **Backend:** Spring Boot (já existente) mantido, com adição de CORS.
3. **Banco:** PostgreSQL – robusto e disponível no free tier do Render.
4. **Deploy separado:** Vercel (front) + Render (back + DB) – evita complexidade de monolito e aproveita free tiers.
5. **Sem Docker nesta fase:** decisão consciente para manter o setup simples para apresentação acadêmica.

## 4. O que funcionou bem

- Backend já estava maduro (auth, funcionários, ausências, horas, dashboard).
- Modelo de dados existente no código facilitou a formalização do DER e do dicionário.
- MVP de frontend foi possível em poucas horas por manter o escopo mínimo.

## 5. Impedimentos e dificuldades

- Tempo reduzido para fechar documentação + protótipo funcional.
- CORS não estava configurado (corrigido nesta sprint).
- Ambiente de revisão anterior não permitia compilação local completa do backend.

## 6. Plano para a próxima Sprint (Sprint 5)

Conforme diretrizes: **Configuração do Ambiente e Fundação Técnica**.

- Finalizar estrutura de pastas e padrões de projeto no frontend
- Configurar variáveis de ambiente e README de deploy
- Garantir que backend sobe corretamente no Render com PostgreSQL
- Preparar seeds básicos de dados para demonstração
- Iniciar testes unitários das regras críticas

## 7. Participação da equipe

| Membro | Papel | Contribuição principal nesta Sprint |
|--------|-------|-------------------------------------|
| Jose Gabriel | Backend / Dev | Arquitetura backend, CORS, apoio ao DER |
| Otávio Henrique | Frontend / Dev | MVP do frontend (React + Vite) |
| Breno | Product Owner | Validação do escopo da arquitetura e backlog |
| Max | Scrum Master | Organização da apresentação e relatório |

---

**Documento gerado para a Sprint Review da Sprint 4.**
