import React, { useState } from "react";

export default function SeatSelection({ session }) {
  const [selectedSeats, setSelectedSeats] = useState([]);

  if (!session) return null;

  const seats = Array.from({ length: 10 }, (_, i) => i + 1);

  const toggleSeat = (seat) => {
    setSelectedSeats((prev) =>
      prev.includes(seat)
        ? prev.filter((s) => s !== seat)
        : [...prev, seat]
    );
  };

  return (
    <div>
      <h2>Assentos da sessão {session.time}</h2>
      <div style={{ display: "flex", gap: "5px", marginBottom: "10px" }}>
        {seats.map((seat) => (
          <button
            key={seat}
            style={{
              background: selectedSeats.includes(seat) ? "green" : "lightgray",
              width: "30px",
              height: "30px",
            }}
            onClick={() => toggleSeat(seat)}
          >
            {seat}
          </button>
        ))}
      </div>
      {selectedSeats.length > 0 && (
        <p>Assentos selecionados: {selectedSeats.join(", ")}</p>
      )}
    </div>
  );
}
