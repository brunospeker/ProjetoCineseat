package com.maisprati.Cineseat.service;

import com.maisprati.Cineseat.dto.CinemaDTO;
import com.maisprati.Cineseat.entities.Cinema;
import com.maisprati.Cineseat.repositories.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    public List<CinemaDTO> findAll() {
        return cinemaRepository.findAll()
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CinemaDTO> findById(Long id) {
        return cinemaRepository.findById(id).map(CinemaDTO::new);
    }

    public List<CinemaDTO> findByCityName(String cityName) {
        return cinemaRepository.findByCityName(cityName)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByState(String state) {
        return cinemaRepository.findByState(state)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByAddress(String address) {
        return cinemaRepository.findByAddress(address)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByEnableTrue() {
        return cinemaRepository.findByEnableTrue()
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByHasBomboniereTrue() {
        return cinemaRepository.findByHasBomboniereTrue()
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByCorporation(String corporation) {
        return cinemaRepository.findByCorporation(corporation)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public List<CinemaDTO> findByTotalRooms(Integer rooms) {
        return cinemaRepository.findByTotalRooms(rooms)
                .stream()
                .map(CinemaDTO::new)
                .collect(Collectors.toList());
    }

    public CinemaDTO createCinema(Cinema cinema) {
        Cinema newCinema = cinemaRepository.save(cinema);
        return new CinemaDTO(newCinema);
    }

    public Optional<CinemaDTO> updateCinema(Long id, CinemaDTO dto) {
        return cinemaRepository.findById(id).map(cinema -> {
            updateCinemaData(cinema, dto);
            return new CinemaDTO(cinemaRepository.save(cinema));
        });
    }

    private void updateCinemaData(Cinema cinema, CinemaDTO dto) {
        if (dto.getName() != null) cinema.setName(dto.getName());
        if (dto.getSite() != null) cinema.setSite(dto.getSite());
        if (dto.getCnpj() != null) cinema.setCnpj(dto.getCnpj());
        if (dto.getState() != null) cinema.setState(dto.getState());
        if (dto.getUf() != null) cinema.setUf(dto.getUf());
        if (dto.getCityName() != null) cinema.setCityName(dto.getCityName());
        if (dto.getCityId() != null) cinema.setCityId(dto.getCityId());
        if (dto.getAddress() != null) cinema.setAddress(dto.getAddress());
        if (dto.getAddressComplement() != null) cinema.setAddressComplement(dto.getAddressComplement());
        if (dto.getNeighborhood() != null) cinema.setNeighborhood(dto.getNeighborhood());
        if (dto.getNumber() != null) cinema.setNumber(dto.getNumber());
        if (dto.getImagesJson() != null) cinema.setImagesJson(dto.getImagesJson());
        if (dto.getHasBomboniere() != null) cinema.setHasBomboniere(dto.getHasBomboniere());
        if (dto.getHasSession() != null) cinema.setHasSession(dto.getHasSession());
        if (dto.getHasSeatDistancePolicy() != null) cinema.setHasSeatDistancePolicy(dto.getHasSeatDistancePolicy());
        if (dto.getHasSeatDistancePolicyArena() != null) cinema.setHasSeatDistancePolicyArena(dto.getHasSeatDistancePolicyArena());
        if (dto.getDeliveryType() != null) cinema.setDeliveryType(dto.getDeliveryType());
        if (dto.getCorporation() != null) cinema.setCorporation(dto.getCorporation());
        if (dto.getCorporationId() != null) cinema.setCorporationId(dto.getCorporationId());
        if (dto.getOperationPoliciesJson() != null) cinema.setOperationPoliciesJson(dto.getOperationPoliciesJson());
        if (dto.getTotalRooms() != null) cinema.setTotalRooms(dto.getTotalRooms());
        if (dto.getEnable() != null) cinema.setEnable(dto.getEnable());

        cinema.setUpdateDate(LocalDateTime.now());
    }


    public boolean deleteCinema(Long id) {
        return cinemaRepository.findById(id).map(cinema -> {
            cinemaRepository.delete(cinema);
            return true;
        }).orElse(false);
    }
}
