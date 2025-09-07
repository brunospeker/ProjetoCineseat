export const rooms = [
  {
    id: 1,
    name: "Sala 1 - LASER",
    type: "LASER",
    capacity: 280,
    rows: 14,
    seatsPerRow: 20,
    cinema: "Cine Araújo Campo Limpo",
    city: "São Paulo",
    features: ["Ar condicionado", "Som Dolby Atmos", "Tela LASER"],
    status: "active",
    seats: {
      total: 280,
      available: 220,
      occupied: 60
    }
  },
  {
    id: 2,
    name: "Sala 2 - IMAX",
    type: "IMAX",
    capacity: 350,
    rows: 16,
    seatsPerRow: 22,
    cinema: "Cine Araújo Campo Limpo",
    city: "São Paulo",
    features: ["Ar condicionado", "Som IMAX", "Tela IMAX"],
    status: "active",
    seats: {
      total: 350,
      available: 280,
      occupied: 70
    }
  },
  {
    id: 3,
    name: "Sala 3 - Tradicional",
    type: "2D",
    capacity: 240,
    rows: 12,
    seatsPerRow: 20,
    cinema: "Cine Araújo Campo Limpo",
    city: "São Paulo",
    features: ["Ar condicionado", "Som Stereo"],
    status: "active",
    seats: {
      total: 240,
      available: 195,
      occupied: 45
    }
  },
  {
    id: 4,
    name: "Sala 4 - VIP",
    type: "VIP",
    capacity: 120,
    rows: 8,
    seatsPerRow: 15,
    cinema: "Cine Araújo Campo Limpo",
    city: "São Paulo",
    features: ["Ar condicionado", "Poltronas reclináveis", "Som Dolby Atmos", "Serviço de mesa"],
    status: "active",
    seats: {
      total: 120,
      available: 95,
      occupied: 25
    }
  },
  {
    id: 5,
    name: "Sala 5 - 4DX",
    type: "4DX",
    capacity: 168,
    rows: 10,
    seatsPerRow: 17,
    cinema: "Cinemark Shopping Ibirapuera",
    city: "São Paulo",
    features: ["Ar condicionado", "Efeitos 4D", "Som Dolby Atmos"],
    status: "maintenance",
    seats: {
      total: 168,
      available: 0,
      occupied: 0
    }
  }
];

export const roomTypes = [
  { id: "2D", name: "2D Tradicional", description: "Sala tradicional com projeção 2D" },
  { id: "3D", name: "3D", description: "Sala com projeção 3D" },
  { id: "IMAX", name: "IMAX", description: "Sala IMAX com tela gigante" },
  { id: "LASER", name: "LASER", description: "Sala com projeção LASER" },
  { id: "VIP", name: "VIP", description: "Sala VIP com poltronas premium" },
  { id: "4DX", name: "4DX", description: "Sala com efeitos 4D" },
  { id: "DOLBY", name: "Dolby Cinema", description: "Sala Dolby Cinema" }
];

export const roomStatus = [
  { id: "active", name: "Ativa", color: "green" },
  { id: "maintenance", name: "Manutenção", color: "orange" },
  { id: "inactive", name: "Inativa", color: "red" }
];