import { useEffect, useState } from 'react';
import { api, DashboardIndicadores } from '../api';

export default function DashboardPage() {
  const [data, setData] = useState<DashboardIndicadores | null>(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api
      .getDashboard()
      .then(setData)
      .catch((err) => setError(err instanceof Error ? err.message : 'Erro ao carregar'))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="loading">Carregando indicadores...</div>;
  if (error) return <div className="error-msg">{error}</div>;
  if (!data) return null;

  return (
    <div className="page">
      <h2>Dashboard</h2>
      <p className="page-desc">Indicadores consolidados de RH</p>

      <div className="cards">
        <div className="card">
          <div className="card-value">{data.totalFuncionarios}</div>
          <div className="card-label">Funcionários ativos</div>
        </div>
        <div className="card">
          <div className="card-value">{data.faltasMes}</div>
          <div className="card-label">Faltas no mês</div>
        </div>
        <div className="card">
          <div className="card-value">{data.feriasPendentes}</div>
          <div className="card-label">Férias pendentes</div>
        </div>
      </div>
    </div>
  );
}
