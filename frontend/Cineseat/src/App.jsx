import React, { useState } from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import logoDark from "./assets/logo-dark.jpeg";
import logoLight from "./assets/logo-light.jpeg";


import Home from "./pages/Home";
import Login from "./pages/Login";
import Filmes from "./pages/Filmes";
import Cinema from "./pages/Cinema";
import Cadastro from "./pages/Cadastro";

export default function App() {
  const [darkMode, setDarkMode] = useState(true);

  // Seleciona logo conforme modo escuro ou claro
  const logo = darkMode ? logoDark : logoLight;

  return (
    <BrowserRouter>
      <div className={darkMode ? "min-h-screen bg-black text-white" : "min-h-screen bg-white text-black"}>
        

        {/* Rotas do site */}
        <Routes>
          <Route path="/" element={<Home darkMode={darkMode} setDarkMode={setDarkMode} />} />
          <Route path="/login" element={<Login />} />
          <Route path="/cadastro" element={<Cadastro />} />
          <Route path="/filmes" element={<Filmes />} />
          <Route path="/cinema" element={<Cinema />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}
