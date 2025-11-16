import React, { useState } from "react";
import { BrowserRouter, Routes, Route, Link, Navigate } from "react-router-dom";
import logoDark from "./assets/logo-dark.jpeg";
import logoLight from "./assets/logo-light.jpeg";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Filmes from "./pages/Filmes";
import FilmeDetalhes from "./pages/FilmeDetalhes";
import Cinema from "./pages/Cinema";
import SalasCinema from "./pages/SalasCinema";
import Cadastro from "./pages/Cadastro";
import Perfil from "./pages/Perfil"; 
import Conta from "./pages/Conta";

export default function App() {
  const [darkMode, setDarkMode] = useState(true);

  // Estado do usuário logado
  const [user, setUser] = useState(() => {
    const savedUser = localStorage.getItem("user");
    return savedUser ? JSON.parse(savedUser) : null;
  });

  // Função que será passada para o Login.jsx
  function handleLogin(userData) {
    setUser(userData);
  }

  // Logout
  function handleLogout() {
    setUser(null);
    localStorage.removeItem("user");
    localStorage.removeItem("token");
  }

  // Seleciona logo conforme modo escuro ou claro
  const logo = darkMode ? logoDark : logoLight;

  return (
    <BrowserRouter>
      <div className={darkMode ? "min-h-screen bg-black text-white" : "min-h-screen bg-white text-black"}>
        
        {/* Rotas do site */}
        <Routes>
          <Route path="/" element={<Home darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={handleLogout} />} />
          <Route path="/login" element={!user ? <Login onLogin={handleLogin} /> : <Navigate to="/" />} />
          <Route path="/cadastro" element={<Cadastro darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={handleLogout} />} />
          <Route path="/perfil" element={<Perfil darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={handleLogout} />} />
          <Route path="/conta" element={<Conta darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={handleLogout} />} />
          <Route path="/filmes" element={<Filmes darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={handleLogout} />} />
          <Route path="/filme/:id" element={<FilmeDetalhes darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={handleLogout} />} />
          <Route path="/cinema" element={<Cinema darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={handleLogout} />} />
          <Route path="/cinema/:id" element={<SalasCinema darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={handleLogout} />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}