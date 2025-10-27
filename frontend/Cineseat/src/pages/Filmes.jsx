import { useState } from "react";
import Header from "../components/Navbar";
import { Search, ChevronDown } from "lucide-react";

const filmesEmCartaz = [
 
  {
    titulo: "Barbie e o Portal Secreto",
    imagem: "../posters/barbie.jpg",
    classificacao: "L",
  },
 
];

export default function Filmes() {
  const [busca, setBusca] = useState("");
  const [genero, setGenero] = useState("Todos os gêneros");
  const [darkMode, setDarkMode] = useState(true);

  const filmesFiltrados = filmesEmCartaz.filter((filme) =>
    filme.titulo.toLowerCase().includes(busca.toLowerCase())
  );

  return (
    <div className={`${darkMode ? "bg-black text-white" : "bg-white text-black"} min-h-screen`}>
      <Header darkMode={darkMode} setDarkMode={setDarkMode} />

      <main className="px-8 py-8">
        <h1 className="text-4xl font-bold mb-6">Filmes</h1>

        <div className="flex flex-col md:flex-row gap-4 mb-8">
          <div className="flex items-center bg-gray-900 border border-gray-700 rounded-xl px-4 py-2 flex-1">
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
              value={genero}
              onChange={(e) => setGenero(e.target.value)}
              className="appearance-none bg-gray-900 border border-gray-700 text-white px-4 py-2 pr-10 rounded-xl"
            >
              <option>Todos os gêneros</option>
              <option>Ação</option>
              <option>Drama</option>
              <option>Comédia</option>
              <option>Infantil</option>
              <option>Terror</option>
            </select>
            <ChevronDown className="absolute right-3 top-3 text-gray-400 w-5 h-5 pointer-events-none" />
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
                <span className="absolute top-2 left-2 bg-red-600 text-xs px-2 py-1 rounded">
                  {filme.classificacao}
                </span>
              </div>
              <p className="mt-2 text-sm">{filme.titulo}</p>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
