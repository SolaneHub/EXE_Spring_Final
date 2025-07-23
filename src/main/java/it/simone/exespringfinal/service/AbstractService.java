package it.simone.exespringfinal.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<T, ID> {

	protected abstract JpaRepository<T, ID> getRepository();

	public long count() {
		return getRepository().count();
	}

	public boolean existsById(ID id) {
		return getRepository().existsById(id);
	}

	public T save(T entity) {
		return getRepository().save(entity);
	}

	public void deleteById(ID id) {
		getRepository().deleteById(id);

	}

	protected abstract String getEntityName();

	public T findByIdOrThrow(ID id) {
		return getRepository().findById(id)
				.orElseThrow(() -> new RuntimeException(getEntityName() + " con id " + id + " non trovata"));
	}

	public List<T> findAll() {
		return getRepository().findAll();
	}

	public abstract T updateById(ID id, T entityDetails);
}
