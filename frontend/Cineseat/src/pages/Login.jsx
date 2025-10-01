import React, { useState } from "react";

export default function Login({ onLogin }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  function handleSubmit(e) {
    e.preventDefault();

    // aqui você poderia chamar uma API real
    if (email && password) {
      localStorage.setItem("user", JSON.stringify({ email }));
      onLogin({ email });
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-900 text-white">
      <form
        onSubmit={handleSubmit}
        className="bg-black p-6 rounded shadow-lg w-80 flex flex-col gap-4"
      >
        <h2 className="text-xl font-bold">Entrar</h2>
        <input
          type="email"
          placeholder="E-mail"
          className="p-2 rounded text-black"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="password"
          placeholder="Senha"
          className="p-2 rounded text-black"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className="bg-red-600 py-2 rounded hover:bg-red-700">
          Login
        </button>
      </form>
    </div>
  );
}
