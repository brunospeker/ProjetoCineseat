import { useState } from "react";
import Header from "../components/Navbar";
import { Search, MapPin } from "lucide-react";

const cinemas = [
  { nome: "Cine Plaza", estado: "SP", endereco: "Av. Paulista, 1000 - São Paulo" },
  { nome: "CineLuz", estado: "RJ", endereco: "Rua do Rio, 45 - Rio de Janeiro" },
  { nome: "MovieMax", estado: "MG", endereco: "Rua Central, 300 - Belo Horizonte" },
  { nome: "CinePorto", estado: "PE", endereco: "Av. Recife, 120 - Recife" },
  { nome: "CineSul", estado: "RS", endereco: "Rua das Flores, 50 - Porto Alegre" },
];

export default function Cinema() {
  const [busca, setBusca] = useState("");
  const [estado, setEstado] = useState("Todos");
  const [darkMode, setDarkMode] = useState(true);

  const cinemasFiltrados = cinemas.filter((cinema) => {
    const buscaOK = cinema.nome.toLowerCase().includes(busca.toLowerCase());
    const estadoOK = estado === "Todos" || cinema.estado === estado;
    return buscaOK && estadoOK;
  });

  return (
    <div className={`${darkMode ? "bg-black text-white" : "bg-white text-black"} min-h-screen`}>
      <Header darkMode={darkMode} setDarkMode={setDarkMode} />

      <main className="px-8 py-8">
        <h1 className="text-4xl font-bold mb-6">Cinemas</h1>

        <div className="flex flex-col md:flex-row gap-4 mb-8">
          <div className="flex items-center bg-black-900 border border-gray-700 rounded-xl px-4 py-2 flex-1">
            <input
              type="text"
              placeholder="Buscar"
              value={busca}
              onChange={(e) => setBusca(e.target.value)}
              className="bg-transparent outline-none text-white w-full placeholder-gray-400"
            />
            <Search className="text-gray-400 w-5 h-5" />
          </div>

          <div className="relative">
            <select
              value={estado}
              onChange={(e) => setEstado(e.target.value)}
              className="appearance-none bg-white-900 border border-gray-700 text-white px-4 py-2 pr-10 rounded-xl"
            >
              <option>Estados</option>
              <option>SP</option>
              <option>RJ</option>
              <option>MG</option>
              <option>PE</option>
              <option>RS</option>
            </select>
            <MapPin className="absolute right-3 top-3 text-black-400 w-5 h-5 pointer-events-none" />
          </div>
        </div>

        <h2 className="text-2xl font-semibold mb-4">Locais disponíveis</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {cinemasFiltrados.map((cinema, index) => (
            <div
              key={index}
              className="bg-white-900 border border-gray-700 p-4 rounded-xl hover:scale-[1.02] transition-transform"
            >
              <h3 className="text-lg font-semibold mb-2">{cinema.nome}</h3>
              <p className="text-gray-400">{cinema.endereco}</p>
              <span className="inline-block mt-3 bg-red-600 px-2 py-1 rounded text-xs">
                {cinema.estado}
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
