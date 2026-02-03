package com.manuelruizg.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manuelruizg.demo.persistence.Persona;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@EntityScan(basePackages = {"com.manuelruizg.demo.persistence"})
@RestController
@RequestMapping("/persona")
public class PersonaController {
	@Autowired
	private PersonaRepository repository;
	
	/**
	 * Get Persona by id
	 * @param personaId
	 * @return
	 * @throws PersonaNotFoundException
	 */
	@GetMapping("/find/{id}") // GET Method for Read operation
	public ResponseEntity<Persona> getPersonaById(@PathVariable(value = "id") Long personaId) throws PersonaNotFoundException {
	
	    Persona persona = repository.findById(personaId)
	        .orElseThrow(() -> new PersonaNotFoundException(personaId));
	
	    return ResponseEntity.ok().body(persona);
	}
	/**
	 * Crea persona entity
	 * @param newPersona Persona
	 * @param result BindingResult
	 * @return
	 */
	@Validated //habilita la validacion a nivel de metodo
	@PostMapping("/create") // POST Method for Create operation
	public ResponseEntity<?> createPerson(@Valid @RequestBody Persona newPersona, BindingResult result){

	    // Si hay errores de validación, Spring NO lanza excepción automáticamente
	    if (result.hasErrors()) {
	        Map<String, String> errors = new HashMap<>();
	        result.getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage())
	        );

	        return ResponseEntity.badRequest().body(errors);
	    }

	    Persona persona = repository.save(newPersona);
	    return ResponseEntity.ok().body(persona);
	}
	/**
	 * Update person response entity
	 * @param personDetails
	 * @param personId
	 * @return
	 * @throws PersonaNotFoundException
	 */
	@PutMapping("/update/{id}") // PUT Method for Update operation
	public ResponseEntity<Persona> updatePerson(@RequestBody Persona personDetails,
	        @PathVariable(value = "id") Long personaId) throws PersonaNotFoundException {

	    Persona persona = repository.findById(personaId)
	            .orElseThrow(() -> new PersonaNotFoundException(personaId));

	    persona.setNombre(personDetails.getNombre());
	    persona.setApellidos(personDetails.getApellidos());
	    persona.setDomicilio(personDetails.getDomicilio());
	    persona.setEmail(personDetails.getEmail());

	    final Persona personaModificada = repository.save(persona);
	    return ResponseEntity.ok(personaModificada);
	}
	/**
	 * Delete person by ID
	 * @param personaId
	 * @return
	 * @throws PersonaNotFoundException
	 */
	@DeleteMapping("/delete/{id}") // DELETE Method for Delete operation
	public boolean deletePerson(@PathVariable(value = "id") Long personaId)
	        throws PersonaNotFoundException {

	    Persona persona = repository.findById(personaId)
	            .orElseThrow(() -> new PersonaNotFoundException(personaId));

	    repository.delete(persona);
	    return true;
	}
	/**
	 * Get all persona list.
	 *
	 * @return the list
	 */
	@Operation(summary = "Obtener todas las personas", description = "Recupera una lista de todas las personas.")
	@ApiResponse(responseCode = "200", description = "Lista de personas recuperada correctamente")
	@GetMapping("/find") // GET Method for reading operation
	public List<Persona> getAllPersons() {

	    return repository.findAll();
	}


}