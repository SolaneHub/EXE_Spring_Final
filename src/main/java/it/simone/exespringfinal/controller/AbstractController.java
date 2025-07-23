package it.simone.exespringfinal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.simone.exespringfinal.service.AbstractService;

public abstract class AbstractController <T, ID>{
	
	protected abstract AbstractService<T, ID> getService();
	
	@GetMapping
	public ResponseEntity<List<T>> findAll(){
		List<T> entities = getService().findAll();
		return ResponseEntity.ok(entities);
	}
	
	@PostMapping
	public ResponseEntity<T> save(@RequestBody T entity){
		T savedEntity = getService().save(entity);
		return ResponseEntity.ok(savedEntity);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<T> updateById(@PathVariable ID id, @RequestBody T entity) {
		T updatedEntityById = getService().updateById(id, entity);
		return ResponseEntity.ok(updatedEntityById);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable ID id) {
		if (!getService().existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID " + id + " non trovato.");
		} else {
			getService().deleteById(id);
			return ResponseEntity.status(HttpStatus.FOUND)
					.body("ID " + id + " eliminato con successo.");
		}

	}

}
