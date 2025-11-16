import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import Header from "../components/Navbar";
import { ArrowLeft, Star, PlayCircle, Armchair } from "lucide-react";

export default function FilmeDetalhes({ darkMode, setDarkMode, user, onLogout }) {
  const { id } = useParams(); // ID vindo da lista de filmes
  const [filme, setFilme] = useState(null);
  const [filmeavaliacao, setFilmeAvaliacao] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function buscarFilme() {
      try {
        setLoading(true);

        const responseFilme = await fetch(`http://localhost:8080/api/filmes/tmdb/${id}`);
        const dataFilme = await responseFilme.json();
        setFilme(dataFilme);
        console.log(dataFilme);

        //const responseFilmeAvaliacao = await fetch(`http://localhost:8080/api/filme-avaliacoes/filme/${id}`); ativar depois do felipe trocar a api para pegar o idfilme e não o id
        const responseFilmeAvaliacao = await fetch(`http://localhost:8080/api/filme-avaliacoes/filme/${dataFilme.id}`); //mudar também na linha 105
        const dataFilmeAvaliacao = await responseFilmeAvaliacao.json();
        setFilmeAvaliacao(dataFilmeAvaliacao);
        console.log(dataFilmeAvaliacao);

      } catch (err) {
        console.error("Erro ao buscar filme:", err);
      } finally {
        setLoading(false);
      }
    }

    buscarFilme();
  }, [id]);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen text-white bg-black">
        <p>Carregando detalhes...</p>
      </div>
    );
  }

  if (!filme) {
    return (
      <div className="flex justify-center items-center h-screen text-white bg-black">
        <p>Filme não encontrado.</p>
      </div>
    );
  }

  return (
    <div className={`${darkMode ? "bg-black text-white" : "bg-white text-black"} min-h-screen`}>
      <Header darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={onLogout} />

      <main className="px-6 md:px-10 py-8 max-w-6xl mx-auto relative">

        <button
          onClick={() => window.history.back()}
          className="absolute left-6 top-6 flex items-center gap-2 text-gray-400 hover:text-white transition"
        >
          <ArrowLeft className="w-4 h-4" />
          Voltar
        </button>

        <div className="flex flex-col md:flex-row gap-10 mt-10">
          <img
            src={filme.poster_url || filme.imagem}
            alt={filme.titulo}
            className="w-full max-w-sm rounded-xl shadow-lg self-center md:self-start"
          />

          <div className="flex-1">
            <h1 className="text-4xl font-bold mb-2 flex items-center gap-2">
              {filme.titulo}
            </h1>

            <span className="bg-red-600 px-3 py-1 rounded text-xs font-semibold inline-block mb-3">
              {filme.classificacao || "L"}
            </span>

            <p className="text-gray-300 text-lg mb-4">{filme.sinopse || "Sinopse não disponível."}</p>

            <div className="grid grid-cols-2 gap-3 text-sm text-gray-300">
              <p><strong>Gênero:</strong> {filme.genero || "Não informado"}</p>
              <p><strong>Duração:</strong> {filme.duracao || "--"} min</p>
              <p><strong>Diretor:</strong> {filme.diretor || "Não informado"}</p>
              <p><strong>Data de Lançamento:</strong> {filme.data_lancamento || "N/A"}</p>
            </div>

            <div className="grid grid-cols-2 gap-3 text-sm text-gray-300">
                <div className="mt-6 p-4 rounded-xl bg-neutral-900 border border-gray-700 max-w-xs">
                <h4 className="font-semibold">Nota Média TMDB</h4>
                <div className="flex items-center gap-2 text-yellow-400 text-lg mt-1">
                    <Star className="w-4 h-4" />
                    {filme.nota?.toFixed?.(1) ?? "0.0"}
                </div>
                </div>
                <div className="mt-6 p-4 rounded-xl bg-neutral-900 border border-gray-700 max-w-xs">
                <h4 className="font-semibold">Nota Média do Cineseat</h4>
                <div className="flex items-center gap-2 text-yellow-400 text-lg mt-1">
                    <Star className="w-4 h-4" />
                    {filmeavaliacao?.length > 0 ? filmeavaliacao[0]?.nota?.toFixed(1) : "0.0"}
                </div>
                </div>
            </div>

            <div className="flex gap-3 mt-6">
            {filme.trailer_url && (
                <a
                href={filme.trailer_url}
                target="_blank"
                className="flex items-center gap-2 bg-red-600 px-4 py-2 rounded-lg w-fit hover:bg-red-700 transition"
                >
                <PlayCircle className="w-5 h-5" />
                Assistir trailer
                </a>
            )}

            <Link
                to={`/avaliar-filme/${filme.id}`}    // troque conforme sua rota
                className="flex items-center gap-2 bg-blue-600 px-4 py-2 rounded-lg w-fit hover:bg-blue-700 transition"
            >
                <Armchair className="w-5 h-5" />
                Avaliar Filme
            </Link>
            </div>

          </div>
        </div>
      </main>
    </div>
  );
}
