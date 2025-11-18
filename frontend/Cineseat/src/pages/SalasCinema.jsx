import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Header from "../components/Navbar";
import { ArrowLeft, Star, Popcorn } from "lucide-react";

export default function SalasCinema({ darkMode, setDarkMode, user, onLogout }) {
  const params = useParams(); // id do cinema vindo da URL
  const navigate = useNavigate();

  const [cinema, setCinema] = useState(null);
  const [salas, setSalas] = useState([]);
  const [cinemaavaliacao, setCinemaAvaliacao] = useState([]);
  const [salasavaliacao, setSalasAvaliacao] = useState([]);
  const [loading, setLoading] = useState(true);
  

  useEffect(() => {
    async function carregarDados() {
      try {
        setLoading(true);

        const cinemaResponse = await fetch(`http://localhost:8080/api/cinemas/${params.id}`);
        const cinemaData = await cinemaResponse.json();
        console.log(cinemaData);
        setCinema(cinemaData);

        const cinemaAvaliacaoResponse = await fetch(`http://localhost:8080/api/cinema-avaliacoes/cinema/${params.id}`);
        const cinemaAvaliacaoData = await cinemaAvaliacaoResponse.json();
        //setCinemaAvaliacao(cinemaAvaliacaoData);
        setCinemaAvaliacao(cinemaAvaliacaoData[0] || null);

        console.log(cinemaAvaliacaoData);
        
        const salasResponse = await fetch(`http://localhost:8080/api/salas/cinema/${params.id}`);
        const salasData = await salasResponse.json();
        //setSalas(salasData);
        //console.log(salasData);

        /*const salasAvaliacaoResponse = await fetch(`http://localhost:8080/api/salas/${params.id}/avaliacoes/estatisticas`);
        //const salasAvaliacaoResponse = await fetch(`http://localhost:8080/api/salas/49/avaliacoes/estatisticas`);
        const salasAvaliacaoData = await salasAvaliacaoResponse.json();
        setSalasAvaliacao(salasAvaliacaoData);
        console.log(salasAvaliacaoData);*/

        const salasComMedias = await Promise.all(
          salasData.map(async (sala) => {
            const idSala = sala.idSala ?? sala.id_sala ?? sala.id;
  
            const mediaResponse = await fetch(
              `http://localhost:8080/api/salas/${idSala}/avaliacoes/estatisticas`
            );
  
            const estatisticas = mediaResponse.ok ? await mediaResponse.json() : null;
  
            return {
              ...sala,
              mediaGeral: estatisticas?.mediaGeral ?? 0,
              mediaAtendimento: estatisticas?.mediaAtendimento ?? 0,
              mediaLimpeza: estatisticas?.mediaLimpeza ?? 0,
              mediaPreco: estatisticas?.mediaPreco ?? 0,
              mediaConforto: estatisticas?.mediaConforto ?? 0,
              estatisticas: estatisticas ?? null,
            };
          })
        );
        setSalas(salasComMedias);
        console.log(salasComMedias);


      } catch (err) {
        console.error("Erro ao carregar cinema:", err);
      } finally {
        setLoading(false);
      }
    }

    carregarDados();
  }, [params.id]);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen text-white bg-black">
        <p>Carregando cinema...</p>
      </div>
    );
  }

  if (!cinema) {
    return (
      <div className="flex justify-center items-center h-screen text-white bg-black">
        <p>Cinema não encontrado</p>
      </div>
    );
  }

  return (
    <div className={`${darkMode ? "bg-black text-white" : "bg-white text-black"} min-h-screen`}>
      <Header darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={onLogout} />

      <main className="p-8 max-w-6xl mx-auto relative">
        {/* Botão voltar */}
        <button
          onClick={() => window.history.back()}
          className="absolute left-8 top-8 flex items-center gap-2 text-gray-400 hover:text-white transition"
        >
          <ArrowLeft className="w-4 h-4" />
          Voltar
        </button>

        <div className="mt-10 mb-6">
          <div>
            <h1 className="text-4xl font-bold mb-2 flex items-center gap-2">
              {cinema.nomeCinema}
              {cinema.temBomboniere && (
                <Popcorn className="text-yellow-400 w-6 h-6" title="Possui bomboniere" />
              )}
            </h1>
            <p className="text-gray-400">
              {cinema.bairro}, {cinema.numero} — {cinema.cidade}/{cinema.estado}
            </p>

            <div className="flex items-center gap-4 mt-3 flex-wrap">
              <span className="bg-red-600 px-2 py-1 rounded text-xs text-white">{cinema.uf}</span>
              <span className="bg-gray-700 px-2 py-1 rounded text-xs text-white">
                {cinema.totalSalas} salas
              </span>
            </div>
          </div>
        </div>

        <div className="mb-10 grid grid-cols-1 sm:grid-cols-3 lg:grid-cols-5 gap-4">
          <div className={`p-4 rounded-xl border ${darkMode ? "bg-neutral-900 border-gray-700" : "bg-white border-gray-300"}`}>
            <h4 className="font-semibold mb-2">Nota Geral</h4>
            <div className="flex items-center gap-2 text-yellow-400 text-lg">
              <Star className="w-4 h-4" />
              {cinemaavaliacao.notaGeral?.toFixed(1)}
            </div>
          </div>

          <div className={`p-4 rounded-xl border ${darkMode ? "bg-neutral-900 border-gray-700" : "bg-white border-gray-300"}`}>
            <h4 className="font-semibold mb-2">Limpeza</h4>
            <div className="flex items-center gap-2 text-yellow-400 text-lg">
              <Star className="w-4 h-4" />
              {cinemaavaliacao.notaLimpeza?.toFixed(1)}
            </div>
          </div>

          <div className={`p-4 rounded-xl border ${darkMode ? "bg-neutral-900 border-gray-700" : "bg-white border-gray-300"}`}>
            <h4 className="font-semibold mb-2">Atendimento</h4>
            <div className="flex items-center gap-2 text-yellow-400 text-lg">
              <Star className="w-4 h-4" />
              {cinemaavaliacao.notaAtendimento?.toFixed(1)}
            </div>
          </div>

          <div className={`p-4 rounded-xl border ${darkMode ? "bg-neutral-900 border-gray-700" : "bg-white border-gray-300"}`}>
            <h4 className="font-semibold mb-2">Preço</h4>
            <div className="flex items-center gap-2 text-yellow-400 text-lg">
              <Star className="w-4 h-4" />
              {cinemaavaliacao.notaPreco?.toFixed(1)}
            </div>
          </div>

          <div className={`p-4 rounded-xl border ${darkMode ? "bg-neutral-900 border-gray-700" : "bg-white border-gray-300"}`}>
            <h4 className="font-semibold mb-2">Alimentação</h4>
            <div className="flex items-center gap-2 text-yellow-400 text-lg">
              <Star className="w-4 h-4" />
              {cinemaavaliacao.notaAlimentacao?.toFixed(1)}
            </div>
          </div>
        </div>

        <h2 className="text-2xl font-semibold mb-4">Salas disponíveis</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {salas.map((sala) => (
            <div
              key={sala.id_sala}
              onClick={() => navigate(`/cinema/${cinema.idCinema}/${sala.id_sala}`)}
              className={`relative overflow-visible border p-4 rounded-xl hover:scale-[1.02] transition-transform ${
                darkMode ? "bg-white-900 border-gray-700" : "bg-gray-50 border-gray-300"
              }`}
            >
              <div className="flex justify-between items-center mb-2">
                <h3 className="text-lg font-semibold">{sala.nome}</h3>
                <span className="flex items-center gap-1 bg-yellow-400 text-black px-2 py-1 rounded text-xs font-semibold">
                  <Star className="w-3 h-3" />
                  {sala.media_avaliacoes / sala.total_avaliacoes}
                </span>
              </div>

              <p className="text-gray-400 mb-1">{sala.descricao}</p>
              <p className="text-gray-400 mb-1">Capacidade: {sala.capacidade_total} lugares</p>
              <div className="flex items-center gap-4 mt-3 mb-3">
                <span className="bg-blue-600 px-2 py-1 rounded text-xs text-white">{sala.tipo_som}</span>
                <span className="bg-blue-600 px-2 py-1 rounded text-xs text-white">{sala.tipo_tela}</span>
              </div>
              
              <p
                className={`text-sm font-semibold ${
                  sala.acessivel ? "text-green-400" : "text-red-400"
                }`}
              >
                {sala.acessivel ? "♿ Acessível" : "♿ Não acessível"}
              </p>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
