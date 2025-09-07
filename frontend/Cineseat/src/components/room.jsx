import React, { useState, useEffect } from "react";
import RoomList from "./RoomList";
import RoomCRUD from "./RoomCRUD";
import RoomDetails from "./RoomDetails";
import { rooms as initialRooms } from "../data/rooms";
import { syncWithIngressoAPI, useIngressoAPI } from "../services/ingressoAPI";
import "../styles/Room.css";

function Room(props) {
    const [rooms, setRooms] = useState(initialRooms);
    const [selectedRoom, setSelectedRoom] = useState(null);
    const [editingRoom, setEditingRoom] = useState(null);
    const [showCRUD, setShowCRUD] = useState(false);
    const [crudMode, setCrudMode] = useState("create");
    const [showDetails, setShowDetails] = useState(false);
    const [syncing, setSyncing] = useState(false);
    const [initialLoad, setInitialLoad] = useState(true);
    const { loading: apiLoading, error: apiError } = useIngressoAPI();

    
    useEffect(() => {
        const loadInitialData = async () => {
            if (initialLoad) {
                setSyncing(true);
                try {
                    const cityId = 1; // São Paulo
                    const syncedRooms = await syncWithIngressoAPI([], cityId);
                    if (syncedRooms.length > 0) {
                        setRooms(syncedRooms);
                    } else {
                      
                        setRooms(initialRooms);
                    }
                } catch (error) {
                    console.error('Erro ao carregar dados da API:', error);
                  
                    setRooms(initialRooms);
                } finally {
                    setSyncing(false);
                    setInitialLoad(false);
                }
            }
        };

        loadInitialData();
    }, [initialLoad]);

    const handleSelectRoom = (room) => {
        setSelectedRoom(room);
        setShowDetails(true);
    };

    const handleEditRoom = (room) => {
        setEditingRoom(room);
        setCrudMode("edit");
        setShowCRUD(true);
    };

    const handleCreateRoom = () => {
        setEditingRoom(null);
        setCrudMode("create");
        setShowCRUD(true);
    };

    const handleDeleteRoom = (roomId) => {
        if (window.confirm("Tem certeza que deseja excluir esta sala?")) {
            setRooms(prevRooms => prevRooms.filter(room => room.id !== roomId));
        }
    };

    const handleSaveRoom = (roomData) => {
        if (crudMode === "create") {
            setRooms(prevRooms => [...prevRooms, roomData]);
        } else {
            setRooms(prevRooms => 
                prevRooms.map(room => 
                    room.id === roomData.id ? roomData : room
                )
            );
        }
        setShowCRUD(false);
        setEditingRoom(null);
    };

    const handleCancelCRUD = () => {
        setShowCRUD(false);
        setEditingRoom(null);
    };

    const handleCloseDetails = () => {
        setShowDetails(false);
        setSelectedRoom(null);
    };

    const handleEditFromDetails = (room) => {
        setShowDetails(false);
        handleEditRoom(room);
    };

    const handleSyncWithAPI = async () => {
        setSyncing(true);
        try {
            const cityId = 1; 
            const syncedRooms = await syncWithIngressoAPI(rooms, cityId);
            setRooms(syncedRooms);
            const apiRoomsCount = syncedRooms.filter(r => r.source === 'ingresso-api').length;
            alert(`Sincronização concluída! ${apiRoomsCount} salas da API do Ingresso.com.`);
        } catch (error) {
            console.error('Erro na sincronização:', error);
            alert('Erro ao sincronizar com a API. Verifique sua conexão com a internet.');
        } finally {
            setSyncing(false);
        }
    };

    return (
        <>
            <div className="room-header">
                <h2>Gerenciamento de Salas</h2>
                <div className="header-actions">
                    <button 
                        className="btn-secondary"
                        onClick={handleSyncWithAPI}
                        disabled={syncing || apiLoading}
                    >
                        {syncing ? 'Sincronizando...' : 'Sincronizar com API'}
                    </button>
                    <button 
                         className="btn-primary"
                         onClick={() => {
                             setCrudMode("create");
                             setEditingRoom(null);
                             setShowCRUD(true);
                         }}
                     >
                         Nova Sala
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
                onEditRoom={handleEditRoom}
                onDeleteRoom={handleDeleteRoom}
                onCreateRoom={handleCreateRoom}
            />
            
            {showCRUD && (
                <RoomCRUD 
                    room={editingRoom}
                    mode={crudMode}
                    onSave={handleSaveRoom}
                    onCancel={handleCancelCRUD}
                />
            )}
            
            {showDetails && (
                <RoomDetails 
                    room={selectedRoom}
                    onClose={handleCloseDetails}
                    onEdit={handleEditFromDetails}
                />
            )}
        </>
    );
}

export default Room;
