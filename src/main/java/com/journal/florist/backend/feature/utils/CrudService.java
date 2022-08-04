package com.journal.florist.backend.feature.utils;

import com.journal.florist.backend.feature.user.model.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public interface CrudService<T extends BaseEntity> {

    JpaRepository<T, Long> getRepository();

    default T saveIdentityUser(AppUsers appUser, T entity){
        return getRepository().saveAndFlush(entity);
    }

    default void delete(AppUsers appUser, T entity) {
        if(entity == null) {
            throw new EntityNotFoundException();
        }
        getRepository().delete(entity);
    }

    default long count() { return getRepository().count(); }

    default T findById(long id){
        T entity = getRepository().findById (id).orElse(null);
        if( entity == null ) {
            throw new EntityNotFoundException();
        }
        return entity;
    }

    T createNew(AppUsers currentAppUser);

}
