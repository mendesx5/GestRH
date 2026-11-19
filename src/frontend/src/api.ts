const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

export function getToken(): string | null {
  return localStorage.getItem('token');
}

export function setAuth(token: string, user: { nome: string; email: string; perfil: string }) {
  localStorage.setItem('token', token);
  localStorage.setItem('user', JSON.stringify(user));
}

export function clearAuth() {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
}

export function getUser(): { nome: string; email: string; perfil: string } | null {
  const raw = localStorage.getItem('user');
  return raw ? JSON.parse(raw) : null;
}

async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
  const token = getToken();
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(options.headers as Record<string, string>),
  };
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const res = await fetch(`${API_URL}${path}`, {
    ...options,
    headers,
  });

  if (!res.ok) {
    let message = 'Erro na requisição';
    try {
      const body = await res.json();
      message = body.message || body.error || message;
    } catch {
      /* ignore */
    }
    throw new Error(message);
  }

  if (res.status === 204) return undefined as T;
  return res.json();
}

export type AuthResponse = {
  token: string;
  nome: string;
  email: string;
  perfil: string;
};

export type Funcionario = {
  id: number;
  nome: string;
  cargo: string;
  dataAdmissao: string;
  ativo: boolean;
};

export type DashboardIndicadores = {
  totalFuncionarios: number;
  faltasMes: number;
  feriasPendentes: number;
};

export type Ausencia = {
  id: number;
  funcionarioId: number;
  funcionarioNome: string;
  tipo: string;
  dataInicio: string;
  dataFim: string;
  status: string;
};

export const api = {
  login: (email: string, senha: string) =>
    request<AuthResponse>('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, senha }),
    }),

  listFuncionarios: (busca?: string) =>
    request<Funcionario[]>(`/funcionarios${busca ? `?busca=${encodeURIComponent(busca)}` : ''}`),

  createFuncionario: (data: { nome: string; cargo: string; dataAdmissao: string }) =>
    request<Funcionario>('/funcionarios', {
      method: 'POST',
      body: JSON.stringify(data),
    }),

  getDashboard: () => request<DashboardIndicadores>('/dashboard/indicadores'),

  listAusencias: () => request<Ausencia[]>('/ausencias'),
};
