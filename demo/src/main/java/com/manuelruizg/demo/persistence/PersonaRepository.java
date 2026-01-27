package com.manuelruizg.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	
}