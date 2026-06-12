package edu.cecar.cootramixtol.cootramixtol_api.repository;

import edu.cecar.cootramixtol.cootramixtol_api.entity.Consecutivo;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ConsecutivoRepository extends JpaRepository<Consecutivo, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Consecutivo> findWithLockByNombre(String nombre);
}
