// Serviço para integração com a API do Ingresso.com
// Documentação: https://api-content.ingresso.com/swagger/index.html

import { useState } from 'react';

const BASE_URL = 'https://api-content.ingresso.com';

// Função para fazer requisições à API
const apiRequest = async (endpoint, options = {}) => {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers
      },
      ...options
    });
    
    if (!response.ok) {
      throw new Error(`Erro na API: ${response.status} - ${response.statusText}`);
    }
    
    return await response.json();
  } catch (error) {
    console.error('Erro ao fazer requisição para a API:', error);
    throw error;
  }
};

// Serviços da API do Ingresso.com
export const ingressoAPI = {
  // Buscar cidades disponíveis
  getCities: async () => {
    try {
      return await apiRequest('/v0/templates/cities');
    } catch (error) {
      console.error('Erro ao buscar cidades:', error);
      return [];
    }
  },

  // Buscar cinemas por cidade
  getCinemasByCity: async (cityId) => {
    try {
      return await apiRequest(`/v0/templates/cities/${cityId}/theaters`);
    } catch (error) {
      console.error('Erro ao buscar cinemas:', error);
      return [];
    }
  },

  // Buscar filmes em cartaz
  getMovies: async (cityId) => {
    try {
      return await apiRequest(`/v0/templates/cities/${cityId}/movies/soon`);
    } catch (error) {
      console.error('Erro ao buscar filmes:', error);
      return [];
    }
  },

  // Buscar sessões de um filme
  getMovieSessions: async (movieId, cityId) => {
    try {
      return await apiRequest(`/v0/templates/movies/${movieId}/cities/${cityId}/theaters`);
    } catch (error) {
      console.error('Erro ao buscar sessões:', error);
      return [];
    }
  },

  // Buscar detalhes de um cinema específico
  getTheaterDetails: async (theaterId) => {
    try {
      return await apiRequest(`/v0/templates/theaters/${theaterId}`);
    } catch (error) {
      console.error('Erro ao buscar detalhes do cinema:', error);
      return null;
    }
  },

  // Buscar salas de um cinema específico
  getTheaterRooms: async (theaterId) => {
    try {
      const theaterDetails = await apiRequest(`/v0/templates/theaters/${theaterId}`);
      // A API pode não ter endpoint específico para salas, então extraímos das sessões
      return theaterDetails?.rooms || [];
    } catch (error) {
      console.error('Erro ao buscar salas do cinema:', error);
      return [];
    }
  }
};

// Função para converter dados da API para o formato usado no sistema
export const convertAPIDataToRooms = (apiData, cinemaName, cityName) => {
  if (!apiData || !Array.isArray(apiData)) {
    return [];
  }

  return apiData.map((room, index) => ({
    id: room.id || `api-${Date.now()}-${index}`,
    name: room.name || `Sala ${index + 1}`,
    type: determineRoomType(room.name || ''),
    capacity: room.capacity || 100,
    rows: Math.ceil(Math.sqrt(room.capacity || 100)),
    seatsPerRow: Math.floor(Math.sqrt(room.capacity || 100)),
    cinema: cinemaName,
    city: cityName,
    features: extractFeatures(room),
    status: 'active',
    seats: {
      total: room.capacity || 100,
      available: Math.floor((room.capacity || 100) * 0.8), // Estimativa
      occupied: Math.floor((room.capacity || 100) * 0.2)   // Estimativa
    },
    source: 'ingresso-api' // Identificar que veio da API
  }));
};

// Função auxiliar para determinar o tipo da sala baseado no nome
const determineRoomType = (roomName) => {
  const name = roomName.toLowerCase();
  
  if (name.includes('imax')) return 'IMAX';
  if (name.includes('4dx')) return '4DX';
  if (name.includes('vip')) return 'VIP';
  if (name.includes('laser')) return 'LASER';
  if (name.includes('dolby')) return 'DOLBY';
  if (name.includes('3d')) return '3D';
  
  return '2D';
};

// Função auxiliar para extrair recursos da sala
const extractFeatures = (roomData) => {
  const features = ['Ar condicionado']; // Recurso padrão
  
  if (roomData.name) {
    const name = roomData.name.toLowerCase();
    
    if (name.includes('imax')) {
      features.push('Som IMAX', 'Tela IMAX');
    }
    if (name.includes('4dx')) {
      features.push('Efeitos 4D', 'Som Dolby Atmos');
    }
    if (name.includes('vip')) {
      features.push('Poltronas reclináveis', 'Serviço de mesa');
    }
    if (name.includes('laser')) {
      features.push('Tela LASER', 'Som Dolby Atmos');
    }
    if (name.includes('dolby')) {
      features.push('Som Dolby Atmos');
    }
  }
  
  return features;
};

// Hook personalizado para usar a API do Ingresso.com
export const useIngressoAPI = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchWithLoading = async (apiCall) => {
    setLoading(true);
    setError(null);
    
    try {
      const result = await apiCall();
      return result;
    } catch (err) {
      setError(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return {
    loading,
    error,
    fetchCities: () => fetchWithLoading(ingressoAPI.getCities),
    fetchCinemas: (cityId) => fetchWithLoading(() => ingressoAPI.getCinemasByCity(cityId)),
    fetchMovies: (cityId) => fetchWithLoading(() => ingressoAPI.getMovies(cityId)),
    fetchTheaterRooms: (theaterId) => fetchWithLoading(() => ingressoAPI.getTheaterRooms(theaterId))
  };
};

// Função para sincronizar dados da API com dados locais
export const syncWithIngressoAPI = async (localRooms, cityId) => {
  try {
    console.log('Iniciando sincronização com API do Ingresso.com...');
    
    // Buscar cinemas da cidade
    const cinemas = await ingressoAPI.getCinemasByCity(cityId);
    
    if (!cinemas || cinemas.length === 0) {
      console.log('Nenhum cinema encontrado na API para esta cidade.');
      return localRooms;
    }
    
    const apiRooms = [];
    
    // Para cada cinema, buscar suas salas
    for (const cinema of cinemas.slice(0, 3)) { // Limitar a 3 cinemas para não sobrecarregar
      try {
        const rooms = await ingressoAPI.getTheaterRooms(cinema.id);
        const convertedRooms = convertAPIDataToRooms(rooms, cinema.name, cinema.city);
        apiRooms.push(...convertedRooms);
      } catch (error) {
        console.warn(`Erro ao buscar salas do cinema ${cinema.name}:`, error);
      }
    }
    
    console.log(`Sincronização concluída. ${apiRooms.length} salas encontradas na API.`);
    
    // Combinar dados locais com dados da API
    const localRoomsFiltered = localRooms.filter(room => room.source !== 'ingresso-api');
    return [...localRoomsFiltered, ...apiRooms];
    
  } catch (error) {
    console.error('Erro durante sincronização com API:', error);
    return localRooms; // Retornar dados locais em caso de erro
  }
};

export default ingressoAPI;