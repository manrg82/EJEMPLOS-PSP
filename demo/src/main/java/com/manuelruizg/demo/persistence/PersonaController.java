package com.manuelruizg.demo.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@EntityScan(basePackages = {"persistence"})
@RestController
@RequestMapping("/persona")
public class PersonaController {
@Autowired
private PersonaRepository repository;
/**
* Get all person list.
*
* @return the list
*/
@GetMapping("/find") // GET Method for reading operation
public List<Persona> getAllPerson() {
return repository.findAll();
}
}