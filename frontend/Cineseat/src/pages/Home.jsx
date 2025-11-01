import React from "react";
import SearchBar from "../components/SearchBar";
import MovieSection from "../components/MovieSection";
import Navbar from "../components/Navbar";

// duas imagens (uma para cada tema)
import poltronaEscura from "../assets/poltronas-dark.jpg";
import poltronaClara from "../assets/poltronas-light.jpg";

export default function Home({ darkMode, setDarkMode, user, onLogout }) {
  return (
    <div className="min-h-screen flex flex-col">
      {/* Topo com imagem que troca conforme o tema */}
      <div className="relative w-full min-h-[400px] lg:min-h-[500px]">
  <img
    src={darkMode ? poltronaEscura : poltronaClara}
    alt="Poltronas do cinema"
    className="absolute inset-0 w-full h-full object-cover"
  />


        {/* Overlay preto só no darkMode (pra dar contraste) */}
        {darkMode}

        {/* Conteúdo em cima da imagem */}
        <div className="relative z-10">
          <Navbar darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={onLogout}/>
          <div className="mt-10">
            <SearchBar darkMode={darkMode} />
          </div>
        </div>
      </div>

      {/* Restante da página com fundo dinâmico */}
      <div className={darkMode ? "flex-1 bg-black text-white" : "flex-1 bg-white text-black"}>
        <MovieSection title="Em Alta" />
        <MovieSection title="Em cartaz" />
      </div>
    </div>
  );
}
