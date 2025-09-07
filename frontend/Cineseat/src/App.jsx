import React, { useState } from "react";
import { movies } from "./data/movies";
import Header from "./components/Header";
import MovieList from "./components/MovieList";
import SessionList from "./components/SessionList";
import SeatSelection from "./components/SeatSelection";
import Room from "./components/room";

export default function App() {
  const [selectedCity, setSelectedCity] = useState("São Paulo");
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [selectedSession, setSelectedSession] = useState(null);
  const [currentView, setCurrentView] = useState("movies"); // "movies" ou "rooms"

  const renderContent = () => {
    switch (currentView) {
      case "rooms":
        return <Room />;
      case "movies":
      default:
        return (
          <>
            <MovieList movies={movies} selectMovie={setSelectedMovie} city={selectedCity} />
            <SessionList movie={selectedMovie} selectSession={setSelectedSession} />
            <SeatSelection session={selectedSession} />
          </>
        );
    }
  };

  return (
    <div>
      <Header selectedCity={selectedCity} setSelectedCity={setSelectedCity} />
      
      {/* Navegação */}
      <div className="app-nav">
        <button 
          onClick={() => setCurrentView('movies')}
          className={`nav-btn ${currentView === 'movies' ? 'active' : ''}`}
        >
          Filmes e Sessões
        </button>
        <button 
          onClick={() => setCurrentView('rooms')}
          className={`nav-btn ${currentView === 'rooms' ? 'active' : ''}`}
        >
          Gerenciar Salas
        </button>
      </div>
      
      <main style={{ 
        padding: "0 20px 40px 20px", 
        width: "100%", 
        minHeight: "calc(100vh - 80px)", 
        boxSizing: "border-box",
        backgroundColor: "#f5f5f5"
      }}>
        {renderContent()}
      </main>
    </div>
  );
}

