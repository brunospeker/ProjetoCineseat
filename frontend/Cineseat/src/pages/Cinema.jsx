import { useState,useEffect } from "react";
import Header from "../components/Navbar";
import { Search, MapPin, Popcorn, Star } from "lucide-react";
import { useNavigate } from "react-router-dom";

const cinemas = [
  { nome: "Cine Plaza", estado: "SP", endereco: "Av. Paulista, 1000 - São Paulo" },
  { nome: "CineLuz", estado: "RJ", endereco: "Rua do Rio, 45 - Rio de Janeiro" },
  { nome: "MovieMax", estado: "MG", endereco: "Rua Central, 300 - Belo Horizonte" },
  { nome: "CinePorto", estado: "PE", endereco: "Av. Recife, 120 - Recife" },
  { nome: "CineSul", estado: "RS", endereco: "Rua das Flores, 50 - Porto Alegre" },
];

export default function Cinema({ darkMode, setDarkMode, user, onLogout }) {
  const [busca, setBusca] = useState("");
  const [estado, setEstado] = useState("Todos");
  const [cinemas, setCinemas] = useState([]);

  const navigate = useNavigate();
  
  useEffect(() => {
    fetch("http://localhost:8080/api/cinemas")
      .then((response) => response.json())
      .then(async (data) => {
        const cinemasComNotas = await Promise.all(
          data.map(async (cinema) => {
            const response = await fetch(`http://localhost:8080/api/cinema-avaliacoes/media/cinema/${cinema.idCinema}`);
            const nota = response.ok ? await response.json() : null;
            return { ...cinema, notaMedia: nota || 0 };
          })
        );
        setCinemas(cinemasComNotas);
      })
      .catch((err) => console.error("Erro ao buscar cinemas:", err));
  }, []);

  const cinemasFiltrados = cinemas.filter((cinema) => {
    const buscaOK = cinema.nomeCinema.toLowerCase().includes(busca.toLowerCase());
    const estadoOK = estado === "Todos" || cinema.uf === estado;
    return buscaOK && estadoOK;
  });

  return (
    <div className={`${darkMode ? "bg-black text-white" : "bg-white text-black"} min-h-screen`}>
      <Header darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={onLogout} />

      <main className="px-8 py-8">
        <h1 className="text-4xl font-bold mb-6">Cinemas</h1>

        <div className="flex flex-col md:flex-row gap-4 mb-8">
          <div className={`flex items-center border rounded-xl px-4 py-2 flex-1 ${
            darkMode ? "bg-black-900 border-gray-700" : "bg-white border-gray-300"
          }`}>
            <input
              type="text"
              placeholder="Buscar"
              value={busca}
              onChange={(e) => setBusca(e.target.value)}
              className={`bg-transparent outline-none w-full placeholder-gray-400 ${
                darkMode ? "text-white" : "text-black"
              }`}
            />
            <Search className={`w-5 h-5 ${darkMode ? "text-gray-400" : "text-gray-600"}`} />
          </div>

          <div className="relative">
            <select
              value={estado}
              onChange={(e) => setEstado(e.target.value)}
              className={`appearance-none border px-4 py-2 pr-10 rounded-xl ${
                darkMode ? "bg-black-900 border-gray-700 text-white" : "bg-white border-gray-300 text-black"
              }`}
            >
              <option>Estados</option>
              <option>SP</option>
              <option>RJ</option>
              <option>MG</option>
              <option>PE</option>
              <option>RS</option>
            </select>
            <MapPin className={`absolute right-3 top-3 w-5 h-5 pointer-events-none ${
              darkMode ? "text-gray-400" : "text-gray-600"
            }`} />
          </div>
        </div>

        <h2 className="text-2xl font-semibold mb-4">Locais disponíveis</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {cinemasFiltrados.map((cinema, index) => (
            <div
              key={cinema.idCinema}
              onClick={() => navigate(`/cinema/${cinema.idCinema}`)}
              className={`relative overflow-visible border p-4 rounded-xl hover:scale-[1.02] transition-transform ${
                darkMode ? "bg-white-900 border-gray-700" : "bg-gray-50 border-gray-300"
              }`}
            >
              <div className="flex items-center gap-2">
                <h3 className="text-lg font-semibold mb-2">{cinema.nomeCinema}</h3>
                {cinema.temBomboniere && (
                    <Popcorn className="text-yellow-400 w-5 h-5" title="Possui bomboniere" />
                  )}
              </div>
              <p className={darkMode ? "text-gray-400" : "text-gray-600"}>{cinema.bairro}, {cinema.numero} — {cinema.cidade}/{cinema.estado}</p>
              
              <div className="absolute top-3 right-3 flex items-center gap-1 px-2 py-1">
                  <Star className="text-yellow-400 w-4 h-4" />
                  <span className="text-sm font-semibold">
                    {cinema.notaMedia?.toFixed(1)}
                  </span>
              </div>
              <span className="inline-block mt-3 bg-red-600 px-2 py-1 rounded text-xs text-white">
                {cinema.uf}
              </span>
              <div className="absolute bottom-3 right-3 items-center justify-between mt-3">
                <span className="inline-block bg-red-600 px-2 py-1 rounded text-xs text-white">
                  {cinema.totalSalas} salas
                </span>
              </div>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}