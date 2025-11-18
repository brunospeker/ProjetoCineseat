import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import Header from "../components/Navbar";
import { ArrowLeft, Star, Armchair, Popcorn } from "lucide-react";

export default function PoltronasSala({ darkMode, setDarkMode, user, onLogout }) {
  const { idCinema, idSala } = useParams();
  const navigate = useNavigate();
  
  const [cinema, setCinema] = useState(null);
  const [sala, setSala] = useState([]);
  const [salasavaliacao, setSalaAvaliacao] = useState([]);
  const [poltronasPorFileira, setPoltronasPorFileira] = useState({});
  //const [mediasSala, setMediasSala] = useState(null);

  useEffect(() => {
    async function carregarPoltronas() {
      try {
        const cinemaResponse = await fetch(`http://localhost:8080/api/cinemas/${idCinema}`);
        const cinemaData = await cinemaResponse.json();
        setCinema(cinemaData);
        console.log(cinemaData);

        const salasResponse = await fetch(`http://localhost:8080/api/salas/${idSala}`);
        const salasData = await salasResponse.json();
        setSala(salasData);
        console.log(salasData);

        const salasAvaliacaoResponse = await fetch(`http://localhost:8080/api/salas/${idSala}/avaliacoes`);
        const salasAvaliacaoData = await salasAvaliacaoResponse.json();
        setSalaAvaliacao(salasAvaliacaoData[0]);
        console.log(salasAvaliacaoData);

        const response = await fetch(`http://localhost:8080/api/poltronas/sala/${idSala}`);
        const data = await response.json();

        const agrupado = data.reduce((acc, poltrona) => {
          if (!acc[poltrona.fila]) acc[poltrona.fila] = [];
          acc[poltrona.fila].push(poltrona);
          return acc;
        }, {});

        for (const fila in agrupado) {
          agrupado[fila].sort((a, b) => Number(a.coluna) - Number(b.coluna));
        }

        setPoltronasPorFileira(agrupado);
        console.log(agrupado);
      } catch (err) {
        console.error("Erro ao carregar poltronas:", err);
      }
    }

    carregarPoltronas();
  }, [idSala]);

  const getColor = (media) => {
    if (media === null || media === 0) return "text-gray-400";
    if (media <= 2) return "text-blue-500";
    if (media <= 3) return "text-yellow-400";
    if (media <= 4) return "text-orange-500";
    return "text-red-600";
  };

  return (
    <div className={`${darkMode ? "bg-black text-white" : "bg-white text-black"} min-h-screen`}>
      <Header darkMode={darkMode} setDarkMode={setDarkMode} user={user} onLogout={onLogout} />

      <main className="p-8 max-w-6xl mx-auto relative">
        <button
          onClick={() => window.history.back()}
          className="absolute left-8 top-8 flex items-center gap-2 text-gray-400 hover:text-white transition"
        >
          <ArrowLeft className="w-4 h-4" />
          Voltar
        </button>

        <div className="mt-10 mb-6">
          <h1 className="text-4xl font-bold mb-2 flex items-center gap-2">
            {cinema?.nomeCinema}
            {cinema?.temBomboniere && <Popcorn className="text-yellow-400 w-6 h-6" />}
          </h1>
          <p className="text-gray-400">
            {cinema?.bairro}, {cinema?.numero} — {cinema?.cidade}/{cinema?.estado}
          </p>
        </div>

        <h2 className="text-2xl font-semibold mb-4 text-center">{sala.nome}</h2>

        <div className="flex items-center gap-4 mt-3 mb-3 justify-center">
          <span className="bg-blue-600 px-2 py-1 rounded text-xs text-white">{sala.tipo_som}</span>
          <span className="bg-blue-600 px-2 py-1 rounded text-xs text-white">{sala.tipo_tela}</span>
        </div>

        <div className="mb-10 grid grid-cols-1 sm:grid-cols-3 lg:grid-cols-5 gap-4">
          {[
            { label: "Nota Geral da Sala", value: sala?.media_avaliacoes },
            { label: "Som", value: salasavaliacao?.nota_som },
            { label: "Imagem", value: salasavaliacao?.nota_imagem },
            { label: "Limpeza", value: salasavaliacao?.nota_limpeza },
            { label: "Temperatura", value: salasavaliacao?.nota_temperatura },
          ].map((item, index) => (
            <div
              key={index}
              className={`p-4 rounded-xl border ${darkMode ? "bg-neutral-900 border-gray-700" : "bg-white border-gray-300"}`}
            >
              <h4 className="font-semibold mb-2">{item.label}</h4>
              <div className="flex items-center gap-2 text-yellow-400 text-lg">
                <Star className="w-4 h-4" />
                {item.value?.toFixed(1)}
              </div>
            </div>
          ))}
        </div>

        <div className="flex flex-col gap-3 mt-4">
          {Object.keys(poltronasPorFileira)
            .sort()
            .reverse() //inversão da ordem pra começar de Z e terminar A
            .map((fila) => (
              <div key={fila} className="flex gap-3 justify-center">
                {poltronasPorFileira[fila].map((poltrona) => (
                  <div
                    key={poltrona.idPoltrona}
                    onClick={() => navigate(`/avaliar/${idCinema}/${idSala}/${poltrona.idPoltrona}`)}
                    className="flex flex-col items-center text-center cursor-pointer hover:scale-110 transition"
                  >
                    <Armchair className={`w-8 h-8 drop-shadow ${getColor(poltrona.mediaGeral)}`} />
                    <span className="text-xs mt-1 text-gray-300 font-medium">
                      {poltrona.fila}{poltrona.coluna}
                    </span>
                  </div>
                ))}
              </div>
            ))}
        </div>

        <div className="mt-6 flex justify-center">
          <div className="w-[80%] h-8 bg-gray-600 rounded-md flex items-center justify-center text-white font-semibold tracking-widest">
            TELA
          </div>
        </div>
      </main>
    </div>
  );
}
