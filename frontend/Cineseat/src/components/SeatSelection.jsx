import React, { useState } from "react";
import styles from './SeatSelection.module.css';

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


  const seatRows = [];
  for (let i = 0; i < seats.length; i += 10) {
    seatRows.push(seats.slice(i, i + 10));
  }

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Assentos da sessão {session.time}</h2>
      
      <div className={styles.screenContainer}>
        <div className={styles.screen}></div>
        <div className={styles.screenLabel}>🎬 TELA 🎬</div>
      </div>


      <div className={styles.seatingArea}>
        {seatRows.map((row, rowIndex) => (
          <div key={rowIndex} className={styles.seatRow}>
            <span className={styles.rowLabel}>
              {String.fromCharCode(65 + rowIndex)}
            </span>
            

            {row.map((seat) => (
              <button
                key={seat}
                onClick={() => toggleSeat(seat)}
                className={`${styles.seat} ${selectedSeats.includes(seat) ? styles.seatSelected : ''}`}
              >
                {seat}
              </button>
            ))}
            <span className={styles.rowLabel}>
              {String.fromCharCode(65 + rowIndex)}
            </span>
          </div>
        ))}
      </div>


      <div className={styles.legend}>
        <div className={styles.legendItem}>
          <div className={`${styles.legendSeat} ${styles.legendAvailable}`}></div>
          <span className={styles.legendText}>Disponível</span>
        </div>
        <div className={styles.legendItem}>
          <div className={`${styles.legendSeat} ${styles.legendSelected}`}></div>
          <span className={styles.legendText}>Selecionado</span>
        </div>
      </div>


      {selectedSeats.length > 0 && (
        <div className={styles.selectedSeats}>
          <strong>Assentos selecionados:</strong> {selectedSeats.sort((a, b) => a - b).join(", ")}
          <div className={styles.selectedSeatsTotal}>
            Total: {selectedSeats.length} assento{selectedSeats.length !== 1 ? 's' : ''}
          </div>
        </div>
      )}
    </div>
  );
}
