import { useState, useEffect } from 'react';
import { getToken, getUser, clearAuth } from './api';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import FuncionariosPage from './pages/FuncionariosPage';
import AusenciasPage from './pages/AusenciasPage';
import './App.css';

type Page = 'login' | 'dashboard' | 'funcionarios' | 'ausencias';

export default function App() {
  const [page, setPage] = useState<Page>('login');
  const [user, setUser] = useState(getUser());

  useEffect(() => {
    if (getToken() && user) {
      setPage('dashboard');
    }
  }, []);

  function handleLoginSuccess(u: { nome: string; email: string; perfil: string }) {
    setUser(u);
    setPage('dashboard');
  }

  function handleLogout() {
    clearAuth();
    setUser(null);
    setPage('login');
  }

  if (page === 'login' || !user) {
    return <LoginPage onSuccess={handleLoginSuccess} />;
  }

  return (
    <div className="app">
      <header className="header">
        <div className="header-brand">
          <span className="logo">GestRH</span>
          <span className="subtitle">Gestão de Recursos Humanos</span>
        </div>
        <nav className="nav">
          <button
            className={page === 'dashboard' ? 'nav-btn active' : 'nav-btn'}
            onClick={() => setPage('dashboard')}
          >
            Dashboard
          </button>
          <button
            className={page === 'funcionarios' ? 'nav-btn active' : 'nav-btn'}
            onClick={() => setPage('funcionarios')}
          >
            Funcionários
          </button>
          <button
            className={page === 'ausencias' ? 'nav-btn active' : 'nav-btn'}
            onClick={() => setPage('ausencias')}
          >
            Ausências
          </button>
        </nav>
        <div className="header-user">
          <span className="user-name">{user.nome}</span>
          <span className="user-perfil">{user.perfil}</span>
          <button className="btn-logout" onClick={handleLogout}>
            Sair
          </button>
        </div>
      </header>

      <main className="main">
        {page === 'dashboard' && <DashboardPage />}
        {page === 'funcionarios' && <FuncionariosPage />}
        {page === 'ausencias' && <AusenciasPage />}
      </main>
    </div>
  );
}
