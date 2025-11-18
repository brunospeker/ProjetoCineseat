import React, { useState } from "react";
import { Link } from "react-router-dom";
import { Sun, Moon } from "lucide-react";
import logoDark from "../assets/logo-dark.jpeg";
import logoLight from "../assets/logo-light.jpeg";

export default function Navbar({ darkMode, setDarkMode, user, onLogout }) {
  const [showDropdown, setShowDropdown] = useState(false);

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
            {/* Perfil com dropdown */}
            <div className="relative">
              <button 
                onClick={() => setShowDropdown(!showDropdown)}
                className="flex items-center gap-2 p-2 rounded hover:bg-gray-200 dark:hover:bg-gray-700 transition-colors"
              >
                <div className="w-8 h-8 bg-red-600 rounded-full flex items-center justify-center text-white font-bold">
                  {user.username ? user.username.charAt(0).toUpperCase() : "U"}
                </div>
                <span>{user.username}</span>
              </button>
              
              {showDropdown && (
                <div className={`absolute right-0 mt-2 w-48 rounded-md shadow-lg py-1 z-10 ${
                  darkMode ? "bg-gray-800 border border-gray-700" : "bg-white border border-gray-200"
                }`}>
                  <Link 
                    to="/perfil" 
                    className={`block px-4 py-2 text-sm hover:bg-gray-200 dark:hover:bg-gray-700 ${
                      darkMode ? "text-white" : "text-black"
                    }`}
                    onClick={() => setShowDropdown(false)}
                  >
                    Minhas Avaliações
                  </Link>
                  <Link 
  to="/conta" 
  className={`block px-4 py-2 text-sm hover:bg-gray-200 dark:hover:bg-gray-700 ${
    darkMode ? "text-white" : "text-black"
  }`}
  onClick={() => setShowDropdown(false)}
>
  Conta
</Link>
                  <button 
                    onClick={() => {
                      onLogout();
                      setShowDropdown(false);
                    }}
                    className={`block w-full text-left px-4 py-2 text-sm hover:bg-gray-200 dark:hover:bg-gray-700 ${
                      darkMode ? "text-white" : "text-black"
                    }`}
                  >
                    Sair
                  </button>
                </div>
              )}
            </div>
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