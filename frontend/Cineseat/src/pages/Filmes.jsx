import { useState } from "react";
import Header from "../components/Navbar";
import { Search, ChevronDown } from "lucide-react";

const filmesEmCartaz = [
  {
    titulo: "Barbie",
    imagem: "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/yRRuLt7sMBEQkHsd1S3KaaofZn7.jpg",
    classificacao: "L",
  },
];

export default function Filmes({ darkMode, setDarkMode, user, onLogout }) {
  const [busca, setBusca] = useState("");
  const [genero, setGenero] = useState("Todos os gêneros");

  const filmesFiltrados = filmesEmCartaz.filter((filme) =>
    filme.titulo.toLowerCase().includes(busca.toLowerCase())
  );

  return (
    <div className={`${darkMode ? "bg-black text-white" : "bg-white text-black"} min-h-screen`}>
      <Header darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={onLogout} />

      <main className="px-8 py-8">
        <h1 className="text-4xl font-bold mb-6">Filmes</h1>

        <div className="flex flex-col md:flex-row gap-4 mb-8">
          <div className={`flex items-center border rounded-xl px-4 py-2 flex-1 ${
            darkMode ? "bg-gray-900 border-gray-700" : "bg-white border-gray-300"
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
              value={genero}
              onChange={(e) => setGenero(e.target.value)}
              className={`appearance-none border px-4 py-2 pr-10 rounded-xl ${
                darkMode ? "bg-gray-900 border-gray-700 text-white" : "bg-white border-gray-300 text-black"
              }`}
            >
              <option>Todos os gêneros</option>
              <option>Ação</option>
              <option>Drama</option>
              <option>Comédia</option>
              <option>Infantil</option>
              <option>Terror</option>
            </select>
            <ChevronDown className={`absolute right-3 top-3 w-5 h-5 pointer-events-none ${
              darkMode ? "text-gray-400" : "text-gray-600"
            }`} />
          </div>
        </div>

        <h2 className="text-2xl font-semibold mb-4">Em cartaz</h2>

        <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-6">
          {filmesFiltrados.map((filme, index) => (
            <div key={index} className="text-center">
              <div className="relative">
                <img
                  src={filme.imagem}
                  alt={filme.titulo}
                  className="rounded-lg shadow-lg hover:scale-105 transition-transform duration-300"
                />
                <span className="absolute top-2 left-2 bg-red-600 text-xs px-2 py-1 rounded text-white">
                  {filme.classificacao}
                </span>
              </div>
              <p className={`mt-2 text-sm ${darkMode ? "text-white" : "text-black"}`}>
                {filme.titulo}
              </p>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}