package com.maisprati.Cineseat.repositories;

import com.maisprati.Cineseat.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    List<Cinema> findByCityName(String cityName);
    List<Cinema> findByState(String state);
    List<Cinema> findByAddress(String address);
    List<Cinema> findByEnableTrue();
    List<Cinema> findByHasBomboniereTrue();
    List<Cinema> findByCorporation(String corporation);
    List<Cinema> findByTotalRooms(Integer rooms);
}

