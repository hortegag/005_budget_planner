package com.home.budgetplanner.repository.impl;

import java.io.Serializable;
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

import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.repository.IdentificationTypeDao;

@Repository
@Transactional
public class IdentificationTypeRepositoryImpl implements IdentificationTypeDao {

    @Autowired
    EntityManager em;

    public IdentificationType findById(Long id) {

        return em.find(IdentificationType.class, id);

    }

    @Override
    public List<IdentificationType> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<IdentificationType> criteriaQuery = criteriaBuilder.createQuery(IdentificationType.class);

        Root<IdentificationType> root = criteriaQuery.from(IdentificationType.class);
        CriteriaQuery<IdentificationType> select = criteriaQuery.select(root);

        TypedQuery<IdentificationType> typedQuery = em.createQuery(select);

        return typedQuery.getResultList();
    }

    @Override
    public IdentificationType getCustomer(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCustomer(int theId) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<IdentificationType> findByName(String searchName, int startPosition, int maxResult) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<IdentificationType> criteriaQuery = criteriaBuilder.createQuery(IdentificationType.class);

        Root<IdentificationType> root = criteriaQuery.from(IdentificationType.class);

        Path<String> path = root.<String>get("name");
        criteriaQuery.where(criteriaBuilder.equal(path, searchName));

        // Root<People> root = criteriaQuery.from(People.class);
        CriteriaQuery<IdentificationType> select = criteriaQuery.select(root);

        TypedQuery<IdentificationType> typedQuery = em.createQuery(select);

        typedQuery.setFirstResult(startPosition);
        typedQuery.setMaxResults(maxResult);

        return em.createQuery(criteriaQuery).getResultList();

    }

    @Override
    public void deleteById(Long id) {
        IdentificationType identificationType = findById(id);
        em.remove(identificationType);

    }

    @Override
    public IdentificationType save(IdentificationType identificationType) {

        if (identificationType.getId() == null) {
            em.persist(identificationType);
        } else {
            em.merge(identificationType);
        }
        return identificationType;
    }

}
