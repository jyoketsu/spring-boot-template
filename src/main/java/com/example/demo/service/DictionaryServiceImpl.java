package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Dictionary;
import com.example.demo.repository.DictionaryRepository;

@Service
public class DictionaryServiceImpl implements DictionaryService {
	private final DictionaryRepository dictionaryRepository;

	public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
		this.dictionaryRepository = dictionaryRepository;
	}

	@Override
	public List<Dictionary> getAllDictionaries() {
		return dictionaryRepository.findAll();
	}

	@Override
	public List<Dictionary> findByDictType(String dictType) {
		return dictionaryRepository.findByDictType(dictType);
	}

	@Override
	public Dictionary createDictionary(Dictionary dictionary) {
		return dictionaryRepository.save(dictionary);
	}

	@Override
	public Dictionary updateDictionary(Long id, Dictionary dictionary) {
		return dictionaryRepository.findById(id)
				.map(existingDictionary -> {
					existingDictionary.setDictType(dictionary.getDictType());
					existingDictionary.setDictCode(dictionary.getDictCode());
					existingDictionary.setName(dictionary.getName());
					Dictionary updatedDictionary = dictionaryRepository.save(existingDictionary);
					return updatedDictionary;
				})
				.orElse(null);
	}

	@Override
	public void deleteDictionary(Long id) {
		dictionaryRepository.deleteById(id);
	}
}
