package it.simone.exespringfinal.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import it.simone.exespringfinal.service.AbstractService;

@CrossOrigin(origins = "http://localhost:4200")
public abstract class AbstractController<T, ID> {

	protected abstract AbstractService<T, ID> getService();

	@GetMapping
	public ResponseEntity<List<T>> findAll() {
		List<T> entities = getService().findAll();
		return ResponseEntity.ok(entities);
	}

	@PostMapping
	public ResponseEntity<T> save(@RequestBody T entity) {
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
		HttpHeaders myHttpHeaders = new HttpHeaders();
		myHttpHeaders.add(HttpHeaders.CONTENT_TYPE, "text/plain");
		String myJson = "";
		if (!getService().existsById(id)) {
			myJson= "ID " + id + " non trovato.";
			return new ResponseEntity<>(myJson, myHttpHeaders, HttpStatus.NOT_FOUND);
		} else {
			getService().deleteById(id);
			myJson = "{\"Messaggio\":\"Ok, eliminato id = "+ id +"\"}";
			return new ResponseEntity<>(myJson, myHttpHeaders, HttpStatus.OK);
		}

	}

}
