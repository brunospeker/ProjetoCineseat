import React, { useState } from "react";
import { rooms, roomTypes, roomStatus } from "../data/rooms";

export default function RoomList({ rooms, onSelectRoom, onEditRoom, onDeleteRoom, onCreateRoom }) {
  const [filterType, setFilterType] = useState("");
  const [filterStatus, setFilterStatus] = useState("");
  const [searchTerm, setSearchTerm] = useState("");

  const filteredRooms = rooms.filter(room => {
    const matchesType = !filterType || room.type === filterType;
    const matchesStatus = !filterStatus || room.status === filterStatus;
    const matchesSearch = !searchTerm || 
      room.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      room.cinema.toLowerCase().includes(searchTerm.toLowerCase());
    
    return matchesType && matchesStatus && matchesSearch;
  });

  const getStatusColor = (status) => {
    const statusObj = roomStatus.find(s => s.id === status);
    return statusObj ? statusObj.color : "gray";
  };

  const getTypeDisplay = (type) => {
    const typeObj = roomTypes.find(t => t.id === type);
    return typeObj ? typeObj.name : type;
  };

  return (
    <div style={{ padding: "20px" }}>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "20px" }}>
        <h2>Gerenciamento de Salas</h2>
        <button 
          onClick={onCreateRoom}
          style={{
            backgroundColor: "#007bff",
            color: "white",
            border: "none",
            padding: "10px 20px",
            borderRadius: "5px",
            cursor: "pointer"
          }}
        >
          + Nova Sala
        </button>
      </div>

      {/* Filtros */}
      <div style={{ 
        display: "flex", 
        gap: "15px", 
        marginBottom: "20px", 
        padding: "15px", 
        backgroundColor: "#f8f9fa", 
        borderRadius: "5px" 
      }}>
        <input
          type="text"
          placeholder="Buscar por nome ou cinema..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          style={{
            padding: "8px 12px",
            border: "1px solid #ddd",
            borderRadius: "4px",
            flex: 1
          }}
        />
        
        <select
          value={filterType}
          onChange={(e) => setFilterType(e.target.value)}
          style={{
            padding: "8px 12px",
            border: "1px solid #ddd",
            borderRadius: "4px"
          }}
        >
          <option value="">Todos os tipos</option>
          {roomTypes.map(type => (
            <option key={type.id} value={type.id}>{type.name}</option>
          ))}
        </select>

        <select
          value={filterStatus}
          onChange={(e) => setFilterStatus(e.target.value)}
          style={{
            padding: "8px 12px",
            border: "1px solid #ddd",
            borderRadius: "4px"
          }}
        >
          <option value="">Todos os status</option>
          {roomStatus.map(status => (
            <option key={status.id} value={status.id}>{status.name}</option>
          ))}
        </select>
      </div>

      {/* Lista de Salas */}
      <div style={{ display: "grid", gap: "15px" }}>
        {filteredRooms.length === 0 ? (
          <p style={{ textAlign: "center", color: "#666", padding: "40px" }}>
            Nenhuma sala encontrada com os filtros aplicados.
          </p>
        ) : (
          filteredRooms.map(room => (
            <div 
              key={room.id} 
              style={{
                border: "1px solid #ddd",
                borderRadius: "8px",
                padding: "20px",
                backgroundColor: "white",
                boxShadow: "0 2px 4px rgba(0,0,0,0.1)"
              }}
            >
              <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
                <div style={{ flex: 1 }}>
                  <div style={{ display: "flex", alignItems: "center", gap: "10px", marginBottom: "10px" }}>
                    <h3 style={{ margin: 0, color: "#333" }}>{room.name}</h3>
                    <span 
                      style={{
                        backgroundColor: getStatusColor(room.status),
                        color: "white",
                        padding: "4px 8px",
                        borderRadius: "12px",
                        fontSize: "12px",
                        fontWeight: "bold"
                      }}
                    >
                      {roomStatus.find(s => s.id === room.status)?.name}
                    </span>
                    <span 
                      style={{
                        backgroundColor: "#6c757d",
                        color: "white",
                        padding: "4px 8px",
                        borderRadius: "12px",
                        fontSize: "12px"
                      }}
                    >
                      {getTypeDisplay(room.type)}
                    </span>
                  </div>
                  
                  <p style={{ margin: "5px 0", color: "#666" }}>
                    <strong>Cinema:</strong> {room.cinema}
                  </p>
                  <p style={{ margin: "5px 0", color: "#666" }}>
                    <strong>Capacidade:</strong> {room.capacity} lugares ({room.rows} fileiras × {room.seatsPerRow} assentos)
                  </p>
                  {room.source && (
                    <span 
                      style={{
                        backgroundColor: room.source === 'ingresso-api' ? '#007bff' : '#28a745',
                        color: "white",
                        padding: "4px 8px",
                        borderRadius: "12px",
                        fontSize: "12px",
                        fontWeight: "bold",
                        marginLeft: "10px"
                      }}
                    >
                      {room.source === 'ingresso-api' ? 'API' : 'Local'}
                    </span>
                  )}
                  <p style={{ margin: "5px 0", color: "#666" }}>
                    <strong>Disponibilidade:</strong> {room.seats.available} livres / {room.seats.occupied} ocupados
                  </p>
                  
                  <div style={{ marginTop: "10px" }}>
                    <strong>Recursos:</strong>
                    <div style={{ display: "flex", gap: "5px", marginTop: "5px", flexWrap: "wrap" }}>
                      {room.features.map((feature, index) => (
                        <span 
                          key={index}
                          style={{
                            backgroundColor: "#e9ecef",
                            padding: "2px 6px",
                            borderRadius: "8px",
                            fontSize: "12px",
                            color: "#495057"
                          }}
                        >
                          {feature}
                        </span>
                      ))}
                    </div>
                  </div>
                </div>
                
                <div style={{ display: "flex", gap: "10px", marginLeft: "20px" }}>
                  <button
                    onClick={() => onSelectRoom(room)}
                    style={{
                      backgroundColor: "#28a745",
                      color: "white",
                      border: "none",
                      padding: "8px 16px",
                      borderRadius: "4px",
                      cursor: "pointer",
                      fontSize: "14px"
                    }}
                  >
                    Ver Detalhes
                  </button>
                  <button
                    onClick={() => onEditRoom(room)}
                    style={{
                      backgroundColor: "#ffc107",
                      color: "#212529",
                      border: "none",
                      padding: "8px 16px",
                      borderRadius: "4px",
                      cursor: "pointer",
                      fontSize: "14px"
                    }}
                  >
                    Editar
                  </button>
                  <button
                    onClick={() => onDeleteRoom(room.id)}
                    style={{
                      backgroundColor: "#dc3545",
                      color: "white",
                      border: "none",
                      padding: "8px 16px",
                      borderRadius: "4px",
                      cursor: "pointer",
                      fontSize: "14px"
                    }}
                  >
                    Excluir
                  </button>
                </div>
              </div>
            </div>
          ))
        )}
      </div>

      {/* Estatísticas */}
      <div style={{ 
        marginTop: "30px", 
        padding: "20px", 
        backgroundColor: "#f8f9fa", 
        borderRadius: "8px" 
      }}>
        <h4 style={{ margin: "0 0 15px 0" }}>Estatísticas</h4>
        <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))", gap: "15px" }}>
          <div style={{ textAlign: "center" }}>
            <div style={{ fontSize: "24px", fontWeight: "bold", color: "#007bff" }}>
              {rooms.length}
            </div>
            <div style={{ color: "#666" }}>Total de Salas</div>
          </div>
          <div style={{ textAlign: "center" }}>
            <div style={{ fontSize: "24px", fontWeight: "bold", color: "#28a745" }}>
              {rooms.filter(r => r.status === "active").length}
            </div>
            <div style={{ color: "#666" }}>Salas Ativas</div>
          </div>
          <div style={{ textAlign: "center" }}>
            <div style={{ fontSize: "24px", fontWeight: "bold", color: "#ffc107" }}>
              {rooms.filter(r => r.status === "maintenance").length}
            </div>
            <div style={{ color: "#666" }}>Em Manutenção</div>
          </div>
          <div style={{ textAlign: "center" }}>
            <div style={{ fontSize: "24px", fontWeight: "bold", color: "#6c757d" }}>
              {rooms.reduce((sum, room) => sum + room.capacity, 0)}
            </div>
            <div style={{ color: "#666" }}>Capacidade Total</div>
          </div>
        </div>
      </div>
    </div>
  );
}