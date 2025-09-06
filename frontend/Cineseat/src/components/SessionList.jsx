import React from "react";

export default function SessionList({ movie, selectSession }) {
  if (!movie) return null;

  return (
    <div>
      <h2>Sessões de {movie.title}</h2>
      {movie.sessions.map((session) => (
        <div key={session.id}>
          <button onClick={() => selectSession(session)}>
            {session.time} - {session.format}
          </button>
        </div>
      ))}
    </div>
  );
}
