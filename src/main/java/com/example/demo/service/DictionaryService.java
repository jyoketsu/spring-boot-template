package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Dictionary;

public interface DictionaryService {
	List<Dictionary> getAllDictionaries();

	List<Dictionary> findByDictType(String type);

	Dictionary createDictionary(Dictionary dictionary);

	Dictionary updateDictionary(Long id, Dictionary dictionary);

	void deleteDictionary(Long id);
}
