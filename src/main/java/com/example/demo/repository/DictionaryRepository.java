package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Dictionary;
import java.util.List;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long>, JpaSpecificationExecutor<Dictionary> {
	List<Dictionary> findByDictType(String dictType);

	@Query("SELECT DISTINCT d.dictType FROM Dictionary d")
	List<String> findDistinctDictType();
}
