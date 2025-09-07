import React from "react";
import styles from './SessionList.module.css';

export default function SessionList({ movie, selectSession }) {
  if (!movie) return null;

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Sessões de {movie.title}</h2>
      
      <div className={styles.sessionsGrid}>
        {movie.sessions.map((session) => (
          <button 
            key={session.id} 
            onClick={() => selectSession(session)}
            className={styles.sessionButton}
          >
            <div className={styles.sessionTime}>{session.time}</div>
            <div className={styles.sessionFormat}>{session.format}</div>
          </button>
        ))}
      </div>
    </div>
  );
}
