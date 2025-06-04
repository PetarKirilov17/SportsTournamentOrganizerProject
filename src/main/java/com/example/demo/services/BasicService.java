package com.example.demo.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class BasicService<Entity> {
    protected abstract CrudRepository<Entity, Long> getRepository();

    public Entity getById(Long id) {
        return this.getRepository().findById(id).orElse(null);
    }

    public Iterable<Entity> getAll() {
        return this.getRepository().findAll();
    }

    public Entity save(Entity entity) {
        return this.getRepository().save(entity);
    }

    public Entity update(Entity entity, Long id) {
        if (entity == null || !this.getRepository().existsById(id)) {
            throw new IllegalArgumentException("Entity does not exist");
        }
        return this.getRepository().save(entity);
    }

    public void deleteById(Long id) {
        if (!this.getRepository().existsById(id)) {
            throw new IllegalArgumentException("Entity does not exist");
        }
        this.getRepository().deleteById(id);
    }

}
