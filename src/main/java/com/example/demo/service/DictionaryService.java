package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.PageResponse;
import com.example.demo.model.Dictionary;

public interface DictionaryService {
	PageResponse<Dictionary> getAllDictionaries(String name, String dictType, int page, int size);

	List<Dictionary> findByDictType(String type);

	Dictionary findById(Long id);

	Dictionary createDictionary(Dictionary dictionary);

	Dictionary updateDictionary(Long id, Dictionary dictionary);

	void deleteDictionary(Long id);
}
