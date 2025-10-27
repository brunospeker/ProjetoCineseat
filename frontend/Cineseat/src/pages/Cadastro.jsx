import React, { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../assets/logo-dark.jpeg";

export default function Cadastro() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  function handleSubmit(e) {
    e.preventDefault();
    if (nome && email && senha) {
      alert("Conta criada com sucesso!");
    }
  }

  return (
    <div className="min-h-screen flex bg-black text-white">
      {/* Lado esquerdo - logo */}
      <div className="w-1/2 flex flex-col items-center justify-center">
        {/* ✅ Agora a logo leva para a Home */}
        <Link to="/" className="flex flex-col items-center">
          <img src={logo} alt="Cineseat Logo" className="h-24 mb-4 cursor-pointer" />
          <h1 className="text-5xl font-bold">CINESEAT</h1>
        </Link>
      </div>

      {/* Lado direito - formulário */}
      <div className="w-1/2 flex items-center justify-center bg-neutral-900">
        <form
          onSubmit={handleSubmit}
          className="flex flex-col gap-6 w-[400px] p-10"
        >
          <h2 className="text-3xl font-bold text-center mb-4">
            Crie uma conta
          </h2>

          <input
            type="text"
            placeholder="Nome"
            className="w-full px-4 py-3 bg-black border border-white rounded-md text-white placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-red-600"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
          />

          <input
            type="email"
            placeholder="Email"
            className="w-full px-4 py-3 bg-black border border-white rounded-md text-white placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-red-600"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            type="password"
            placeholder="Senha"
            className="w-full px-4 py-3 bg-black border border-white rounded-md text-white placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-red-600"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />

          <button
            type="submit"
            className="border border-red-600 text-white rounded-full py-2 text-lg hover:bg-red-600 transition"
          >
            Criar conta
          </button>

          <div className="text-center mt-2">
            <Link to="/login" className="hover:underline">
              Já tem uma conta? Faça login
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
