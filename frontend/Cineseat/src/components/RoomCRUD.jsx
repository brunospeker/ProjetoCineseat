import React, { useState, useEffect } from "react";
import { roomTypes, roomStatus } from "../data/rooms";

export default function RoomCRUD({ room, onSave, onCancel, mode = "create" }) {
  const [formData, setFormData] = useState({
    name: "",
    type: "2D",
    capacity: 100,
    rows: 8,
    seatsPerRow: 12,
    cinema: "",
    city: "São Paulo",
    features: [],
    status: "active"
  });

  const [newFeature, setNewFeature] = useState("");
  const [errors, setErrors] = useState({});

  const availableFeatures = [
    "Ar condicionado",
    "Som Dolby Atmos",
    "Som Stereo",
    "Som IMAX",
    "Tela LASER",
    "Tela IMAX",
    "Poltronas reclináveis",
    "Serviço de mesa",
    "Efeitos 4D",
    "Acessibilidade",
    "Wi-Fi"
  ];

  useEffect(() => {
    if (room && mode === "edit") {
      setFormData({
        name: room.name || "",
        type: room.type || "2D",
        capacity: room.capacity || 100,
        rows: room.rows || 8,
        seatsPerRow: room.seatsPerRow || 12,
        cinema: room.cinema || "",
        city: room.city || "São Paulo",
        features: room.features || [],
        status: room.status || "active"
      });
    }
  }, [room, mode]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    
  
    if (name === "rows" || name === "seatsPerRow") {
      const rows = name === "rows" ? parseInt(value) : formData.rows;
      const seatsPerRow = name === "seatsPerRow" ? parseInt(value) : formData.seatsPerRow;
      setFormData(prev => ({
        ...prev,
        [name]: value,
        capacity: rows * seatsPerRow
      }));
    }
    
  
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: "" }));
    }
  };

  const addFeature = () => {
    if (newFeature.trim() && !formData.features.includes(newFeature.trim())) {
      setFormData(prev => ({
        ...prev,
        features: [...prev.features, newFeature.trim()]
      }));
      setNewFeature("");
    }
  };

  const removeFeature = (featureToRemove) => {
    setFormData(prev => ({
      ...prev,
      features: prev.features.filter(feature => feature !== featureToRemove)
    }));
  };

  const addPredefinedFeature = (feature) => {
    if (!formData.features.includes(feature)) {
      setFormData(prev => ({
        ...prev,
        features: [...prev.features, feature]
      }));
    }
  };

  const validateForm = () => {
    const newErrors = {};
    
    if (!formData.name.trim()) {
      newErrors.name = "Nome da sala é obrigatório";
    }
    
    if (!formData.cinema.trim()) {
      newErrors.cinema = "Nome do cinema é obrigatório";
    }
    
    if (formData.rows < 1 || formData.rows > 20) {
      newErrors.rows = "Número de fileiras deve estar entre 1 e 20";
    }
    
    if (formData.seatsPerRow < 1 || formData.seatsPerRow > 30) {
      newErrors.seatsPerRow = "Assentos por fileira deve estar entre 1 e 30";
    }
    
    if (formData.capacity < 10 || formData.capacity > 600) {
      newErrors.capacity = "Capacidade deve estar entre 10 e 600";
    }
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }
    
    const roomData = {
      ...formData,
      id: room?.id || Date.now(),
      seats: {
        total: formData.capacity,
        available: formData.capacity,
        occupied: 0
      }
    };
    
    onSave(roomData);
  };

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
        maxWidth: "600px",
        maxHeight: "90vh",
        overflow: "auto"
      }}>
        <h2 style={{ marginTop: 0 }}>
          {mode === "create" ? "Nova Sala" : "Editar Sala"}
        </h2>
        
        <form onSubmit={handleSubmit}>
          <div style={{ display: "grid", gap: "20px" }}>
    
            <div>
              <label style={{ display: "block", marginBottom: "5px", fontWeight: "bold" }}>
                Nome da Sala *
              </label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                style={{
                  width: "100%",
                  padding: "10px",
                  border: errors.name ? "2px solid #dc3545" : "1px solid #ddd",
                  borderRadius: "4px",
                  fontSize: "14px"
                }}
                placeholder="Ex: Sala 1 - LASER"
              />
              {errors.name && <span style={{ color: "#dc3545", fontSize: "12px" }}>{errors.name}</span>}
            </div>

    
            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "15px" }}>
              <div>
                <label style={{ display: "block", marginBottom: "5px", fontWeight: "bold" }}>
                  Tipo da Sala
                </label>
                <select
                  name="type"
                  value={formData.type}
                  onChange={handleInputChange}
                  style={{
                    width: "100%",
                    padding: "10px",
                    border: "1px solid #ddd",
                    borderRadius: "4px",
                    fontSize: "14px"
                  }}
                >
                  {roomTypes.map(type => (
                    <option key={type.id} value={type.id}>{type.name}</option>
                  ))}
                </select>
              </div>
              
              <div>
                <label style={{ display: "block", marginBottom: "5px", fontWeight: "bold" }}>
                  Status
                </label>
                <select
                  name="status"
                  value={formData.status}
                  onChange={handleInputChange}
                  style={{
                    width: "100%",
                    padding: "10px",
                    border: "1px solid #ddd",
                    borderRadius: "4px",
                    fontSize: "14px"
                  }}
                >
                  {roomStatus.map(status => (
                    <option key={status.id} value={status.id}>{status.name}</option>
                  ))}
                </select>
              </div>
            </div>

    
            <div style={{ display: "grid", gridTemplateColumns: "2fr 1fr", gap: "15px" }}>
              <div>
                <label style={{ display: "block", marginBottom: "5px", fontWeight: "bold" }}>
                  Cinema *
                </label>
                <input
                  type="text"
                  name="cinema"
                  value={formData.cinema}
                  onChange={handleInputChange}
                  style={{
                    width: "100%",
                    padding: "10px",
                    border: errors.cinema ? "2px solid #dc3545" : "1px solid #ddd",
                    borderRadius: "4px",
                    fontSize: "14px"
                  }}
                  placeholder="Ex: Cine Araújo Campo Limpo"
                />
                {errors.cinema && <span style={{ color: "#dc3545", fontSize: "12px" }}>{errors.cinema}</span>}
              </div>
              
              <div>
                <label style={{ display: "block", marginBottom: "5px", fontWeight: "bold" }}>
                  Cidade
                </label>
                <input
                  type="text"
                  name="city"
                  value={formData.city}
                  onChange={handleInputChange}
                  style={{
                    width: "100%",
                    padding: "10px",
                    border: "1px solid #ddd",
                    borderRadius: "4px",
                    fontSize: "14px"
                  }}
                />
              </div>
            </div>

    
            <div>
              <h4 style={{ margin: "0 0 10px 0" }}>Configuração de Assentos</h4>
              <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr 1fr", gap: "15px" }}>
                <div>
                  <label style={{ display: "block", marginBottom: "5px", fontWeight: "bold" }}>
                    Fileiras
                  </label>
                  <input
                    type="number"
                    name="rows"
                    value={formData.rows}
                    onChange={handleInputChange}
                    min="1"
                    max="20"
                    style={{
                      width: "100%",
                      padding: "10px",
                      border: errors.rows ? "2px solid #dc3545" : "1px solid #ddd",
                      borderRadius: "4px",
                      fontSize: "14px"
                    }}
                  />
                  {errors.rows && <span style={{ color: "#dc3545", fontSize: "12px" }}>{errors.rows}</span>}
                </div>
                
                <div>
                  <label style={{ display: "block", marginBottom: "5px", fontWeight: "bold" }}>
                    Assentos/Fileira
                  </label>
                  <input
                    type="number"
                    name="seatsPerRow"
                    value={formData.seatsPerRow}
                    onChange={handleInputChange}
                    min="1"
                    max="30"
                    style={{
                      width: "100%",
                      padding: "10px",
                      border: errors.seatsPerRow ? "2px solid #dc3545" : "1px solid #ddd",
                      borderRadius: "4px",
                      fontSize: "14px"
                    }}
                  />
                  {errors.seatsPerRow && <span style={{ color: "#dc3545", fontSize: "12px" }}>{errors.seatsPerRow}</span>}
                </div>
                
                <div>
                  <label style={{ display: "block", marginBottom: "5px", fontWeight: "bold" }}>
                    Capacidade Total
                  </label>
                  <input
                    type="number"
                    name="capacity"
                    value={formData.capacity}
                    onChange={handleInputChange}
                    style={{
                      width: "100%",
                      padding: "10px",
                      border: errors.capacity ? "2px solid #dc3545" : "1px solid #ddd",
                      borderRadius: "4px",
                      fontSize: "14px",
                      backgroundColor: "#f8f9fa"
                    }}
                    readOnly
                  />
                  {errors.capacity && <span style={{ color: "#dc3545", fontSize: "12px" }}>{errors.capacity}</span>}
                </div>
              </div>
            </div>

    
            <div>
              <h4 style={{ margin: "0 0 10px 0" }}>Recursos da Sala</h4>
              
      
              <div style={{ marginBottom: "15px" }}>
                <label style={{ display: "block", marginBottom: "5px", fontSize: "14px" }}>
                  Recursos Disponíveis:
                </label>
                <div style={{ display: "flex", flexWrap: "wrap", gap: "5px" }}>
                  {availableFeatures.map(feature => (
                    <button
                      key={feature}
                      type="button"
                      onClick={() => addPredefinedFeature(feature)}
                      disabled={formData.features.includes(feature)}
                      style={{
                        padding: "4px 8px",
                        border: "1px solid #ddd",
                        borderRadius: "12px",
                        fontSize: "12px",
                        backgroundColor: formData.features.includes(feature) ? "#e9ecef" : "white",
                        cursor: formData.features.includes(feature) ? "not-allowed" : "pointer",
                        opacity: formData.features.includes(feature) ? 0.6 : 1
                      }}
                    >
                      {feature}
                    </button>
                  ))}
                </div>
              </div>
              
      
              <div style={{ display: "flex", gap: "10px", marginBottom: "15px" }}>
                <input
                  type="text"
                  value={newFeature}
                  onChange={(e) => setNewFeature(e.target.value)}
                  placeholder="Adicionar recurso personalizado"
                  style={{
                    flex: 1,
                    padding: "8px",
                    border: "1px solid #ddd",
                    borderRadius: "4px",
                    fontSize: "14px"
                  }}
                  onKeyPress={(e) => e.key === "Enter" && (e.preventDefault(), addFeature())}
                />
                <button
                  type="button"
                  onClick={addFeature}
                  style={{
                    padding: "8px 16px",
                    backgroundColor: "#28a745",
                    color: "white",
                    border: "none",
                    borderRadius: "4px",
                    cursor: "pointer"
                  }}
                >
                  Adicionar
                </button>
              </div>
              
      
              {formData.features.length > 0 && (
                <div>
                  <label style={{ display: "block", marginBottom: "5px", fontSize: "14px" }}>
                    Recursos Selecionados:
                  </label>
                  <div style={{ display: "flex", flexWrap: "wrap", gap: "5px" }}>
                    {formData.features.map((feature, index) => (
                      <span
                        key={index}
                        style={{
                          backgroundColor: "#007bff",
                          color: "white",
                          padding: "4px 8px",
                          borderRadius: "12px",
                          fontSize: "12px",
                          display: "flex",
                          alignItems: "center",
                          gap: "5px"
                        }}
                      >
                        {feature}
                        <button
                          type="button"
                          onClick={() => removeFeature(feature)}
                          style={{
                            background: "none",
                            border: "none",
                            color: "white",
                            cursor: "pointer",
                            padding: "0",
                            fontSize: "14px",
                            lineHeight: 1
                          }}
                        >
                          ×
                        </button>
                      </span>
                    ))}
                  </div>
                </div>
              )}
            </div>
          </div>

  
          <div style={{ 
            display: "flex", 
            justifyContent: "flex-end", 
            gap: "10px", 
            marginTop: "30px",
            paddingTop: "20px",
            borderTop: "1px solid #eee"
          }}>
            <button
              type="button"
              onClick={onCancel}
              style={{
                padding: "10px 20px",
                border: "1px solid #ddd",
                borderRadius: "4px",
                backgroundColor: "white",
                cursor: "pointer",
                fontSize: "14px"
              }}
            >
              Cancelar
            </button>
            <button
              type="submit"
              style={{
                padding: "10px 20px",
                border: "none",
                borderRadius: "4px",
                backgroundColor: "#007bff",
                color: "white",
                cursor: "pointer",
                fontSize: "14px"
              }}
            >
              {mode === "create" ? "Criar Sala" : "Salvar Alterações"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}