import React from "react";
import styles from './MovieList.module.css';

export default function MovieList({ movies, selectMovie, city }) {
  const filteredMovies = movies.filter((movie) => movie.city === city);

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Filmes em {city}</h2>
      
      <div className={styles.moviesGrid}>
        {filteredMovies.map((movie) => (
          <button 
            key={movie.id} 
            onClick={() => selectMovie(movie)} 
            className={styles.movieButton}
          >
            {movie.title}
          </button>
        ))}
      </div>
    </div>
  );
}
