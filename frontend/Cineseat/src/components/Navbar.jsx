import React from "react";
import { Link } from "react-router-dom";
import { Sun, Moon } from "lucide-react";
import logoDark from "../assets/logo-dark.jpeg";   // logo para fundo escuro
import logoLight from "../assets/logo-light.jpeg"; // logo para fundo claro

export default function Navbar({ darkMode, setDarkMode, user, onLogout }) {
  return (
    <nav
      className={`flex items-center justify-between px-6 py-4 shadow-md ${
        darkMode ? "bg-black-900 text-white" : "bg-white text-black"
      }`}
    >
      {/* Logo + nome */}
<div className="flex items-center gap-2">
  <Link to="/" className="flex items-center gap-2">
    <img
      src={darkMode ? logoDark : logoLight}
      alt="Cineseat Logo"
      className="h-10 w-auto rounded cursor-pointer"
    />
    <span className="text-2xl font-bold tracking-wide">CINESEAT</span>
  </Link>
</div>


      {/* Menu à direita */}
      <div className="flex items-center gap-6">
        {/* Filtro de estado */}
        <select
          className={`border px-3 py-1 rounded ${
            darkMode
              ? "bg-black-800 text-white border-white-600"
              : "bg-white text-black border-gray-400"
          }`}
        >
          <option>Estado</option>
          <option>SP</option>
          <option>RJ</option>
          <option>MG</option>
          <option>RS</option>
        </select>

        {/* Navegação entre páginas */}
        <Link
          to="/filmes"
           className={`hover:underline ${
            darkMode ? "hover:text-red-400" : "hover:text-red-600"
          }`}
        >
          Filmes
        </Link>

        <Link
          to="/cinema"
          className={`hover:underline ${
            darkMode ? "hover:text-red-400" : "hover:text-red-600"
          }`}
        >
          Cinema
        </Link>

        {/* Toggle dark/light */}
        <button
          onClick={() => setDarkMode(!darkMode)}
          className="p-2 rounded transition hover:bg-gray-200 dark:hover:bg-gray-700"
        >
          {darkMode ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
        </button>

        {/* Botão de login/logout */}
        {user ? (
            <div className="flex items-center gap-3">
            <Link
              to="/perfil"
              className={`border px-4 py-1 rounded transition ${
                darkMode
                  ? "border-blue-500 hover:bg-blue-600"
                  : "border-blue-600 hover:bg-blue-500 hover:text-white"
              }`}
            >
              {user.username}
            </Link>
            <button
            onClick={onLogout}
            className={`border px-4 py-1 rounded transition ${
              darkMode
                ? "border-red-500 hover:bg-red-600"
                : "border-red-600 hover:bg-red-500 hover:text-white"
            }`}
          >
            Sair
          </button>
          </div>
        ) : (
        <Link
          to="/login"
          className={`border px-4 py-1 rounded transition ${
            darkMode
              ? "border-red-500 hover:bg-red-600"
              : "border-red-600 hover:bg-red-500 hover:text-white"
          }`}
        >
          Entrar
        </Link>
        )}
      </div>
    </nav>
  );
}
