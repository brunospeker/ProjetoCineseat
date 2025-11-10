import React, { useState } from "react";
import Navbar from "../components/Navbar";
import { Camera, Save, Key, LogOut } from "lucide-react";

export default function Conta({ darkMode, setDarkMode, user, onLogout }) {
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({
    nome: user?.username || "",
    email: user?.email || "",
    foto: user?.foto || "",
  });
  const [senhaData, setSenhaData] = useState({
    senhaAtual: "",
    novaSenha: "",
    confirmarSenha: "",
  });

  const handleSave = () => {
    // Aqui você implementaria a lógica para salvar no backend
    console.log("Dados salvos:", formData);
    setEditMode(false);
    // Atualizar o usuário no localStorage
    const updatedUser = { ...user, ...formData };
    localStorage.setItem("user", JSON.stringify(updatedUser));
  };

  const handleSenhaChange = () => {
    // Aqui você implementaria a lógica para alterar senha no backend
    console.log("Alterar senha:", senhaData);
    setSenhaData({ senhaAtual: "", novaSenha: "", confirmarSenha: "" });
    alert("Senha alterada com sucesso!");
  };

  const handleFotoChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        setFormData({ ...formData, foto: e.target.result });
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <div className={darkMode ? "min-h-screen bg-black text-white" : "min-h-screen bg-white text-black"}>
      <Navbar darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={onLogout} />

      <main className="max-w-4xl mx-auto px-6 py-8">
        <h1 className="text-3xl font-bold mb-8">Minha Conta</h1>

        {/* Seção de Informações Pessoais */}
        <div className={`rounded-lg p-6 mb-6 ${
          darkMode ? "bg-gray-900" : "bg-gray-50"
        }`}>
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-xl font-semibold">Informações Pessoais</h2>
            {!editMode ? (
              <button
                onClick={() => setEditMode(true)}
                className={`px-4 py-2 rounded-lg transition ${
                  darkMode 
                    ? "bg-blue-600 hover:bg-blue-700" 
                    : "bg-blue-500 hover:bg-blue-600 text-white"
                }`}
              >
                Editar Perfil
              </button>
            ) : (
              <button
                onClick={handleSave}
                className="flex items-center gap-2 px-4 py-2 bg-green-600 hover:bg-green-700 text-white rounded-lg transition"
              >
                <Save size={18} />
                Salvar Alterações
              </button>
            )}
          </div>

          <div className="flex flex-col md:flex-row gap-6">
            {/* Foto de Perfil */}
            <div className="flex flex-col items-center">
              <div className="relative">
                <div className="w-32 h-32 rounded-full overflow-hidden border-4 border-gray-300">
                  {formData.foto ? (
                    <img 
                      src={formData.foto} 
                      alt="Foto do perfil" 
                      className="w-full h-full object-cover"
                    />
                  ) : (
                    <div className={`w-full h-full flex items-center justify-center text-4xl font-bold ${
                      darkMode ? "bg-gray-800" : "bg-gray-200"
                    }`}>
                      {user?.username?.charAt(0).toUpperCase() || "U"}
                    </div>
                  )}
                </div>
                {editMode && (
                  <label className="absolute bottom-2 right-2 bg-blue-600 p-2 rounded-full cursor-pointer hover:bg-blue-700 transition">
                    <Camera size={16} className="text-white" />
                    <input
                      type="file"
                      accept="image/*"
                      onChange={handleFotoChange}
                      className="hidden"
                    />
                  </label>
                )}
              </div>
              {editMode && (
                <p className="text-sm text-gray-500 mt-2 text-center">
                  Clique na câmera para alterar
                </p>
              )}
            </div>

            {/* Informações do Usuário */}
            <div className="flex-1 space-y-4">
              <div>
                <label className={`block text-sm font-medium mb-2 ${
                  darkMode ? "text-gray-300" : "text-gray-700"
                }`}>
                  Nome de Exibição
                </label>
                {editMode ? (
                  <input
                    type="text"
                    value={formData.nome}
                    onChange={(e) => setFormData({ ...formData, nome: e.target.value })}
                    className={`w-full px-3 py-2 rounded-lg border ${
                      darkMode 
                        ? "bg-gray-800 border-gray-600 text-white" 
                        : "bg-white border-gray-300 text-black"
                    }`}
                  />
                ) : (
                  <p className={`px-3 py-2 rounded-lg ${
                    darkMode ? "bg-gray-800" : "bg-white"
                  }`}>
                    {formData.nome}
                  </p>
                )}
              </div>

              <div>
                <label className={`block text-sm font-medium mb-2 ${
                  darkMode ? "text-gray-300" : "text-gray-700"
                }`}>
                  E-mail
                </label>
                <p className={`px-3 py-2 rounded-lg ${
                  darkMode ? "bg-gray-800" : "bg-white"
                }`}>
                  {formData.email}
                </p>
                <p className="text-sm text-gray-500 mt-1">
                  O e-mail não pode ser alterado
                </p>
              </div>
            </div>
          </div>
        </div>

        {/* Seção de Segurança */}
        <div className={`rounded-lg p-6 mb-6 ${
          darkMode ? "bg-gray-900" : "bg-gray-50"
        }`}>
          <div className="flex items-center gap-2 mb-6">
            <Key size={20} className={darkMode ? "text-yellow-400" : "text-yellow-600"} />
            <h2 className="text-xl font-semibold">Segurança</h2>
          </div>

          <div className="space-y-4">
            <div>
              <label className={`block text-sm font-medium mb-2 ${
                darkMode ? "text-gray-300" : "text-gray-700"
              }`}>
                Senha Atual
              </label>
              <input
                type="password"
                value={senhaData.senhaAtual}
                onChange={(e) => setSenhaData({ ...senhaData, senhaAtual: e.target.value })}
                className={`w-full px-3 py-2 rounded-lg border ${
                  darkMode 
                    ? "bg-gray-800 border-gray-600 text-white" 
                    : "bg-white border-gray-300 text-black"
                }`}
                placeholder="Digite sua senha atual"
              />
            </div>

            <div>
              <label className={`block text-sm font-medium mb-2 ${
                darkMode ? "text-gray-300" : "text-gray-700"
              }`}>
                Nova Senha
              </label>
              <input
                type="password"
                value={senhaData.novaSenha}
                onChange={(e) => setSenhaData({ ...senhaData, novaSenha: e.target.value })}
                className={`w-full px-3 py-2 rounded-lg border ${
                  darkMode 
                    ? "bg-gray-800 border-gray-600 text-white" 
                    : "bg-white border-gray-300 text-black"
                }`}
                placeholder="Digite a nova senha"
              />
            </div>

            <div>
              <label className={`block text-sm font-medium mb-2 ${
                darkMode ? "text-gray-300" : "text-gray-700"
              }`}>
                Confirmar Nova Senha
              </label>
              <input
                type="password"
                value={senhaData.confirmarSenha}
                onChange={(e) => setSenhaData({ ...senhaData, confirmarSenha: e.target.value })}
                className={`w-full px-3 py-2 rounded-lg border ${
                  darkMode 
                    ? "bg-gray-800 border-gray-600 text-white" 
                    : "bg-white border-gray-300 text-black"
                }`}
                placeholder="Confirme a nova senha"
              />
            </div>

            <button
              onClick={handleSenhaChange}
              disabled={!senhaData.senhaAtual || !senhaData.novaSenha || !senhaData.confirmarSenha || senhaData.novaSenha !== senhaData.confirmarSenha}
              className={`px-4 py-2 rounded-lg transition ${
                (!senhaData.senhaAtual || !senhaData.novaSenha || !senhaData.confirmarSenha || senhaData.novaSenha !== senhaData.confirmarSenha)
                  ? "bg-gray-500 cursor-not-allowed"
                  : darkMode 
                    ? "bg-yellow-600 hover:bg-yellow-700" 
                    : "bg-yellow-500 hover:bg-yellow-600 text-white"
              }`}
            >
              Alterar Senha
            </button>
          </div>
        </div>

        {/* Seção de Ações */}
        <div className={`rounded-lg p-6 ${
          darkMode ? "bg-gray-900" : "bg-gray-50"
        }`}>
          <div className="flex items-center gap-2 mb-6">
            <LogOut size={20} className={darkMode ? "text-red-400" : "text-red-600"} />
            <h2 className="text-xl font-semibold">Ações</h2>
          </div>

          <button
            onClick={onLogout}
            className={`flex items-center gap-2 px-6 py-3 rounded-lg transition ${
              darkMode 
                ? "bg-red-600 hover:bg-red-700" 
                : "bg-red-500 hover:bg-red-600 text-white"
            }`}
          >
            <LogOut size={18} />
            Sair da Conta
          </button>
        </div>
      </main>
    </div>
  );
}