import { useState } from "react";
import Header from "../components/Navbar";
import { Search, ChevronDown } from "lucide-react";

const filmesEmCartaz = [
  {
    titulo: "O Bad Boy E Eu",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/o-bad-boy-e-eu/3628a819-f945-41c5-9831-e6d92504cb6f.webp",
    classificacao: "12",
  },
  {
    titulo: "Mauricio De Sousa: O Filme",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/mauricio-de-sousa-o-filme/72289697-9a7b-48ae-b5a9-2c91892b98ad.webp",
    classificacao: "L",
  },
  {
    titulo: "A Casa Mágica Da Gabby – O Filme",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/a-casa-magica-da-gabby-o-filme/7ac85bca-bdab-4ef6-b1f6-d3378ff9ecf8.webp",
    classificacao: "L",
  },
  {
    titulo: "Quando O Céu Se Engana",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/quando-o-ceu-se-engana/e337d096-2441-463b-a070-ffca1e655469.webp",
    classificacao: "14",
  },
  {
    titulo: "Grand Prix: A Toda Velocidade",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/grand-prix-a-toda-velocidade/2687668c-87df-43b3-a749-e048f2eaef65.webp",
    classificacao: "L",
  },
  {
    titulo: "Eu E Meu Avô Nihonjin",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/eu-e-meu-avo-nihonjin/599f24a2-bf70-408f-ae28-a5ec5cf4cb98.webp",
    classificacao: "L",
  },
  {
    titulo: "Uma Nova História",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/uma-nova-historia/835587cf-636e-4a52-872a-a446cdb3b6a9.webp",
    classificacao: "18",
  },
  {
    titulo: "Honey, Não!",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/honey-nao/d745fb7d-f195-428d-8f7f-010e075d7d07.webp",
    classificacao: "L",
  },
  {
    titulo: "Wicked ",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/wicked-relancamento/e5df2983-817e-4801-94d9-7ed70b82576d.webp",
    classificacao: "L",
  },
  {
    titulo: "Perrengue Fashion",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/perrengue-fashion/93e03333-1084-4fba-9cc0-ab72b37e183b.webp",
    classificacao: "14",
  },
  {
    titulo: "Se Não Fosse Você",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/se-nao-fosse-voce/ee41deae-8b9d-494e-b5ef-10ba12839eb3.webp",
    classificacao: "16",
  },
  {
    titulo: "Zuzubalândia O Filme",
    imagem: "https://ingresso-a.akamaihd.net/prd/img/movie/zuzubalandia-o-filme/98ea56ad-bca7-43ae-b809-aa573995ab4e.webp",
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