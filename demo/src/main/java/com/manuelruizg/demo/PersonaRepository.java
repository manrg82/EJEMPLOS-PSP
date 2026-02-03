package com.manuelruizg.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manuelruizg.demo.persistence.Persona;
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	
}