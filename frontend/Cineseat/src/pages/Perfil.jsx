import React, { useState } from "react";
import Navbar from "../components/Navbar";

const TABS = ["Filmes", "Cinemas", "Salas", "Poltronas"];

function avg(numbers) {
  if (!numbers || numbers.length === 0) return 0;
  return (numbers.reduce((a, b) => a + b, 0) / numbers.length).toFixed(1);
}

const filmes = [
  {
    id: 1,
    nome: "A Vida de Chuck",
    genero: "Drama",
    data: "09/08/2025",
    foto: "https://ingresso-a.akamaihd.net/prd/img/movie/a-vida-de-chuck/918adc31-a705-4f28-b789-4df7d32187fc.webp",
    notas: [4, 5, 3, 5],
  },
  {
    id: 2,
    nome: "Barbie",
    genero: "Fantasia",
    data: "01/07/2025",
    foto: "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/yRRuLt7sMBEQkHsd1S3KaaofZn7.jpg",
    notas: [5, 4, 4],
  },
];

const cinemas = [
  { id: 1, nome: "CineCentro", estado: "SP", cidade: "São Paulo", atendimento: 4, limpeza: 5, alimentacao: 3 },
  { id: 2, nome: "Megaplex", estado: "RJ", cidade: "Rio de Janeiro", atendimento: 5, limpeza: 4, alimentacao: 4 },
  { id: 3, nome: "CineArt", estado: "MG", cidade: "Belo Horizonte", atendimento: 4, limpeza: 4, alimentacao: 5 },
];

const salas = [
  { id: 1, cinema: "CineCentro", numero: "Sala 1", estado: "SP", cidade: "São Paulo", imagemTela: 5, som: 4, limpeza: 5, temperatura: 4 },
  { id: 2, cinema: "Megaplex", numero: "Sala 2", estado: "RJ", cidade: "Rio de Janeiro", imagemTela: 4, som: 5, limpeza: 4, temperatura: 3 },
];

const poltronas = [
  { id: 1, numero: "A1", sala: "Sala 1", cinema: "CineCentro", estado: "SP", cidade: "São Paulo", visao: 5, conforto: 4, braco: 4 },
  { id: 2, numero: "B4", sala: "Sala 1", cinema: "CineCentro", estado: "SP", cidade: "São Paulo", visao: 4, conforto: 3, braco: 3 },
];

export default function Perfil({ darkMode, setDarkMode, user, onLogout }) {
  const [active, setActive] = useState("Filmes");

  return (
    <div className={darkMode ? "min-h-screen bg-black text-white" : "min-h-screen bg-white text-black"}>
      {/* Use o Navbar comum */}
      <Navbar darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={onLogout} />

      {/* Abas de navegação - parte específica do Perfil */}
      <div className="max-w-6xl mx-auto border-b border-gray-500/30 pb-4">
        <nav className="mt-4">
          <ul className="flex justify-between max-w-6xl mx-auto items-center gap-4 border-t border-b border-gray-500/30 py-3">
            {TABS.map((t) => (
              <li
                key={t}
                onClick={() => setActive(t)}
                className={`cursor-pointer px-4 py-2 text-center w-1/4 ${
                  active === t ? "text-red-500 font-bold border-b-4 border-red-500" : "text-black-200"
                }`}
              >
                {t}
              </li>
            ))}
          </ul>
        </nav>
      </div>

      {/* Conteúdo principal */}
      <main className="max-w-6xl mx-auto mt-8">
        {active === "Filmes" && <FilmesList darkMode={darkMode} />}
        {active === "Cinemas" && <CinemasList darkMode={darkMode} />}
        {active === "Salas" && <SalasList darkMode={darkMode} />}
        {active === "Poltronas" && <PoltronasList darkMode={darkMode} />}
      </main>
    </div>
  );
}

function FilmesList({ darkMode }) {
  return (
    <section className={`p-6 rounded ${
      darkMode ? "bg-[rgba(255,255,255,0.02)]" : "bg-gray-100"
    }`}>
      <h2 className={`text-xl font-semibold mb-4 ${
        darkMode ? "text-red-400" : "text-red-600"
      }`}>Lista de Filmes</h2>
      <div className="overflow-x-auto">
        <table className="w-full border-collapse">
          <thead>
            <tr className={`border-b ${
              darkMode ? "border-gray-700" : "border-gray-300"
            }`}>
              <th className="text-left py-2 px-4">Filme</th>
              <th className="text-left py-2 px-4">Nome</th>
              <th className="text-left py-2 px-4">Gênero</th>
              <th className="text-left py-2 px-4">Data</th>
              <th className="text-left py-2 px-4">Nota</th>
            </tr>
          </thead>
          <tbody>
            {filmes.map((f) => (
              <tr key={f.id} className={`border-b ${
                darkMode ? "border-gray-800 hover:bg-gray-800/50" : "border-gray-200 hover:bg-gray-200/50"
              }`}>
                <td className="py-3 px-4">
                  <img src={f.foto} alt={f.nome} className="w-16 h-24 object-cover rounded" />
                </td>
                <td className="py-3 px-4 font-medium">{f.nome}</td>
                <td className="py-3 px-4">{f.genero}</td>
                <td className="py-3 px-4">{f.data}</td>
                <td className={`py-3 px-4 font-semibold ${
                  darkMode ? "text-red-400" : "text-red-600"
                }`}>{avg(f.notas)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  );
}

function CinemasList({ darkMode }) {
  return (
    <section className={`p-6 rounded ${
      darkMode ? "bg-[rgba(255,255,255,0.02)]" : "bg-gray-100"
    }`}>
      <h2 className={`text-xl font-semibold mb-4 ${
        darkMode ? "text-red-400" : "text-red-600"
      }`}>Lista de Cinemas</h2>
      <div className="overflow-x-auto">
        <table className="w-full border-collapse">
          <thead>
            <tr className={`border-b ${
              darkMode ? "border-gray-700" : "border-gray-300"
            }`}>
              <th className="text-left py-2 px-4">Nome</th>
              <th className="text-left py-2 px-4">Estado</th>
              <th className="text-left py-2 px-4">Cidade</th>
              <th className="text-left py-2 px-4">Atendimento</th>
              <th className="text-left py-2 px-4">Limpeza</th>
              <th className="text-left py-2 px-4">Alimentação</th>
              <th className="text-left py-2 px-4">Nota Média</th>
            </tr>
          </thead>
          <tbody>
            {cinemas.map((c) => {
              const media = avg([c.atendimento, c.limpeza, c.alimentacao]);
              return (
                <tr key={c.id} className={`border-b ${
                  darkMode ? "border-gray-800 hover:bg-gray-800/50" : "border-gray-200 hover:bg-gray-200/50"
                }`}>
                  <td className="py-3 px-4 font-medium">{c.nome}</td>
                  <td className="py-3 px-4">
                    <span className="px-2 py-1 rounded text-xs bg-blue-500/20 text-blue-400">
                      {c.estado}
                    </span>
                  </td>
                  <td className="py-3 px-4">{c.cidade}</td>
                  <td className="py-3 px-4">{c.atendimento}</td>
                  <td className="py-3 px-4">{c.limpeza}</td>
                  <td className="py-3 px-4">{c.alimentacao}</td>
                  <td className={`py-3 px-4 font-semibold ${
                    darkMode ? "text-red-400" : "text-red-600"
                  }`}>{media}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </section>
  );
}

function SalasList({ darkMode }) {
  return (
    <section className={`p-6 rounded ${
      darkMode ? "bg-[rgba(255,255,255,0.02)]" : "bg-gray-100"
    }`}>
      <h2 className={`text-xl font-semibold mb-4 ${
        darkMode ? "text-red-400" : "text-red-600"
      }`}>Lista de Salas</h2>
      <div className="overflow-x-auto">
        <table className="w-full border-collapse">
          <thead>
            <tr className={`border-b ${
              darkMode ? "border-gray-700" : "border-gray-300"
            }`}>
              <th className="text-left py-2 px-4">Cinema</th>
              <th className="text-left py-2 px-4">Número da Sala</th>
              <th className="text-left py-2 px-4">Estado</th>
              <th className="text-left py-2 px-4">Cidade</th>
              <th className="text-left py-2 px-4">Imagem da Tela</th>
              <th className="text-left py-2 px-4">Som</th>
              <th className="text-left py-2 px-4">Limpeza</th>
              <th className="text-left py-2 px-4">Temperatura</th>
              <th className="text-left py-2 px-4">Nota Média</th>
            </tr>
          </thead>
          <tbody>
            {salas.map((s) => {
              const media = avg([s.imagemTela, s.som, s.limpeza, s.temperatura]);
              return (
                <tr key={s.id} className={`border-b ${
                  darkMode ? "border-gray-800 hover:bg-gray-800/50" : "border-gray-200 hover:bg-gray-200/50"
                }`}>
                  <td className="py-3 px-4 font-medium">{s.cinema}</td>
                  <td className="py-3 px-4">{s.numero}</td>
                  <td className="py-3 px-4">
                    <span className="px-2 py-1 rounded text-xs bg-blue-500/20 text-blue-400">
                      {s.estado}
                    </span>
                  </td>
                  <td className="py-3 px-4">{s.cidade}</td>
                  <td className="py-3 px-4">{s.imagemTela}</td>
                  <td className="py-3 px-4">{s.som}</td>
                  <td className="py-3 px-4">{s.limpeza}</td>
                  <td className="py-3 px-4">{s.temperatura}</td>
                  <td className={`py-3 px-4 font-semibold ${
                    darkMode ? "text-red-400" : "text-red-600"
                  }`}>{media}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </section>
  );
}

function PoltronasList({ darkMode }) {
  return (
    <section className={`p-6 rounded ${
      darkMode ? "bg-[rgba(255,255,255,0.02)]" : "bg-gray-100"
    }`}>
      <h2 className={`text-xl font-semibold mb-4 ${
        darkMode ? "text-red-400" : "text-red-600"
      }`}>Lista de Poltronas</h2>
      <div className="overflow-x-auto">
        <table className="w-full border-collapse">
          <thead>
            <tr className={`border-b ${
              darkMode ? "border-gray-700" : "border-gray-300"
            }`}>
              <th className="text-left py-2 px-4">Número</th>
              <th className="text-left py-2 px-4">Sala</th>
              <th className="text-left py-2 px-4">Cinema</th>
              <th className="text-left py-2 px-4">Estado</th>
              <th className="text-left py-2 px-4">Cidade</th>
              <th className="text-left py-2 px-4">Visão da Tela</th>
              <th className="text-left py-2 px-4">Conforto</th>
              <th className="text-left py-2 px-4">Braço</th>
              <th className="text-left py-2 px-4">Nota Média</th>
            </tr>
          </thead>
          <tbody>
            {poltronas.map((p) => {
              const media = avg([p.visao, p.conforto, p.braco]);
              return (
                <tr key={p.id} className={`border-b ${
                  darkMode ? "border-gray-800 hover:bg-gray-800/50" : "border-gray-200 hover:bg-gray-200/50"
                }`}>
                  <td className="py-3 px-4 font-medium">{p.numero}</td>
                  <td className="py-3 px-4">{p.sala}</td>
                  <td className="py-3 px-4">{p.cinema}</td>
                  <td className="py-3 px-4">
                    <span className="px-2 py-1 rounded text-xs bg-blue-500/20 text-blue-400">
                      {p.estado}
                    </span>
                  </td>
                  <td className="py-3 px-4">{p.cidade}</td>
                  <td className="py-3 px-4">{p.visao}</td>
                  <td className="py-3 px-4">{p.conforto}</td>
                  <td className="py-3 px-4">{p.braco}</td>
                  <td className={`py-3 px-4 font-semibold ${
                    darkMode ? "text-red-400" : "text-red-600"
                  }`}>{media}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </section>
  );
}