import React, { useEffect, useState } from "react";
import MovieCard from "./MovieCard";

export default function MovieSection({ title }) {

  const [movie, setMovie] = useState([]);
  const api_key = "606cca6cdedaffa15296e36ac4d914ef"

  

  return (
    <section className="px-6 py-4">
      <h2 className="text-xl font-semibold mb-4">{title}</h2>
      <div className="flex gap-4 overflow-x-auto">
        <MovieCard title="Invocação do Mal 4" image="/movie1.jpg" />
        <MovieCard title="A Vida de Chuck" image="/movie2.jpg" />
        <MovieCard title="Toy Story (1995)" image="/movie3.jpg" />
      </div>
    </section>
  );
}
