package com.home.budgetplanner.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

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

    @Override
    public List<IdentificationType> findByMnemonic(String searchMnemonic, int startPosition, int maxResult) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<IdentificationType> criteriaQuery = criteriaBuilder.createQuery(IdentificationType.class);

        Root<IdentificationType> root = criteriaQuery.from(IdentificationType.class);

        Path<String> path = root.<String>get("mnemonic");
        criteriaQuery.where(criteriaBuilder.equal(path, searchMnemonic));

        // Root<People> root = criteriaQuery.from(People.class);
        CriteriaQuery<IdentificationType> select = criteriaQuery.select(root);

        TypedQuery<IdentificationType> typedQuery = em.createQuery(select);

        typedQuery.setFirstResult(startPosition);
        typedQuery.setMaxResults(maxResult);

        return em.createQuery(criteriaQuery).getResultList();

    }

    public List<Tuple> findByIdentificationType(IdentificationType identificationType, int startPosition, int maxResult) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        // CriteriaQuery<Tuple> criteriaQuery =
        // criteriaBuilder.createQuery(Tuple.class);

        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

        Root<IdentificationType> root = criteriaQuery.from(IdentificationType.class);

        criteriaQuery.select(criteriaBuilder.tuple(root.get("name"), root.get("mnemonic")));

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (identificationType.getName() != null) {
            Path<String> path = root.<String>get("name");
            predicates.add(criteriaBuilder.equal(path, identificationType.getName()));

        }

        if (identificationType.getMnemonic() != null) {
            Path<String> path = root.<String>get("mnemonic");
            predicates.add( criteriaBuilder.equal(path, identificationType.getMnemonic())  );

        }
        
        
        List<Predicate> predicatesNew = new ArrayList<Predicate>();
        if (predicates.size() > 1) {
            predicatesNew.add(criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])));
        } else if (predicates.size() == 1) {
            predicatesNew.add(predicates.get(0));
        }
        
        
        
        if (identificationType.getId() != null) {
            Path<String> path = root.<String>get("id");
            //predicatesNew.add( criteriaBuilder.equal(path, identificationType.getId())  );
            
            predicatesNew.add(criteriaBuilder.and( criteriaBuilder.notEqual(path, identificationType.getId()) ));


        }
        

        
        

        // query itself
        criteriaQuery.where(predicatesNew.toArray(new Predicate[] {}));

        TypedQuery<Tuple> typedQuery = em.createQuery(criteriaQuery);

        typedQuery.setFirstResult(startPosition);
        typedQuery.setMaxResults(maxResult);
        //  List<Tuple> test = em.createQuery(criteriaQuery).setFirstResult(startPosition).setMaxResults(maxResult).getResultList();
        
        List<Tuple> test = em.createQuery(criteriaQuery).getResultList();

        return test;

    }
    
    
    public IdentificationType findOneByIdentificationType(IdentificationType identificationType, int startPosition, int maxResult) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        // CriteriaQuery<Tuple> criteriaQuery =
        // criteriaBuilder.createQuery(Tuple.class);

        CriteriaQuery<IdentificationType> criteriaQuery = criteriaBuilder.createQuery(IdentificationType.class);

        Root<IdentificationType> root = criteriaQuery.from(IdentificationType.class);

       // criteriaQuery.select(criteriaBuilder.tuple(root.get("name"), root.get("mnemonic")));
       // Root<IdentificationType> root = criteriaQuery.from(IdentificationType.class);


        List<Predicate> predicates = new ArrayList<Predicate>();

        if (identificationType.getName() != null) {
            Path<String> path = root.<String>get("name");
            predicates.add(criteriaBuilder.equal(path, identificationType.getName()));

        }

        if (identificationType.getMnemonic() != null) {
            Path<String> path = root.<String>get("mnemonic");
            predicates.add( criteriaBuilder.equal(path, identificationType.getMnemonic())  );

        }
        
        
        List<Predicate> predicatesNew = new ArrayList<Predicate>();
        if (predicates.size() > 1) {
            predicatesNew.add(criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])));
        } else if (predicates.size() == 1) {
            predicatesNew.add(predicates.get(0));
        }
        
        
        
        if (identificationType.getId() != null) {
            Path<String> path = root.<String>get("id");
            //predicatesNew.add( criteriaBuilder.equal(path, identificationType.getId())  );
            
            predicatesNew.add(criteriaBuilder.and( criteriaBuilder.notEqual(path, identificationType.getId()) ));


        }
        

        
        

        // query itself
        criteriaQuery.where(predicatesNew.toArray(new Predicate[] {}));

        TypedQuery<IdentificationType> typedQuery = em.createQuery(criteriaQuery);

        typedQuery.setFirstResult(1);
        typedQuery.setMaxResults(1);
        //  List<Tuple> test = em.createQuery(criteriaQuery).setFirstResult(startPosition).setMaxResults(maxResult).getResultList();
        
        IdentificationType test=null;
        try{
            
            test = em.createQuery(criteriaQuery).getSingleResult();
        }
        
        catch(NoResultException e){
            
        }
        
        return test;

    }

}
