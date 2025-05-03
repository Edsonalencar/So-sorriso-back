package br.com.starter.domain.address;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    boolean existsByStreetAndNumberAndCepAndCityAndState(
        String street,
        String number,
        String cep,
        String city,
        String state
    );

    Optional<Address> findByStreetAndNumberAndCepAndCityAndState(
        String street,
        String number,
        String cep,
        String city,
        String state
    );

    Page<Address> findByStreetIgnoreCaseContaining(String street, Pageable pageable);

    Page<Address> findByNumberIgnoreCaseContaining(String number, Pageable pageable);

    Page<Address> findByCepIgnoreCaseContaining(String cep, Pageable pageable);

    Page<Address> findByCityIgnoreCaseContaining(String city, Pageable pageable);

    Page<Address> findByStateIgnoreCaseContaining(String state, Pageable pageable);
}
