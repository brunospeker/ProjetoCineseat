import React, { useEffect, useState } from "react";
import MovieCard from "./MovieCard";

export default function MovieSection({ title }) {

  const [movies, setMovies] = useState([]);
  const api_key = "606cca6cdedaffa15296e36ac4d914ef";
  let section;
  if (title == "Em Alta") {
    section = "popular";
  } else if (title == "Em cartaz") {
    section = "now_playing";
  }
  useEffect(() => {
    async function fetchMovies() {
      try {
        const response = await fetch(
          `https://api.themoviedb.org/3/movie/${section}?api_key=${api_key}&language=pt-BR&page=1&region=BR`
        );
        const data = await response.json();
        setMovies(data.results);
        console.log(data.results);
      } catch (error) {
        console.log(error);
      }
    }

    fetchMovies();
  }, []);

  return (
    <section className="px-6 py-4">
      <h2 className="text-xl font-semibold mb-4">{title}</h2>
      <div className="flex gap-4 overflow-x-auto">

        {movies.map((movie) => (
          <MovieCard
            key={movie.id}
            title={movies.title}
            image={`https://image.tmdb.org/t/p/w500${movie.poster_path}`} />
        ))}
      </div>
    </section>
  );
}
