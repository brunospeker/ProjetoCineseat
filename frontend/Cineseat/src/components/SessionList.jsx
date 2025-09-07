import React from "react";

export default function SessionList({ movie, selectSession }) {
  if (!movie) return null;

  return (
    <div style={{
      padding: "30px",
      backgroundColor: "#ffffff",
      borderRadius: "10px",
      margin: "20px",
      boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
      border: "1px solid #e9ecef"
    }}>
      <h2 style={{
        color: "#333",
        marginBottom: "25px",
        fontSize: "24px",
        textAlign: "center",
        borderBottom: "2px solid #28a745",
        paddingBottom: "15px"
      }}>Sessões de {movie.title}</h2>
      
      <div style={{
        display: "grid",
        gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
        gap: "15px",
        marginTop: "20px"
      }}>
        {movie.sessions.map((session) => (
          <button 
            key={session.id} 
            onClick={() => selectSession(session)}
            style={{
              padding: "15px 20px",
              backgroundColor: "#28a745",
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
              justifyContent: "center",
              flexDirection: "column",
              gap: "5px"
            }}
            onMouseEnter={(e) => {
              e.target.style.backgroundColor = "#218838";
              e.target.style.transform = "translateY(-2px)";
              e.target.style.boxShadow = "0 4px 8px rgba(0,0,0,0.2)";
            }}
            onMouseLeave={(e) => {
              e.target.style.backgroundColor = "#28a745";
              e.target.style.transform = "translateY(0)";
              e.target.style.boxShadow = "0 2px 4px rgba(0,0,0,0.1)";
            }}
          >
            <div style={{ fontSize: "18px", fontWeight: "bold" }}>{session.time}</div>
            <div style={{ fontSize: "14px", opacity: "0.9" }}>{session.format}</div>
          </button>
        ))}
      </div>
    </div>
  );
}
