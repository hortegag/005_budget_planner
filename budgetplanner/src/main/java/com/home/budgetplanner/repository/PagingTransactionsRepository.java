package com.home.budgetplanner.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.home.budgetplanner.controller.dtos.DataDTO;
import com.home.budgetplanner.entity.IdentificationType;
import com.home.budgetplanner.entity.People;
import com.home.budgetplanner.entity.TransactionType;
import com.home.budgetplanner.entity.Transactions;

public interface PagingTransactionsRepository extends PagingAndSortingRepository<Transactions, Long> {


    Page<Transactions> findByDescription(String description, Pageable pageable);
    
    Page<Transactions> findByDescriptionAndPeople(String description, People people, Pageable pageable);
    
  //  Page<Transactions> findAllTransactionsByPeople(People people, Pageable pageable);
    
    
    Page<Transactions> findByPeople(People people, Pageable pageable);
    
    Transactions findTopByPeopleOrderByIdDesc(People people);
    
    
    @Query("SELECT " +
            "    new com.home.budgetplanner.controller.dtos.DataDTO(v.transactionType.entryType, SUM(v.value)) " +
            "FROM " +
            "    Transactions v " +
            "GROUP BY " +
            "    v.transactionType.entryType")
     List<DataDTO> findTransactionSum();
    
    
    
    @Query("SELECT " +
            "    new com.home.budgetplanner.controller.dtos.DataDTO(v.transactionType.name, SUM(v.value)) " +
            "FROM " +
            "    Transactions v " +
            "WHERE "+
            "    v.transactionType.entryType = :entryType " +
            "GROUP BY " +
            "    v.transactionType.name "
            )
     List<DataDTO> findTransactionSumByEntryType(@Param("entryType") String entryType);
    
    
    
    
 //   @Query(value="SELECT " +
//            "    new com.home.budgetplanner.controller.dtos.DataDTO(  v.transactionDate , SUM(v.value)) " +
//            "FROM " +
//            "    Transactions v " +
//            "WHERE "+
//            "    v.transactionType.entryType = :entryType " +
//            "    AND v.transactionDate between :startDate AND :endDate " + 
//            "GROUP BY " +
         //   "    date_part( 'day', v.transactionDate) ", nativeQuery =true
//            "   v.transactionDate ", nativeQuery =true
//            )
    
    @Query(value="SELECT " +
           // "     v.transaction_date , SUM(v.value) " +
         //  "      date_part( 'day', v.transactionDate) , SUM(v.value) " +
           "       EXTRACT(DAY FROM v.transaction_date) , SUM(v.value) " +

        
            "FROM " +
            "    transactions v " +
            " JOIN transaction_type tt ON v.id_transaction_type=tt.id_transaction_type "+
            " JOIN people p ON v.id_person = p.id_person  "+
            "WHERE "+
            "    tt.entry_type = :entryType " +
            " AND p.username = :username " +

           // "    AND v.transaction_date between :startDate AND :endDate " + 
           " AND v.transaction_date between :startDate AND :endDate " + 
            "GROUP BY " +
          // "    date_part( 'day', v.transactionDate) ", nativeQuery =true
           "    EXTRACT(DAY FROM v.transaction_date) "+
           "ORDER BY "+
              " v.transaction_date ", nativeQuery =true
            

          //  "   v.transaction_date ", nativeQuery =true
            )
    
    
    List<Object[][]> findTransactionSumByEntryTypeAndDay(@Param("entryType") String entryType, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("username") String userName);  
    
    
    
    
    
    @Query("SELECT " +
            "    new com.home.budgetplanner.controller.dtos.DataDTO(v.transactionType.name, SUM(v.value)) " +
            "FROM " +
            "    Transactions v INNER JOIN v.people p " +
            "WHERE "+
            "    v.transactionType.entryType = :entryType " +
            " AND p.username= :username "+
            " AND v.transactionDate between :startDate AND :endDate "+
            "GROUP BY " +
            "    v.transactionType.name "
            )
     List<DataDTO> findTransactionSumByEntryTypeAndDate(@Param("entryType") String entryType,  @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,  @Param("username") String userName);
    
    
    
    @Query("SELECT " +
            "    new com.home.budgetplanner.controller.dtos.DataDTO(v.transactionType.entryType, SUM(v.value)) " +
            "FROM " +
            "    Transactions v INNER JOIN v.people p " +
            "WHERE "+
            "     v.transactionDate between :startDate AND :endDate "+
            " AND "+
            "     p.username= :username "+
            "GROUP BY " +
            "    v.transactionType.entryType ")
     List<DataDTO> findTransactionIncomeAndExpenseByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,  @Param("username") String username);

}
