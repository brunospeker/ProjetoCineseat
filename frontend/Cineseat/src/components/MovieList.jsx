import React from "react";

export default function MovieList({ movies, selectMovie, city }) {
  const filteredMovies = movies.filter((movie) => movie.city === city);

  return (
    <div>
      <h2>Filmes em {city}</h2>
      {filteredMovies.map((movie) => (
        <button key={movie.id} onClick={() => selectMovie(movie)} style={{ display: "block", margin: "5px 0" }}>
          {movie.title}
        </button>
      ))}
    </div>
  );
}
