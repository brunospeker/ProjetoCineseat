import React from "react";
import { roomTypes, roomStatus } from "../data/rooms";
import styles from './RoomDetails.module.css';

export default function RoomDetails({ room, onClose }) {
  if (!room) return null;

  const getStatusColor = (status) => {
    const statusObj = roomStatus.find(s => s.id === status);
    return statusObj ? statusObj.color : "gray";
  };

  const getTypeDisplay = (type) => {
    const typeObj = roomTypes.find(t => t.id === type);
    return typeObj ? typeObj.name : type;
  };

  const getTypeDescription = (type) => {
    const typeObj = roomTypes.find(t => t.id === type);
    return typeObj ? typeObj.description : "";
  };

  const occupancyPercentage = ((room.seats.occupied / room.seats.total) * 100).toFixed(1);


  const generateSeatLayout = () => {
    const layout = [];
    for (let row = 1; row <= room.rows; row++) {
      const rowSeats = [];
      for (let seat = 1; seat <= room.seatsPerRow; seat++) {
        const seatNumber = (row - 1) * room.seatsPerRow + seat;
        const isOccupied = seatNumber <= room.seats.occupied;
        rowSeats.push({
          number: seatNumber,
          row: row,
          seat: seat,
          occupied: isOccupied
        });
      }
      layout.push(rowSeats);
    }
    return layout;
  };

  const seatLayout = generateSeatLayout();

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
  
        <div className={styles.header}>
          <div>
            <h2 className={styles.title}>{room.name}</h2>
            <div className={styles.badges}>
              <span 
                className={styles.statusBadge}
                style={{ backgroundColor: getStatusColor(room.status) }}
              >
                {roomStatus.find(s => s.id === room.status)?.name}
              </span>
              <span className={styles.typeBadge}>
                {getTypeDisplay(room.type)}
              </span>
            </div>
          </div>
          
          <div>
            <button
              onClick={onClose}
              className={styles.closeButton}
            >
              Fechar
            </button>
          </div>
        </div>

  
        <div className={styles.content}>
          <div>
            <h3 className={styles.sectionTitle}>Informações Gerais</h3>
            <div className={styles.infoGrid}>
              <div className={styles.infoRow}>
                <strong>Cinema:</strong>
                <span>{room.cinema}</span>
              </div>
              <div className={styles.infoRow}>
                <strong>Cidade:</strong>
                <span>{room.city}</span>
              </div>
              <div className={styles.infoRow}>
                <strong>Tipo:</strong>
                <span>{getTypeDisplay(room.type)}</span>
              </div>
              <div className={styles.infoRow}>
                <strong>Capacidade:</strong>
                <span>{room.capacity} lugares</span>
              </div>
              <div className={styles.infoRow}>
                <strong>Layout:</strong>
                <span>{room.rows} fileiras × {room.seatsPerRow} assentos</span>
              </div>
            </div>
            
            {getTypeDescription(room.type) && (
              <div className={styles.typeDescription}>
                <strong>Sobre este tipo de sala:</strong><br/>
                {getTypeDescription(room.type)}
              </div>
            )}
          </div>
          
          <div>
            <h3 className={styles.sectionTitle}>Ocupação Atual</h3>
            <div className={styles.occupancyGrid}>
              <div className={`${styles.occupancyCard} ${styles.occupancyCardTotal}`}>
                <div className={`${styles.occupancyNumber} ${styles.occupancyNumberTotal}`}>
                  {room.seats.total}
                </div>
                <div className={styles.occupancyLabel}>Total</div>
              </div>
              <div className={`${styles.occupancyCard} ${styles.occupancyCardAvailable}`}>
                <div className={`${styles.occupancyNumber} ${styles.occupancyNumberAvailable}`}>
                  {room.seats.available}
                </div>
                <div className={styles.occupancyLabel}>Disponíveis</div>
              </div>
              <div className={`${styles.occupancyCard} ${styles.occupancyCardOccupied}`}>
                <div className={`${styles.occupancyNumber} ${styles.occupancyNumberOccupied}`}>
                  {room.seats.occupied}
                </div>
                <div className={styles.occupancyLabel}>Ocupados</div>
              </div>
            </div>
            
      
            <div className={styles.progressContainer}>
              <div className={styles.progressHeader}>
                <span className={styles.progressLabel}>Taxa de Ocupação</span>
                <span className={styles.progressLabel}>{occupancyPercentage}%</span>
              </div>
              <div className={styles.progressBar}>
                <div 
                  className={styles.progressFill}
                  style={{
                    width: `${occupancyPercentage}%`,
                    backgroundColor: occupancyPercentage > 80 ? "#dc3545" : occupancyPercentage > 50 ? "#ffc107" : "#28a745"
                  }}
                ></div>
              </div>
            </div>
          </div>
        </div>

  
        <div className={styles.featuresSection}>
          <h3 className={styles.sectionTitle}>Recursos da Sala</h3>
          <div className={styles.features}>
            {room.features.map((feature, index) => (
              <span 
                key={index}
                className={styles.featureBadge}
              >
                {feature}
              </span>
            ))}
          </div>
        </div>

  
        <div>
          <h3 className={styles.sectionTitle}>Layout da Sala</h3>
          
          <div className={styles.screenContainer}>
            <div className={styles.screen}></div>
            <div className={styles.screenLabel}>🎬 TELA 🎬</div>
          </div>
          
  
          <div className={styles.seatingArea}>
            {seatLayout.map((row, rowIndex) => (
              <div key={rowIndex} className={styles.seatRow}>
                <span className={styles.rowLabel}>
                  {String.fromCharCode(65 + rowIndex)}
                </span>
                {row.map((seat, seatIndex) => {
        
                  const isMiddle = seatIndex === Math.floor(row.length / 2);
                  return (
                    <React.Fragment key={seatIndex}>
                      {isMiddle && <div className={styles.corridor}></div>}
                      <div
                        className={`${styles.seat} ${seat.occupied ? styles.seatOccupied : styles.seatAvailable}`}
                        title={`Assento ${seat.number} - ${seat.occupied ? 'Ocupado' : 'Disponível'}`}
                      >
                        {seat.seat}
                      </div>
                    </React.Fragment>
                  );
                })}
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
              <div className={`${styles.legendSeat} ${styles.legendOccupied}`}></div>
              <span className={styles.legendText}>Ocupado</span>
            </div>
            <div className={styles.legendItem}>
              <div className={styles.legendCorridor}></div>
              <span className={styles.legendText}>Corredor</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}