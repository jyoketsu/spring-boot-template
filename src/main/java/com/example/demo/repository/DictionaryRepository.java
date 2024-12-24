package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Dictionary;
import java.util.List;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
	List<Dictionary> findByDictType(String dictType);
}
