package com.example.springbootexercisementoring.phonenumber;

import java.util.Optional;
import javax.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/phonenumber")
public class PhoneNumberController {
 private final PhoneNumberRepository phoneNumberRepository;

 @Autowired
  public PhoneNumberController(PhoneNumberRepository phoneNumberRepository) {
    this.phoneNumberRepository = phoneNumberRepository;
  }

  @GetMapping("/get")
  public Optional<PhoneNumber> getPhoneNumber(@RequestParam String id){
    return phoneNumberRepository.findById(id);
  }

  @PostMapping("/create")
  public PhoneNumber createPhoneNumber(@RequestBody PhoneNumber phoneNumber){
    return phoneNumberRepository.save(phoneNumber);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deletePhoneNumber(@RequestParam String id){
   if(phoneNumberRepository.existsById(id)){
     phoneNumberRepository.deleteById(id);
     return new ResponseEntity<>("Person with id " + id + " has been deleted.", HttpStatus.OK);
   }else {
     return new ResponseEntity<>("Person with id " + id + " not found.", HttpStatus.NOT_FOUND);
   }
  }

}
