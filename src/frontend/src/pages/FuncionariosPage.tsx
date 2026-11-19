import { useEffect, useState, FormEvent } from 'react';
import { api, Funcionario } from '../api';

export default function FuncionariosPage() {
  const [lista, setLista] = useState<Funcionario[]>([]);
  const [busca, setBusca] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState({ nome: '', cargo: '', dataAdmissao: '' });
  const [saving, setSaving] = useState(false);

  async function load(buscaTermo?: string) {
    setLoading(true);
    setError('');
    try {
      const data = await api.listFuncionarios(buscaTermo);
      setLista(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro ao listar');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    load();
  }, []);

  async function handleBusca(e: FormEvent) {
    e.preventDefault();
    await load(busca);
  }

  async function handleCreate(e: FormEvent) {
    e.preventDefault();
    setSaving(true);
    setError('');
    try {
      await api.createFuncionario(form);
      setForm({ nome: '', cargo: '', dataAdmissao: '' });
      setShowForm(false);
      await load(busca);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erro ao cadastrar');
    } finally {
      setSaving(false);
    }
  }

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <h2>Funcionários</h2>
          <p className="page-desc">Cadastro e listagem de colaboradores</p>
        </div>
        <button className="btn-primary" onClick={() => setShowForm(!showForm)}>
          {showForm ? 'Cancelar' : '+ Novo funcionário'}
        </button>
      </div>

      {showForm && (
        <form className="form-card" onSubmit={handleCreate}>
          <h3>Novo funcionário</h3>
          <div className="form-row">
            <label>
              Nome
              <input
                value={form.nome}
                onChange={(e) => setForm({ ...form, nome: e.target.value })}
                required
              />
            </label>
            <label>
              Cargo
              <input
                value={form.cargo}
                onChange={(e) => setForm({ ...form, cargo: e.target.value })}
                required
              />
            </label>
            <label>
              Data de admissão
              <input
                type="date"
                value={form.dataAdmissao}
                onChange={(e) => setForm({ ...form, dataAdmissao: e.target.value })}
                required
              />
            </label>
          </div>
          <button type="submit" className="btn-primary" disabled={saving}>
            {saving ? 'Salvando...' : 'Salvar'}
          </button>
        </form>
      )}

      <form className="search-bar" onSubmit={handleBusca}>
        <input
          placeholder="Buscar por nome ou cargo..."
          value={busca}
          onChange={(e) => setBusca(e.target.value)}
        />
        <button type="submit" className="btn-secondary">
          Buscar
        </button>
      </form>

      {error && <div className="error-msg">{error}</div>}

      {loading ? (
        <div className="loading">Carregando...</div>
      ) : (
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Cargo</th>
                <th>Admissão</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {lista.length === 0 ? (
                <tr>
                  <td colSpan={5} className="empty">
                    Nenhum funcionário encontrado
                  </td>
                </tr>
              ) : (
                lista.map((f) => (
                  <tr key={f.id}>
                    <td>{f.id}</td>
                    <td>{f.nome}</td>
                    <td>{f.cargo}</td>
                    <td>{f.dataAdmissao}</td>
                    <td>
                      <span className={f.ativo ? 'badge ok' : 'badge off'}>
                        {f.ativo ? 'Ativo' : 'Inativo'}
                      </span>
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
