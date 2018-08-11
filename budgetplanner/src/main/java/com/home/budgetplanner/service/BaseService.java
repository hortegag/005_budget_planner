package com.home.budgetplanner.service;

import java.util.List;

public interface BaseService<E, K> {
    public E findById(Long id);

    public void deleteById(K id);

    public E save(E entity);

    public List<E> findPaginated(int startPosition, int maxResult);

    public Long count();

}