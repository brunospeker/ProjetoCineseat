import React, { useState, useEffect } from "react";
import RoomList from "./RoomList";
import RoomDetails from "./RoomDetails";
import { syncWithIngressoAPI, useIngressoAPI } from "../services/ingressoAPI";
import "../styles/Room.css";

function Room({ selectedCity }) {
    const [rooms, setRooms] = useState([]);
    const [selectedRoom, setSelectedRoom] = useState(null);
    const [showDetails, setShowDetails] = useState(false);
    const [syncing, setSyncing] = useState(false);
    const [initialLoad, setInitialLoad] = useState(true);
    const { loading: apiLoading, error: apiError } = useIngressoAPI();
    
    
    const getCityId = (cityName) => {
    const cityMap = {
      "Campinas": 1,
      "São Paulo": 2,
      "Rio de Janeiro": 3,
      "Belo Horizonte": 4,
      "Brasília": 5,
      "Salvador": 6,
      "Fortaleza": 7,
      "Recife": 8
    };
    return cityMap[cityName] || 1;
  };


    useEffect(() => {
        const loadRoomsFromAPI = async () => {
            setSyncing(true);
            try {
                const cityId = getCityId(selectedCity);
                console.log(`Carregando salas para ${selectedCity} (ID: ${cityId})`);
                const apiRooms = await syncWithIngressoAPI([], cityId);
                
                const filteredRooms = apiRooms.filter(room => 
                    room.city === selectedCity || 
                    (selectedCity === "São Paulo" && room.city === "Campinas")
                );
                setRooms(filteredRooms);
                console.log(`${filteredRooms.length} salas encontradas para ${selectedCity}`);
            } catch (error) {
                console.error('Erro ao carregar dados da API:', error);
                setRooms([]);
            } finally {
                setSyncing(false);
                setInitialLoad(false);
            }
        };

        if (selectedCity) {
            loadRoomsFromAPI();
        }
    }, [selectedCity, initialLoad]);

    const handleSelectRoom = (room) => {
        setSelectedRoom(room);
        setShowDetails(true);
    };

    const handleCloseDetails = () => {
        setShowDetails(false);
        setSelectedRoom(null);
    };

    const handleRefreshData = async () => {
        setSyncing(true);
        try {
            const cityId = getCityId(selectedCity);
            const apiRooms = await syncWithIngressoAPI([], cityId);
            const filteredRooms = apiRooms.filter(room => 
                room.city === selectedCity || 
                (selectedCity === "São Paulo" && room.city === "Campinas")
            );
            setRooms(filteredRooms);
            alert(`Dados atualizados! ${filteredRooms.length} salas carregadas para ${selectedCity}.`);
        } catch (error) {
            console.error('Erro ao atualizar dados:', error);
            alert('Erro ao carregar dados da API.');
        } finally {
            setSyncing(false);
        }
    };

    return (
        <>
            <div className="room-header">
                <h2>Salas de Cinema</h2>
                <div className="header-actions">
                    <button 
                        className="btn-secondary"
                        onClick={handleRefreshData}
                        disabled={syncing || apiLoading}
                    >
                        {syncing ? 'Carregando...' : 'Atualizar Dados'}
                    </button>
                </div>
            </div>
            {apiError && (
                <div className="api-error">
                    <p>⚠️ Erro na API: {apiError}</p>
                </div>
            )}
            {initialLoad && syncing && (
                <div className="loading-indicator">
                    <p>🔄 Carregando dados da API do Ingresso.com...</p>
                </div>
            )}
            
            <RoomList 
                rooms={rooms}
                onSelectRoom={handleSelectRoom}
            />
            
            {showDetails && (
                <RoomDetails 
                    room={selectedRoom}
                    onClose={handleCloseDetails}
                />
            )}
        </>
    );
}

export default Room;
