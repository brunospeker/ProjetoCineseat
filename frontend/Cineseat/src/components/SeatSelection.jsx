import React, { useState } from "react";

export default function SeatSelection({ session }) {
  const [selectedSeats, setSelectedSeats] = useState([]);

  if (!session) return null;

  const seats = Array.from({ length: 40 }, (_, i) => i + 1);

  const toggleSeat = (seat) => {
    setSelectedSeats((prev) =>
      prev.includes(seat)
        ? prev.filter((s) => s !== seat)
        : [...prev, seat]
    );
  };

  // Organizar assentos em grupos de 10
  const seatRows = [];
  for (let i = 0; i < seats.length; i += 10) {
    seatRows.push(seats.slice(i, i + 10));
  }

  return (
    <div style={{
      padding: "20px",
      backgroundColor: "#f8f9fa",
      borderRadius: "10px",
      margin: "20px 0"
    }}>
      <h2 style={{
        textAlign: "center",
        color: "#333",
        marginBottom: "30px",
        fontSize: "24px"
      }}>Assentos da sessão {session.time}</h2>
      
      {/* Tela do cinema */}
      <div style={{
        textAlign: "center",
        marginBottom: "30px"
      }}>
        <div style={{
          width: "80%",
          height: "8px",
          background: "linear-gradient(45deg, #2c3e50, #34495e)",
          margin: "0 auto 10px",
          borderRadius: "4px",
          boxShadow: "0 2px 4px rgba(0,0,0,0.3)"
        }}></div>
        <div style={{
          fontSize: "14px",
          color: "#666",
          fontWeight: "bold",
          letterSpacing: "1px"
        }}>🎬 TELA 🎬</div>
      </div>

      {/* Grid de assentos */}
      <div style={{
        display: "flex",
        flexDirection: "column",
        gap: "15px",
        alignItems: "center",
        marginBottom: "25px"
      }}>
        {seatRows.map((row, rowIndex) => (
          <div key={rowIndex} style={{
            display: "flex",
            gap: "8px",
            alignItems: "center"
          }}>
            {/* Label da fileira */}
            <span style={{
              width: "25px",
              fontSize: "14px",
              fontWeight: "bold",
              textAlign: "center",
              color: "#495057"
            }}>
              {String.fromCharCode(65 + rowIndex)}
            </span>
            
            {/* Assentos da fileira */}
            {row.map((seat) => (
              <button
                key={seat}
                style={{
                  width: "40px",
                  height: "40px",
                  border: "2px solid",
                  borderColor: selectedSeats.includes(seat) ? "#28a745" : "#dee2e6",
                  borderRadius: "8px",
                  backgroundColor: selectedSeats.includes(seat) ? "#28a745" : "#ffffff",
                  color: selectedSeats.includes(seat) ? "white" : "#333",
                  fontSize: "14px",
                  fontWeight: "bold",
                  cursor: "pointer",
                  transition: "all 0.2s ease",
                  boxShadow: "0 2px 4px rgba(0,0,0,0.1)"
                }}
                onClick={() => toggleSeat(seat)}
                onMouseEnter={(e) => {
                  if (!selectedSeats.includes(seat)) {
                    e.target.style.backgroundColor = "#e3f2fd";
                    e.target.style.borderColor = "#2196f3";
                  }
                  e.target.style.transform = "scale(1.05)";
                }}
                onMouseLeave={(e) => {
                  if (!selectedSeats.includes(seat)) {
                    e.target.style.backgroundColor = "#ffffff";
                    e.target.style.borderColor = "#dee2e6";
                  }
                  e.target.style.transform = "scale(1)";
                }}
              >
                {seat}
              </button>
            ))}
            
            {/* Label da fileira (direita) */}
            <span style={{
              width: "25px",
              fontSize: "14px",
              fontWeight: "bold",
              textAlign: "center",
              color: "#495057"
            }}>
              {String.fromCharCode(65 + rowIndex)}
            </span>
          </div>
        ))}
      </div>

      {/* Legenda */}
      <div style={{
        display: "flex",
        justifyContent: "center",
        gap: "30px",
        marginBottom: "20px",
        padding: "15px",
        backgroundColor: "#ffffff",
        borderRadius: "8px",
        boxShadow: "0 2px 4px rgba(0,0,0,0.1)"
      }}>
        <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
          <div style={{
            width: "20px",
            height: "20px",
            backgroundColor: "#ffffff",
            border: "2px solid #dee2e6",
            borderRadius: "4px"
          }}></div>
          <span style={{ fontSize: "14px", color: "#666" }}>Disponível</span>
        </div>
        <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
          <div style={{
            width: "20px",
            height: "20px",
            backgroundColor: "#28a745",
            border: "2px solid #28a745",
            borderRadius: "4px"
          }}></div>
          <span style={{ fontSize: "14px", color: "#666" }}>Selecionado</span>
        </div>
      </div>

      {/* Assentos selecionados */}
      {selectedSeats.length > 0 && (
        <div style={{
          textAlign: "center",
          padding: "15px",
          backgroundColor: "#d4edda",
          border: "1px solid #c3e6cb",
          borderRadius: "8px",
          color: "#155724"
        }}>
          <strong>Assentos selecionados:</strong> {selectedSeats.sort((a, b) => a - b).join(", ")}
          <div style={{ marginTop: "8px", fontSize: "14px" }}>
            Total: {selectedSeats.length} assento{selectedSeats.length !== 1 ? 's' : ''}
          </div>
        </div>
      )}
    </div>
  );
}
