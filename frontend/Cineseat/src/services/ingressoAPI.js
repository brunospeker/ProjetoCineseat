import { useState } from 'react';

const BASE_URL = 'https://api-content.ingresso.com';

const apiRequest = async (endpoint, options = {}) => {
  const fullUrl = `${BASE_URL}${endpoint}`;
  console.log('Fazendo requisição para:', fullUrl);
  
  try {
    const response = await fetch(fullUrl, {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers
      },
      ...options
    });
    
    console.log('Status da resposta:', response.status, response.statusText);
    
    if (!response.ok) {
      const errorText = await response.text();
      console.error('Erro na resposta da API:', errorText);
      throw new Error(`Erro na API: ${response.status} - ${response.statusText}`);
    }
    
    const data = await response.json();
    console.log('Dados recebidos da API:', data);
    return data;
  } catch (error) {
    console.error('Erro ao fazer requisição para a API:', error);
    console.error('URL tentada:', fullUrl);
    throw error;
  }
};


export const ingressoAPI = {
  getCities: async () => {
    try {
      return await apiRequest('/v0/templates/cities');
    } catch (error) {
      console.error('Erro ao buscar cidades:', error);
      console.warn('API externa indisponível, usando dados mock temporários');
      return [
        { id: 1, name: "Campinas", state: "SP" },
        { id: 2, name: "São Paulo", state: "SP" },
        { id: 3, name: "Rio de Janeiro", state: "RJ" },
        { id: 4, name: "Belo Horizonte", state: "MG" },
        { id: 5, name: "Brasília", state: "DF" },
        { id: 6, name: "Salvador", state: "BA" },
        { id: 7, name: "Fortaleza", state: "CE" },
        { id: 8, name: "Recife", state: "PE" }
      ];
    }
  },


  getCinemasByCity: async (cityId) => {
    try {
      return await apiRequest(`/v0/templates/cities/${cityId}/theaters`);
    } catch (error) {
      console.error('Erro ao buscar cinemas:', error);
      console.warn('API externa indisponível, usando dados mock temporários');
      const mockDataByCity = {
        1: [
          {
            id: 1,
            name: "Cinemark Shopping Iguatemi",
            address: "Av. Iguatemi, 777 - Vila Brandina, Campinas - SP",
            city: "Campinas",
            rooms: [
              { id: 1, name: "Sala 1", capacity: 150, type: "standard" },
              { id: 2, name: "Sala 2", capacity: 200, type: "premium" },
              { id: 3, name: "Sala IMAX", capacity: 300, type: "imax" }
            ]
          }
        ],
        2: [
           {
             id: 4,
             name: "Cinemark Eldorado",
             address: "Av. Rebouças, 3970 - Pinheiros, São Paulo - SP",
             city: "São Paulo",
             rooms: [
               { id: "sp-eldorado-1", name: "Sala Premium 1", capacity: 180, type: "premium" },
               { id: "sp-eldorado-2", name: "Sala IMAX", capacity: 350, type: "imax" },
               { id: "sp-eldorado-3", name: "Sala VIP", capacity: 90, type: "vip" }
             ]
           },
           {
             id: 5,
             name: "UCI Anália Franco",
             address: "Av. Regente Feijó, 1739 - Anália Franco, São Paulo - SP",
             city: "São Paulo",
             rooms: [
               { id: "sp-analia-1", name: "Sala A", capacity: 140, type: "standard" },
               { id: "sp-analia-2", name: "Sala B", capacity: 200, type: "premium" },
               { id: "sp-analia-3", name: "Sala 4DX", capacity: 100, type: "4dx" }
             ]
           }
         ],
         3: [
           {
             id: 6,
             name: "Cinemark Barra Shopping",
             address: "Av. das Américas, 4666 - Barra da Tijuca, Rio de Janeiro - RJ",
             city: "Rio de Janeiro",
             rooms: [
               { id: "rj-barra-1", name: "Sala Ocean 1", capacity: 160, type: "standard" },
               { id: "rj-barra-2", name: "Sala Ocean IMAX", capacity: 400, type: "imax" },
               { id: "rj-barra-3", name: "Sala VIP Copacabana", capacity: 85, type: "vip" }
             ]
           },
           {
             id: 7,
             name: "UCI New York City Center",
             address: "Av. das Américas, 5150 - Barra da Tijuca, Rio de Janeiro - RJ",
             city: "Rio de Janeiro",
             rooms: [
               { id: "rj-nyc-1", name: "Sala Carioca 1", capacity: 130, type: "standard" },
               { id: "rj-nyc-2", name: "Sala Carioca Premium", capacity: 190, type: "premium" },
               { id: "rj-nyc-3", name: "Sala 4DX Rio", capacity: 95, type: "4dx" }
             ]
           }
         ],
         4: [
           {
             id: 8,
             name: "Cinemark Diamond Mall",
             address: "Av. Olegário Maciel, 1600 - Santo Agostinho, Belo Horizonte - MG",
             city: "Belo Horizonte",
             rooms: [
               { id: "bh-diamond-1", name: "Sala Minas 1", capacity: 145, type: "standard" },
               { id: "bh-diamond-2", name: "Sala Minas Premium", capacity: 175, type: "premium" },
               { id: "bh-diamond-3", name: "Sala IMAX BH", capacity: 320, type: "imax" }
             ]
           }
         ]
      };
      
      return mockDataByCity[cityId] || mockDataByCity[1];
    }
  },


  getMovies: async (cityId) => {
    try {
      return await apiRequest(`/v0/templates/cities/${cityId}/movies/soon`);
    } catch (error) {
      console.error('Erro ao buscar filmes:', error);
      return [];
    }
  },


  getMovieSessions: async (movieId, cityId) => {
    try {
      return await apiRequest(`/v0/templates/movies/${movieId}/cities/${cityId}/theaters`);
    } catch (error) {
      console.error('Erro ao buscar sessões:', error);
      return [];
    }
  },


  getTheaterDetails: async (theaterId) => {
    try {
      return await apiRequest(`/v0/templates/theaters/${theaterId}`);
    } catch (error) {
      console.error('Erro ao buscar detalhes do cinema:', error);
      console.warn('API externa indisponível, usando dados mock temporários');
      const mockTheaters = {
        1: {
          id: 1,
          name: "Cinemark Shopping Iguatemi",
          address: "Av. Iguatemi, 777 - Vila Brandina, Campinas - SP",
          city: "Campinas",
          state: "SP"
        },
        2: {
          id: 2,
          name: "UCI Cinemas Campinas",
          address: "Rua Jacy Teixeira de Camargo, 940 - Cidade Universitária, Campinas - SP",
          city: "Campinas",
          state: "SP"
        },
        3: {
          id: 3,
          name: "Moviecom Campinas Shopping",
          address: "Rua Jacy Teixeira de Camargo, 940 - Cidade Universitária, Campinas - SP",
          city: "Campinas",
          state: "SP"
        }
      };
      return mockTheaters[theaterId] || mockTheaters[1];
    }
  },


  getTheaterRooms: async (theaterId) => {
    try {
      const theaterDetails = await apiRequest(`/v0/templates/theaters/${theaterId}`);

      return theaterDetails?.rooms || [];
    } catch (error) {
      console.error('Erro ao buscar salas do cinema:', error);
      console.warn('API externa indisponível, usando dados mock temporários');
      const generateMockSchedule = (roomId) => {
         const schedules = {
           1: [{ time: '14:00', movie: 'Homem-Aranha: Através do Aranhaverso', available: true }, { time: '16:30', movie: 'Avatar: O Caminho da Água', available: true }, { time: '19:00', movie: 'Top Gun: Maverick', available: false }, { time: '21:30', movie: 'Pantera Negra: Wakanda Para Sempre', available: true }],
           2: [{ time: '13:30', movie: 'Minions 2: A Origem de Gru', available: true }, { time: '15:45', movie: 'Thor: Amor e Trovão', available: true }, { time: '18:15', movie: 'Doutor Estranho no Multiverso da Loucura', available: true }, { time: '21:00', movie: 'Jurassic World: Domínio', available: false }],
           3: [{ time: '14:30', movie: 'Lightyear', available: true }, { time: '17:00', movie: 'Morbius', available: false }, { time: '19:30', movie: 'Sonic 2: O Filme', available: true }, { time: '22:00', movie: 'Animais Fantásticos: Os Segredos de Dumbledore', available: true }]
         };
         return schedules[roomId] || schedules[1];
       };

       const mockRooms = {
         1: [
           { id: `theater-1-room-1`, name: "Sala 1", capacity: 150, type: "standard", rows: 10, seatsPerRow: 15, schedule: generateMockSchedule(1) },
           { id: `theater-1-room-2`, name: "Sala 2", capacity: 200, type: "premium", rows: 12, seatsPerRow: 17, schedule: generateMockSchedule(2) },
           { id: `theater-1-room-3`, name: "Sala IMAX", capacity: 300, type: "imax", rows: 15, seatsPerRow: 20, schedule: generateMockSchedule(3) }
         ],
         2: [
           { id: `theater-2-room-1`, name: "Sala A", capacity: 120, type: "standard", rows: 8, seatsPerRow: 15, schedule: generateMockSchedule(1) },
           { id: `theater-2-room-2`, name: "Sala B", capacity: 180, type: "premium", rows: 10, seatsPerRow: 18, schedule: generateMockSchedule(2) },
           { id: `theater-2-room-3`, name: "Sala VIP", capacity: 80, type: "vip", rows: 6, seatsPerRow: 14, schedule: generateMockSchedule(3) }
         ],
         3: [
           { id: `theater-3-room-1`, name: "Sala 1", capacity: 100, type: "standard", rows: 8, seatsPerRow: 13, schedule: generateMockSchedule(1) },
           { id: `theater-3-room-2`, name: "Sala 2", capacity: 160, type: "premium", rows: 10, seatsPerRow: 16, schedule: generateMockSchedule(2) },
           { id: `theater-3-room-3`, name: "Sala 4DX", capacity: 90, type: "4dx", rows: 6, seatsPerRow: 15, schedule: generateMockSchedule(3) }
         ],
         4: [
           { id: `theater-4-room-1`, name: "Sala Premium 1", capacity: 180, type: "premium", rows: 12, seatsPerRow: 15, schedule: generateMockSchedule(1) },
           { id: `theater-4-room-2`, name: "Sala IMAX", capacity: 350, type: "imax", rows: 18, seatsPerRow: 20, schedule: generateMockSchedule(2) },
           { id: `theater-4-room-3`, name: "Sala VIP", capacity: 90, type: "vip", rows: 6, seatsPerRow: 15, schedule: generateMockSchedule(3) }
         ],
         5: [
           { id: `theater-5-room-1`, name: "Sala A", capacity: 140, type: "standard", rows: 10, seatsPerRow: 14, schedule: generateMockSchedule(1) },
           { id: `theater-5-room-2`, name: "Sala B", capacity: 200, type: "premium", rows: 13, seatsPerRow: 16, schedule: generateMockSchedule(2) },
           { id: `theater-5-room-3`, name: "Sala 4DX", capacity: 100, type: "4dx", rows: 8, seatsPerRow: 13, schedule: generateMockSchedule(3) }
         ],
         6: [
           { id: `theater-6-room-1`, name: "Sala Ocean 1", capacity: 160, type: "standard", rows: 11, seatsPerRow: 15, schedule: generateMockSchedule(1) },
           { id: `theater-6-room-2`, name: "Sala Ocean IMAX", capacity: 400, type: "imax", rows: 20, seatsPerRow: 20, schedule: generateMockSchedule(2) },
           { id: `theater-6-room-3`, name: "Sala VIP Copacabana", capacity: 85, type: "vip", rows: 6, seatsPerRow: 14, schedule: generateMockSchedule(3) }
         ],
         7: [
           { id: `theater-7-room-1`, name: "Sala Carioca 1", capacity: 130, type: "standard", rows: 9, seatsPerRow: 15, schedule: generateMockSchedule(1) },
           { id: `theater-7-room-2`, name: "Sala Carioca Premium", capacity: 190, type: "premium", rows: 12, seatsPerRow: 16, schedule: generateMockSchedule(2) },
           { id: `theater-7-room-3`, name: "Sala 4DX Rio", capacity: 95, type: "4dx", rows: 7, seatsPerRow: 14, schedule: generateMockSchedule(3) }
         ],
         8: [
           { id: `theater-8-room-1`, name: "Sala Minas 1", capacity: 145, type: "standard", rows: 10, seatsPerRow: 15, schedule: generateMockSchedule(1) },
           { id: `theater-8-room-2`, name: "Sala Minas Premium", capacity: 175, type: "premium", rows: 11, seatsPerRow: 16, schedule: generateMockSchedule(2) },
           { id: `theater-8-room-3`, name: "Sala IMAX BH", capacity: 320, type: "imax", rows: 16, seatsPerRow: 20, schedule: generateMockSchedule(3) }
         ]
       };
      return mockRooms[theaterId] || mockRooms[1];
    }
  }
};


export const convertAPIDataToRooms = (apiData, cinemaName, cityName) => {
  if (!apiData || !Array.isArray(apiData)) {
    return [];
  }

  const generateSchedule = () => {
    const times = ['14:00', '16:30', '19:00', '21:30'];
    const movies = ['Filme A', 'Filme B', 'Filme C', 'Filme D'];
    return times.map((time, index) => ({
      time,
      movie: movies[index % movies.length],
      available: true
    }));
  };

  return apiData.map((room, index) => ({
    id: room.id || `api-${cityName}-${cinemaName}-${index}-${Date.now()}`,
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
      available: Math.floor((room.capacity || 100) * 0.8),
      occupied: Math.floor((room.capacity || 100) * 0.2)
    },
    schedule: generateSchedule(),
    source: 'ingresso-api'
  }));
};


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


const extractFeatures = (roomData) => {
  const features = ['Ar condicionado'];
  
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


export const syncWithIngressoAPI = async (localRooms, cityId) => {
  try {
    console.log('Iniciando sincronização com API do Ingresso.com...');
    
    const cinemas = await ingressoAPI.getCinemasByCity(cityId);
    
    if (!cinemas || cinemas.length === 0) {
      console.log('Nenhum cinema encontrado na API para esta cidade.');
      return localRooms;
    }
    
    const apiRooms = [];
    
    for (const cinema of cinemas.slice(0, 3)) {
      try {
        const rooms = await ingressoAPI.getTheaterRooms(cinema.id);
        const convertedRooms = convertAPIDataToRooms(rooms, cinema.name, cinema.city);
        apiRooms.push(...convertedRooms);
      } catch (error) {
        console.warn(`Erro ao buscar salas do cinema ${cinema.name}:`, error);
      }
    }
    
    console.log(`Sincronização concluída. ${apiRooms.length} salas encontradas na API.`);
    
    const localRoomsFiltered = localRooms.filter(room => room.source !== 'ingresso-api');
    return [...localRoomsFiltered, ...apiRooms];
    
  } catch (error) {
    console.error('Erro durante sincronização com API:', error);
    return localRooms;
  }
};

export default ingressoAPI;