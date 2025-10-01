import React from "react";
import { Sun, Moon } from "lucide-react";
import logoDark from "../assets/logo-dark.jpeg";   // logo para fundo escuro
import logoLight from "../assets/logo-light.jpeg"; // logo para fundo claro

export default function Navbar({ darkMode, setDarkMode }) {
  return (
    <nav className="flex items-center justify-between px-6 py-4">
      {/* Logo + nome */}
      <div className="flex items-center gap-2">
        <img
          src={darkMode ? logoDark : logoLight}
          alt="Cineseat Logo"
          className="h-8 w-auto"
        />
        <span className="text-2xl font-bold">CINESEAT</span>
      </div>

      {/* Menu à direita */}
      <div className="flex items-center gap-6">
        <select
          className={`border px-3 py-1 rounded ${
            darkMode ? "bg-black text-white border-white" : "bg-white text-black border-black"
          }`}
        >
          <option>Estado</option>
          <option>SP</option>
          <option>RJ</option>
        </select>
        <button className="hover:underline">Filmes</button>
        <button className="hover:underline">Cinema</button>

        {/* Toggle dark/light */}
        <button onClick={() => setDarkMode(!darkMode)} className="p-2">
          {darkMode ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
        </button>

        <button
          className={`border px-4 py-1 rounded transition ${
            darkMode ? "hover:bg-red-600" : "hover:bg-red-400"
          }`}
        >
          Entrar
        </button>
      </div>
    </nav>
  );
}
