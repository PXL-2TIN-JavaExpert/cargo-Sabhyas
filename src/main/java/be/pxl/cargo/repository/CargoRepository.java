package be.pxl.cargo.repository;

import be.pxl.cargo.domain.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    Optional<Cargo> findCargoByCode(String code);
}
