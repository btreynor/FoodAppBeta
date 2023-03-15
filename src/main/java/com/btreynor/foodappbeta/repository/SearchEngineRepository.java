package com.btreynor.foodappbeta.repository;

import com.btreynor.foodappbeta.model.SearchEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SearchEngineRepository extends JpaRepository<SearchEngine, String> {

}
