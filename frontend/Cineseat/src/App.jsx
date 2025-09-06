import React, { useState } from "react";
import { movies } from "./data/movies";
import Header from "./components/Header";
import MovieList from "./components/MovieList";
import SessionList from "./components/SessionList";
import SeatSelection from "./components/SeatSelection";

export default function App() {
  const [selectedCity, setSelectedCity] = useState("São Paulo");
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [selectedSession, setSelectedSession] = useState(null);

  return (
    <div>
      <Header selectedCity={selectedCity} setSelectedCity={setSelectedCity} />
      <main style={{ padding: "20px" }}>
        <MovieList movies={movies} selectMovie={setSelectedMovie} city={selectedCity} />
        <SessionList movie={selectedMovie} selectSession={setSelectedSession} />
        <SeatSelection session={selectedSession} />
      </main>
    </div>
  );
}

