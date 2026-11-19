import { useState, FormEvent } from 'react';
import { api, setAuth } from '../api';

type Props = {
  onSuccess: (user: { nome: string; email: string; perfil: string }) => void;
};

export default function LoginPage({ onSuccess }: Props) {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e: FormEvent) {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const res = await api.login(email, senha);
      setAuth(res.token, { nome: res.nome, email: res.email, perfil: res.perfil });
      onSuccess({ nome: res.nome, email: res.email, perfil: res.perfil });
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Falha no login');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <h1>GestRH</h1>
        <p className="login-subtitle">Sistema de Gestão de Recursos Humanos</p>

        <form onSubmit={handleSubmit}>
          <label>
            E-mail
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="seu@email.com"
              required
              autoFocus
            />
          </label>

          <label>
            Senha
            <input
              type="password"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              placeholder="••••••••"
              required
            />
          </label>

          {error && <div className="error-msg">{error}</div>}

          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Entrando...' : 'Entrar'}
          </button>
        </form>

        <p className="login-hint">
          MVP acadêmico – use um usuário cadastrado na API
        </p>
      </div>
    </div>
  );
}
