# GestRH – Frontend (MVP)

Interface web mínima do sistema de Gestão de Recursos Humanos.

## Stack

- React 18 + TypeScript
- Vite
- CSS puro (sem framework)

## Telas

1. **Login** – autenticação via JWT
2. **Dashboard** – indicadores (total de funcionários, faltas do mês, férias pendentes)
3. **Funcionários** – listagem com busca + cadastro rápido
4. **Ausências** – listagem de férias e faltas

## Como rodar localmente

```bash
cd src/frontend
cp .env.example .env
# Edite VITE_API_URL se necessário
npm install
npm run dev
```

Acesse: http://localhost:5173

## Deploy (Vercel)

1. Conecte o repositório na Vercel
2. Root directory: `src/frontend`
3. Build command: `npm run build`
4. Output: `dist`
5. Variável de ambiente: `VITE_API_URL=https://sua-api.onrender.com`

## Observações

- MVP acadêmico da Sprint 4
- Não usa React Router (navegação por estado – mais simples)
- Token JWT armazenado no `localStorage`
