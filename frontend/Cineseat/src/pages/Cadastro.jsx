import React, { useState } from "react";
import { Link } from "react-router-dom";
import logo from "../assets/logo-dark.jpeg";

export default function Cadastro() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();

    if (!username && !email && !password) {
      alert("Preencha todos os campos!");
      return;
    }

    setLoading(true);

    try{
      const response = await fetch("http://localhost:8080/api/users/register",{
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, email, password})
      });

      if(!response.ok){
        const errorText = await response.json();
        throw new Error(errorText || "Erro ao criar conta");
      }

      const data = await response.json();
      alert(`Usuário ${data.username} criado com sucesso! Faça o Login na sua conta.`);

      //limpa os nomes (retirar depois para trocar o link de redirecionamento)
      setUsername("");
      setEmail("");
      setPassword("");

    } catch (error){
      console.error("Erro ao cadastrar:", error);
      alert(error.message || "Erro inesperado");
    } finally {
      setLoading(false);
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
            placeholder="Username"
            className="w-full px-4 py-3 bg-black border border-white rounded-md text-white placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-red-600"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
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
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button
            type="submit"
            disabled={loading}
            className={`border border-red-600 text-white rounded-full py-2 text-lg hover:bg-red-600 transition ${
              loading ? "opacity-50 cursor-not-allowed" : ""
            }`}
          >
            {loading ? "Criando..." : "Criar conta"}
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
