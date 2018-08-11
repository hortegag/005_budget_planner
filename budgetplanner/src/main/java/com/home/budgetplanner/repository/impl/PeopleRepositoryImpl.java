package com.home.budgetplanner.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.repository.PeopleDAO;

@Repository
@Transactional
public class PeopleRepositoryImpl implements PeopleDAO {

    @Autowired
    EntityManager em;

    @Override
    public People findById(Long id) {

        return em.find(People.class, id);

    }

    @Override
    public void deleteById(Long id) {
        People people = findById(id);
        em.remove(people);

    }

    @Override
    public People save(People entity) {

        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
        return entity;
    }

    @Override
    public List<People> findPaginated(int startPosition, int maxResult) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<People> criteriaQuery = criteriaBuilder.createQuery(People.class);

        Root<People> root = criteriaQuery.from(People.class);
        CriteriaQuery<People> select = criteriaQuery.select(root);

        TypedQuery<People> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(startPosition);
        typedQuery.setMaxResults(maxResult);

        return typedQuery.getResultList();
    }

    @Override
    public Long count() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(People.class)));
        return em.createQuery(countQuery).getSingleResult();

    }

    public List<People> findPeopleByName(String name) {
        
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<People> criteriaQuery = criteriaBuilder.createQuery(People.class);
        Root<People> root = criteriaQuery.from(People.class);
        Path<String> path = root.<String>get("name");
        criteriaQuery.where(criteriaBuilder.equal(path, name));
        return em.createQuery(criteriaQuery).getResultList();
        
    }

}
