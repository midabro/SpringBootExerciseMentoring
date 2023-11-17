package com.example.springbootexercisementoring.address;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

  private final AddressRepository addressRepository;

  public AddressController(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @GetMapping("/get")
  public Optional<Address> getPerson(@RequestParam String id) {
    return addressRepository.findById(id);
  }

  @PostMapping("/create")
  public Address createAddress(@RequestBody AddressDTO addressDTO) {
    Address address=new Address();
    address.setId(addressDTO.getId());
    address.setCity(addressDTO.getCity());
    address.setStreet(addressDTO.getStreet());
    address.setPostalCode(address.getPostalCode());
    return addressRepository.save(address);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteAddress(String addressId) {
    if (addressRepository.existsById(addressId)) {
      addressRepository.deleteById(addressId);
      return new ResponseEntity<>("Address with id " + addressId + " has been deleted.",
          HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Address with id " + addressId + " not found.",
          HttpStatus.NOT_FOUND);
    }
  }
}
