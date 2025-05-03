package br.com.starter.domain.address;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address create(Address address) {
        if (isDuplicateAddress(address)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço já existe!");
        }
        return addressRepository.save(address);
    }

    public Address getOrCreate(Address address) {
        return get(address).orElseGet(() ->
            addressRepository.save(address)
        );
    }

    public Address update(UUID id, Address addressDetails) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não econtrado!"));

        var address = get(addressDetails);
        if (address.isPresent())
            return  address.get();

        existingAddress.setStreet(addressDetails.getStreet());
        existingAddress.setNumber(addressDetails.getNumber());
        existingAddress.setCep(addressDetails.getCep());
        existingAddress.setProvince(addressDetails.getProvince());
        existingAddress.setCity(addressDetails.getCity());
        existingAddress.setState(addressDetails.getState());

        return addressRepository.save(existingAddress);
    }

    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    public Address getById(UUID id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado"));
    }

    public void delete(UUID id) {
        if (!addressRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
        addressRepository.deleteById(id);
    }

    private boolean isDuplicateAddress(Address address) {
        return addressRepository.existsByStreetAndNumberAndCepAndCityAndState(
            address.getStreet(),
            address.getNumber(),
            address.getCep(),
            address.getCity(),
            address.getState()
        );
    }

    private Optional<Address> get(Address address) {
        return addressRepository.findByStreetAndNumberAndCepAndCityAndState(
            address.getStreet(),
            address.getNumber(),
            address.getCep(),
            address.getCity(),
            address.getState()
        );
    }

    public Page<Address> searchByStreet(String street, Pageable pageable) {
        return addressRepository.findByStreetIgnoreCaseContaining(street, pageable);
    }

    public Page<Address> searchByNumber(String number, Pageable pageable) {
        return addressRepository.findByNumberIgnoreCaseContaining(number, pageable);
    }

    public Page<Address> searchByCep(String cep, Pageable pageable) {
        return addressRepository.findByCepIgnoreCaseContaining(cep, pageable);
    }

    public Page<Address> searchByCity(String city, Pageable pageable) {
        return addressRepository.findByCityIgnoreCaseContaining(city, pageable);
    }

    public Page<Address> searchByState(String state, Pageable pageable) {
        return addressRepository.findByStateIgnoreCaseContaining(state, pageable);
    }
}

