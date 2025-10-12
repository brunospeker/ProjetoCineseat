import React, { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../assets/logo-dark.jpeg"; // logo vermelha da poltrona

export default function Login({ onLogin }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  function handleSubmit(e) {
    e.preventDefault();
    if (email && password) {
      localStorage.setItem("user", JSON.stringify({ email }));
      onLogin({ email });
    }
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-black text-white">
      {/* Logo (agora clicável) */}
      <div className="flex flex-col items-center mb-6">
        <Link to="/" className="flex flex-col items-center hover:opacity-90 transition">
          <img src={logo} alt="Cineseat Logo" className="h-20 mb-2 cursor-pointer" />
          <h1 className="text-4xl font-bold">CINESEAT</h1>
        </Link>
      </div>

      {/* Card de login */}
      <form
        onSubmit={handleSubmit}
        className="bg-neutral-900 p-10 rounded-lg shadow-lg flex flex-col gap-6 w-[400px]"
      >
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
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button
          type="submit"
          className="border border-red-600 text-white rounded-full py-2 text-lg hover:bg-red-600 transition"
        >
          Login
        </button>

        <div className="text-center mt-2">
          <Link to="/cadastro" className="hover:underline">
            Cadastre-se
          </Link>
        </div>
      </form>
    </div>
  );
}
