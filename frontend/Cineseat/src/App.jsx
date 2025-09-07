import React, { useState } from "react";
import { movies } from "./data/movies";
import Header from "./components/Header";
import MovieList from "./components/MovieList";
import SessionList from "./components/SessionList";
import SeatSelection from "./components/SeatSelection";
import Room from "./components/room";
import "./App.css";

export default function App() {
  const [selectedCity, setSelectedCity] = useState("São Paulo");
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [selectedSession, setSelectedSession] = useState(null);
  const [currentView, setCurrentView] = useState("movies"); 

  const renderContent = () => {
    switch (currentView) {
      case "rooms":
        return <Room selectedCity={selectedCity} />;
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
      
      <main style={{ padding: "20px" }}>
        {renderContent()}
      </main>
    </div>
  );
}

