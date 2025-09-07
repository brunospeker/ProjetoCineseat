import React from "react";
import { roomTypes, roomStatus } from "../data/rooms";

export default function RoomDetails({ room, onClose, onEdit }) {
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

  // Gerar layout visual dos assentos
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
    <div style={{ 
      position: "fixed", 
      top: 0, 
      left: 0, 
      right: 0, 
      bottom: 0, 
      backgroundColor: "rgba(0,0,0,0.5)", 
      display: "flex", 
      justifyContent: "center", 
      alignItems: "center",
      zIndex: 1000
    }}>
      <div style={{
        backgroundColor: "white",
        padding: "30px",
        borderRadius: "8px",
        width: "90%",
        maxWidth: "900px",
        maxHeight: "90vh",
        overflow: "auto"
      }}>
        {/* Header */}
        <div style={{ 
          display: "flex", 
          justifyContent: "space-between", 
          alignItems: "flex-start", 
          marginBottom: "20px" 
        }}>
          <div>
            <h2 style={{ margin: "0 0 10px 0", color: "#333" }}>{room.name}</h2>
            <div style={{ display: "flex", gap: "10px", alignItems: "center" }}>
              <span 
                style={{
                  backgroundColor: getStatusColor(room.status),
                  color: "white",
                  padding: "6px 12px",
                  borderRadius: "15px",
                  fontSize: "14px",
                  fontWeight: "bold"
                }}
              >
                {roomStatus.find(s => s.id === room.status)?.name}
              </span>
              <span 
                style={{
                  backgroundColor: "#6c757d",
                  color: "white",
                  padding: "6px 12px",
                  borderRadius: "15px",
                  fontSize: "14px"
                }}
              >
                {getTypeDisplay(room.type)}
              </span>
            </div>
          </div>
          
          <div style={{ display: "flex", gap: "10px" }}>
            <button
              onClick={() => onEdit(room)}
              style={{
                backgroundColor: "#ffc107",
                color: "#212529",
                border: "none",
                padding: "10px 20px",
                borderRadius: "5px",
                cursor: "pointer",
                fontSize: "14px"
              }}
            >
              Editar Sala
            </button>
            <button
              onClick={onClose}
              style={{
                backgroundColor: "#6c757d",
                color: "white",
                border: "none",
                padding: "10px 20px",
                borderRadius: "5px",
                cursor: "pointer",
                fontSize: "14px"
              }}
            >
              Fechar
            </button>
          </div>
        </div>

        {/* Informações Básicas */}
        <div style={{ 
          display: "grid", 
          gridTemplateColumns: "1fr 1fr", 
          gap: "30px", 
          marginBottom: "30px" 
        }}>
          <div>
            <h3 style={{ margin: "0 0 15px 0", color: "#495057" }}>Informações Gerais</h3>
            <div style={{ display: "grid", gap: "10px" }}>
              <div style={{ display: "flex", justifyContent: "space-between" }}>
                <strong>Cinema:</strong>
                <span>{room.cinema}</span>
              </div>
              <div style={{ display: "flex", justifyContent: "space-between" }}>
                <strong>Cidade:</strong>
                <span>{room.city}</span>
              </div>
              <div style={{ display: "flex", justifyContent: "space-between" }}>
                <strong>Tipo:</strong>
                <span>{getTypeDisplay(room.type)}</span>
              </div>
              <div style={{ display: "flex", justifyContent: "space-between" }}>
                <strong>Capacidade:</strong>
                <span>{room.capacity} lugares</span>
              </div>
              <div style={{ display: "flex", justifyContent: "space-between" }}>
                <strong>Layout:</strong>
                <span>{room.rows} fileiras × {room.seatsPerRow} assentos</span>
              </div>
            </div>
            
            {getTypeDescription(room.type) && (
              <div style={{ 
                marginTop: "15px", 
                padding: "10px", 
                backgroundColor: "#f8f9fa", 
                borderRadius: "5px",
                fontSize: "14px",
                color: "#6c757d"
              }}>
                <strong>Sobre este tipo de sala:</strong><br/>
                {getTypeDescription(room.type)}
              </div>
            )}
          </div>
          
          <div>
            <h3 style={{ margin: "0 0 15px 0", color: "#495057" }}>Ocupação Atual</h3>
            <div style={{ 
              display: "grid", 
              gridTemplateColumns: "repeat(3, 1fr)", 
              gap: "15px", 
              marginBottom: "15px" 
            }}>
              <div style={{ textAlign: "center", padding: "15px", backgroundColor: "#e3f2fd", borderRadius: "8px" }}>
                <div style={{ fontSize: "24px", fontWeight: "bold", color: "#1976d2" }}>
                  {room.seats.total}
                </div>
                <div style={{ fontSize: "12px", color: "#666" }}>Total</div>
              </div>
              <div style={{ textAlign: "center", padding: "15px", backgroundColor: "#e8f5e8", borderRadius: "8px" }}>
                <div style={{ fontSize: "24px", fontWeight: "bold", color: "#388e3c" }}>
                  {room.seats.available}
                </div>
                <div style={{ fontSize: "12px", color: "#666" }}>Disponíveis</div>
              </div>
              <div style={{ textAlign: "center", padding: "15px", backgroundColor: "#ffebee", borderRadius: "8px" }}>
                <div style={{ fontSize: "24px", fontWeight: "bold", color: "#d32f2f" }}>
                  {room.seats.occupied}
                </div>
                <div style={{ fontSize: "12px", color: "#666" }}>Ocupados</div>
              </div>
            </div>
            
            {/* Barra de Ocupação */}
            <div style={{ marginBottom: "15px" }}>
              <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "5px" }}>
                <span style={{ fontSize: "14px", fontWeight: "bold" }}>Taxa de Ocupação</span>
                <span style={{ fontSize: "14px", fontWeight: "bold" }}>{occupancyPercentage}%</span>
              </div>
              <div style={{ 
                width: "100%", 
                height: "10px", 
                backgroundColor: "#e9ecef", 
                borderRadius: "5px",
                overflow: "hidden"
              }}>
                <div style={{
                  width: `${occupancyPercentage}%`,
                  height: "100%",
                  backgroundColor: occupancyPercentage > 80 ? "#dc3545" : occupancyPercentage > 50 ? "#ffc107" : "#28a745",
                  transition: "width 0.3s ease"
                }}></div>
              </div>
            </div>
          </div>
        </div>

        {/* Recursos */}
        <div style={{ marginBottom: "30px" }}>
          <h3 style={{ margin: "0 0 15px 0", color: "#495057" }}>Recursos da Sala</h3>
          <div style={{ display: "flex", flexWrap: "wrap", gap: "8px" }}>
            {room.features.map((feature, index) => (
              <span 
                key={index}
                style={{
                  backgroundColor: "#007bff",
                  color: "white",
                  padding: "6px 12px",
                  borderRadius: "15px",
                  fontSize: "14px",
                  fontWeight: "500"
                }}
              >
                {feature}
              </span>
            ))}
          </div>
        </div>

        {/* Layout Visual dos Assentos */}
        <div>
          <h3 style={{ margin: "0 0 15px 0", color: "#495057" }}>Layout da Sala</h3>
          
          {/* Tela */}
          <div style={{ 
            textAlign: "center", 
            marginBottom: "25px",
            padding: "15px"
          }}>
            <div style={{
              width: "70%",
              height: "12px",
              background: "linear-gradient(45deg, #2c3e50, #34495e, #2c3e50)",
              margin: "0 auto 8px",
              borderRadius: "6px",
              boxShadow: "0 4px 8px rgba(0,0,0,0.3), inset 0 2px 4px rgba(255,255,255,0.1)",
              border: "2px solid #1a252f"
            }}></div>
            <div style={{ 
              fontSize: "16px", 
              color: "#495057", 
              fontWeight: "bold",
              letterSpacing: "2px",
              textShadow: "1px 1px 2px rgba(0,0,0,0.1)"
            }}>🎬 TELA 🎬</div>
          </div>
          
          {/* Assentos */}
          <div style={{ 
            display: "flex", 
            flexDirection: "column", 
            gap: "12px", 
            alignItems: "center",
            marginBottom: "25px",
            padding: "20px",
            backgroundColor: "#f8f9fa",
            borderRadius: "10px",
            border: "2px solid #e9ecef"
          }}>
            {seatLayout.map((row, rowIndex) => (
              <div key={rowIndex} style={{ display: "flex", gap: "8px", alignItems: "center" }}>
                <span style={{ 
                  width: "30px", 
                  fontSize: "14px", 
                  fontWeight: "bold", 
                  textAlign: "center",
                  color: "#495057",
                  backgroundColor: "#ffffff",
                  borderRadius: "4px",
                  padding: "4px",
                  border: "1px solid #dee2e6"
                }}>
                  {String.fromCharCode(65 + rowIndex)}
                </span>
                {row.map((seat, seatIndex) => {
                  // Adicionar espaço no meio para corredor
                  const isMiddle = seatIndex === Math.floor(row.length / 2);
                  return (
                    <React.Fragment key={seatIndex}>
                      {isMiddle && <div style={{ width: "20px" }}></div>}
                      <div
                        style={{
                          width: "28px",
                          height: "28px",
                          backgroundColor: seat.occupied ? "#dc3545" : "#28a745",
                          borderRadius: "6px",
                          display: "flex",
                          alignItems: "center",
                          justifyContent: "center",
                          fontSize: "11px",
                          color: "white",
                          fontWeight: "bold",
                          cursor: "pointer",
                          transition: "all 0.2s ease",
                          border: "2px solid",
                          borderColor: seat.occupied ? "#c82333" : "#218838",
                          boxShadow: "0 2px 4px rgba(0,0,0,0.1)"
                        }}
                        title={`Assento ${seat.number} - ${seat.occupied ? 'Ocupado' : 'Disponível'}`}
                        onMouseEnter={(e) => {
                          e.target.style.transform = "scale(1.1)";
                          e.target.style.boxShadow = "0 4px 8px rgba(0,0,0,0.2)";
                        }}
                        onMouseLeave={(e) => {
                          e.target.style.transform = "scale(1)";
                          e.target.style.boxShadow = "0 2px 4px rgba(0,0,0,0.1)";
                        }}
                      >
                        {seat.seat}
                      </div>
                    </React.Fragment>
                  );
                })}
                <span style={{ 
                  width: "30px", 
                  fontSize: "14px", 
                  fontWeight: "bold", 
                  textAlign: "center",
                  color: "#495057",
                  backgroundColor: "#ffffff",
                  borderRadius: "4px",
                  padding: "4px",
                  border: "1px solid #dee2e6"
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
            fontSize: "16px",
            fontWeight: "500",
            padding: "15px",
            backgroundColor: "#ffffff",
            borderRadius: "8px",
            border: "1px solid #dee2e6",
            boxShadow: "0 2px 4px rgba(0,0,0,0.05)"
          }}>
            <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
              <div style={{
                width: "24px",
                height: "24px",
                backgroundColor: "#28a745",
                borderRadius: "6px",
                border: "2px solid #218838",
                boxShadow: "0 2px 4px rgba(0,0,0,0.1)"
              }}></div>
              <span style={{ color: "#495057" }}>Disponível</span>
            </div>
            <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
              <div style={{
                width: "24px",
                height: "24px",
                backgroundColor: "#dc3545",
                borderRadius: "6px",
                border: "2px solid #c82333",
                boxShadow: "0 2px 4px rgba(0,0,0,0.1)"
              }}></div>
              <span style={{ color: "#495057" }}>Ocupado</span>
            </div>
            <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
              <div style={{
                width: "24px",
                height: "4px",
                backgroundColor: "#6c757d",
                borderRadius: "2px"
              }}></div>
              <span style={{ color: "#495057" }}>Corredor</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}