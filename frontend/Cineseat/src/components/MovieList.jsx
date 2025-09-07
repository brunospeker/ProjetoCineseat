import React from "react";

export default function MovieList({ movies, selectMovie, city }) {
  const filteredMovies = movies.filter((movie) => movie.city === city);

  return (
    <div style={{
      padding: "30px",
      backgroundColor: "#f8f9fa",
      borderRadius: "10px",
      margin: "20px",
      boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
    }}>
      <h2 style={{
        color: "#333",
        marginBottom: "25px",
        fontSize: "28px",
        textAlign: "center",
        borderBottom: "3px solid #007bff",
        paddingBottom: "15px"
      }}>Filmes em {city}</h2>
      
      <div style={{
        display: "grid",
        gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
        gap: "15px",
        marginTop: "20px"
      }}>
        {filteredMovies.map((movie) => (
          <button 
            key={movie.id} 
            onClick={() => selectMovie(movie)} 
            style={{
              padding: "15px 20px",
              backgroundColor: "#007bff",
              color: "white",
              border: "none",
              borderRadius: "8px",
              fontSize: "16px",
              fontWeight: "500",
              cursor: "pointer",
              transition: "all 0.3s ease",
              boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
              textAlign: "center",
              minHeight: "60px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center"
            }}
            onMouseEnter={(e) => {
              e.target.style.backgroundColor = "#0056b3";
              e.target.style.transform = "translateY(-2px)";
              e.target.style.boxShadow = "0 4px 8px rgba(0,0,0,0.2)";
            }}
            onMouseLeave={(e) => {
              e.target.style.backgroundColor = "#007bff";
              e.target.style.transform = "translateY(0)";
              e.target.style.boxShadow = "0 2px 4px rgba(0,0,0,0.1)";
            }}
          >
            {movie.title}
          </button>
        ))}
      </div>
    </div>
  );
}
