import { useEffect, useState } from 'react';
import { api, Ausencia } from '../api';

export default function AusenciasPage() {
  const [lista, setLista] = useState<Ausencia[]>([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api
      .listAusencias()
      .then(setLista)
      .catch((err) => setError(err instanceof Error ? err.message : 'Erro ao listar'))
      .finally(() => setLoading(false));
  }, []);

  function statusClass(status: string) {
    if (status === 'APROVADA') return 'badge ok';
    if (status === 'REJEITADA') return 'badge off';
    return 'badge pending';
  }

  return (
    <div className="page">
      <h2>Ausências</h2>
      <p className="page-desc">Férias e faltas registradas</p>

      {error && <div className="error-msg">{error}</div>}

      {loading ? (
        <div className="loading">Carregando...</div>
      ) : (
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Funcionário</th>
                <th>Tipo</th>
                <th>Início</th>
                <th>Fim</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {lista.length === 0 ? (
                <tr>
                  <td colSpan={6} className="empty">
                    Nenhuma ausência registrada
                  </td>
                </tr>
              ) : (
                lista.map((a) => (
                  <tr key={a.id}>
                    <td>{a.id}</td>
                    <td>{a.funcionarioNome}</td>
                    <td>{a.tipo}</td>
                    <td>{a.dataInicio}</td>
                    <td>{a.dataFim}</td>
                    <td>
                      <span className={statusClass(a.status)}>{a.status}</span>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
