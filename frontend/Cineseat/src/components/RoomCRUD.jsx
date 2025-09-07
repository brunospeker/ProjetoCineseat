import React, { useState, useEffect } from "react";
import { roomTypes, roomStatus } from "../data/rooms";
import styles from "./RoomCRUD.module.css";

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
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h2 className={styles.title}>
          {mode === "create" ? "Nova Sala" : "Editar Sala"}
        </h2>
        
        <form onSubmit={handleSubmit}>
          <div className={styles.formGrid}>
    
            <div>
              <label className={styles.label}>
                Nome da Sala *
              </label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                className={errors.name ? styles.inputError : styles.input}
                placeholder="Ex: Sala 1 - LASER"
              />
              {errors.name && <span className={styles.errorText}>{errors.name}</span>}
            </div>

    
            <div className={styles.gridTwoColumns}>
              <div>
                <label className={styles.label}>
                  Tipo da Sala
                </label>
                <select
                  name="type"
                  value={formData.type}
                  onChange={handleInputChange}
                  className={styles.input}
                >
                  {roomTypes.map(type => (
                    <option key={type.id} value={type.id}>{type.name}</option>
                  ))}
                </select>
              </div>
              
              <div>
                <label className={styles.label}>
                  Status
                </label>
                <select
                  name="status"
                  value={formData.status}
                  onChange={handleInputChange}
                  className={styles.input}
                >
                  {roomStatus.map(status => (
                    <option key={status.id} value={status.id}>{status.name}</option>
                  ))}
                </select>
              </div>
            </div>

    
            <div className={styles.gridCinemaCity}>
              <div>
                <label className={styles.label}>
                  Cinema *
                </label>
                <input
                  type="text"
                  name="cinema"
                  value={formData.cinema}
                  onChange={handleInputChange}
                  className={errors.cinema ? styles.inputError : styles.input}
                  placeholder="Ex: Cine Araújo Campo Limpo"
                />
                {errors.cinema && <span className={styles.errorText}>{errors.cinema}</span>}
              </div>
              
              <div>
                <label className={styles.label}>
                  Cidade
                </label>
                <input
                  type="text"
                  name="city"
                  value={formData.city}
                  onChange={handleInputChange}
                  className={styles.input}
                />
              </div>
            </div>

    
            <div>
              <h4 className={styles.sectionTitle}>Configuração de Assentos</h4>
              <div className={styles.gridThreeColumns}>
                <div>
                  <label className={styles.label}>
                    Fileiras
                  </label>
                  <input
                    type="number"
                    name="rows"
                    value={formData.rows}
                    onChange={handleInputChange}
                    min="1"
                    max="20"
                    className={errors.rows ? styles.inputError : styles.input}
                  />
                  {errors.rows && <span className={styles.errorText}>{errors.rows}</span>}
                </div>
                
                <div>
                  <label className={styles.label}>
                    Assentos/Fileira
                  </label>
                  <input
                    type="number"
                    name="seatsPerRow"
                    value={formData.seatsPerRow}
                    onChange={handleInputChange}
                    min="1"
                    max="30"
                    className={errors.seatsPerRow ? styles.inputError : styles.input}
                  />
                  {errors.seatsPerRow && <span className={styles.errorText}>{errors.seatsPerRow}</span>}
                </div>
                
                <div>
                  <label className={styles.label}>
                    Capacidade Total
                  </label>
                  <input
                    type="number"
                    name="capacity"
                    value={formData.capacity}
                    onChange={handleInputChange}
                    className={styles.inputReadonly}
                    readOnly
                  />
                  {errors.capacity && <span className={styles.errorText}>{errors.capacity}</span>}
                </div>
              </div>
            </div>

    
            <div>
              <h4 className={styles.sectionTitle}>Recursos da Sala</h4>
              
      
              <div className={styles.featuresContainer}>
                <label className={styles.labelSmall}>
                  Recursos Disponíveis:
                </label>
                <div className={styles.featuresGrid}>
                  {availableFeatures.map(feature => (
                    <button
                      key={feature}
                      type="button"
                      onClick={() => addPredefinedFeature(feature)}
                      disabled={formData.features.includes(feature)}
                      className={formData.features.includes(feature) ? styles.featureButtonDisabled : styles.featureButton}
                    >
                      {feature}
                    </button>
                  ))}
                </div>
              </div>
              
      
              <div className={styles.addFeatureContainer}>
                <input
                  type="text"
                  value={newFeature}
                  onChange={(e) => setNewFeature(e.target.value)}
                  placeholder="Adicionar recurso personalizado"
                  className={styles.addFeatureInput}
                  onKeyPress={(e) => e.key === "Enter" && (e.preventDefault(), addFeature())}
                />
                <button
                  type="button"
                  onClick={addFeature}
                  className={styles.addFeatureButton}
                >
                  Adicionar
                </button>
              </div>
              
      
              {formData.features.length > 0 && (
                <div>
                  <label className={styles.labelSmall}>
                    Recursos Selecionados:
                  </label>
                  <div className={styles.selectedFeaturesGrid}>
                    {formData.features.map((feature, index) => (
                      <span
                        key={index}
                        className={styles.selectedFeature}
                      >
                        {feature}
                        <button
                          type="button"
                          onClick={() => removeFeature(feature)}
                          className={styles.removeFeatureButton}
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

  
          <div className={styles.buttonContainer}>
            <button
              type="button"
              onClick={onCancel}
              className={styles.cancelButton}
            >
              Cancelar
            </button>
            <button
              type="submit"
              className={styles.submitButton}
            >
              {mode === "create" ? "Criar Sala" : "Salvar Alterações"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}