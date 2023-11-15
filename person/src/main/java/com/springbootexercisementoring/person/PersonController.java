package com.springbootexercisementoring.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonController {
    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/get")
    public Optional<Person> getPerson(@RequestParam String id) {
        return personRepository.findById(id);
    }


    @PostMapping("/create")
    public Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePerson(@RequestParam String id){
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return new ResponseEntity<>("com.spr.person.Person with id " + id + " has been deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("com.spr.person.Person with id " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }


}
