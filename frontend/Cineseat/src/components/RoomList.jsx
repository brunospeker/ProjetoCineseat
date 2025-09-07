import React, { useState } from "react";
import { rooms, roomTypes, roomStatus } from "../data/rooms";
import styles from './RoomList.module.css';

export default function RoomList({ rooms, onSelectRoom }) {
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
    <div className={styles.container}>
      <div className={styles.header}>
        <h2>Lista de Salas</h2>
      </div>

      <div className={styles.filtersContainer}>
        <input
          type="text"
          placeholder="Buscar por nome ou cinema..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className={styles.searchInput}
        />
        
        <select
          value={filterType}
          onChange={(e) => setFilterType(e.target.value)}
          className={styles.filterSelect}
        >
          <option value="">Todos os tipos</option>
          {roomTypes.map(type => (
            <option key={type.id} value={type.id}>{type.name}</option>
          ))}
        </select>

        <select
          value={filterStatus}
          onChange={(e) => setFilterStatus(e.target.value)}
          className={styles.filterSelect}
        >
          <option value="">Todos os status</option>
          {roomStatus.map(status => (
            <option key={status.id} value={status.id}>{status.name}</option>
          ))}
        </select>
      </div>


      <div className={styles.roomsGrid}>
        {filteredRooms.length === 0 ? (
          <p className={styles.noRoomsMessage}>
            Nenhuma sala encontrada com os filtros aplicados.
          </p>
        ) : (
          filteredRooms.map(room => (
            <div 
              key={room.id} 
              className={styles.roomCard}
            >
              <div className={styles.roomCardHeader}>
                <div className={styles.roomCardContent}>
                  <div className={styles.roomTitleRow}>
                    <h3 className={styles.roomTitle}>{room.name}</h3>
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
                  
                  <p className={styles.roomInfo}>
                    <strong>Cinema:</strong> {room.cinema}
                  </p>
                  <p className={styles.roomInfo}>
                    <strong>Capacidade:</strong> {room.capacity} lugares ({room.rows} fileiras × {room.seatsPerRow} assentos)
                  </p>
                  {room.source && (
                    <span 
                      className={`${styles.sourceBadge} ${room.source === 'ingresso-api' ? styles.sourceBadgeApi : styles.sourceBadgeLocal}`}
                    >
                      {room.source === 'ingresso-api' ? 'API' : 'Local'}
                    </span>
                  )}
                  <p className={styles.roomInfo}>
                    <strong>Disponibilidade:</strong> {room.seats.available} livres / {room.seats.occupied} ocupados
                  </p>
                  
                  <div className={styles.featuresContainer}>
                    <strong>Recursos:</strong>
                    <div className={styles.featuresGrid}>
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
                  
                  {room.schedule && (
                    <div className={styles.scheduleContainer}>
                      <strong>Horários de Hoje:</strong>
                      <div className={styles.scheduleGrid}>
                        {room.schedule.map((session, index) => (
                          <div 
                            key={index}
                            className={`${styles.sessionCard} ${session.available ? styles.sessionAvailable : styles.sessionUnavailable}`}
                          >
                            <div className={`${styles.sessionTime} ${session.available ? styles.sessionTimeAvailable : styles.sessionTimeUnavailable}`}>
                              {session.time}
                            </div>
                            <div className={`${styles.sessionMovie} ${session.available ? styles.sessionTimeAvailable : styles.sessionTimeUnavailable}`}>
                              {session.movie}
                            </div>
                            <div className={`${styles.sessionStatus} ${session.available ? styles.sessionTimeAvailable : styles.sessionTimeUnavailable}`}>
                              {session.available ? "Disponível" : "Esgotado"}
                            </div>
                          </div>
                        ))}
                      </div>
                    </div>
                  )}
                </div>
                
                <div className={styles.roomActions}>
                  <button
                    onClick={() => onSelectRoom(room)}
                    className={styles.detailsButton}
                  >
                    Ver Detalhes
                  </button>
                </div>
              </div>
            </div>
          ))
        )}
      </div>


      <div className={styles.statisticsContainer}>
        <h4 className={styles.statisticsTitle}>Estatísticas</h4>
        <div className={styles.statisticsGrid}>
          <div className={styles.statisticsItem}>
            <div className={`${styles.statisticsNumber} ${styles.statisticsNumberTotal}`}>
              {rooms.length}
            </div>
            <div className={styles.statisticsLabel}>Total de Salas</div>
          </div>
          <div className={styles.statisticsItem}>
            <div className={`${styles.statisticsNumber} ${styles.statisticsNumberActive}`}>
              {rooms.filter(r => r.status === "active").length}
            </div>
            <div className={styles.statisticsLabel}>Salas Ativas</div>
          </div>
          <div className={styles.statisticsItem}>
            <div className={`${styles.statisticsNumber} ${styles.statisticsNumberMaintenance}`}>
              {rooms.filter(r => r.status === "maintenance").length}
            </div>
            <div className={styles.statisticsLabel}>Em Manutenção</div>
          </div>
          <div className={styles.statisticsItem}>
            <div className={`${styles.statisticsNumber} ${styles.statisticsNumberCapacity}`}>
              {rooms.reduce((sum, room) => sum + room.capacity, 0)}
            </div>
            <div className={styles.statisticsLabel}>Capacidade Total</div>
          </div>
        </div>
      </div>
    </div>
  );
}