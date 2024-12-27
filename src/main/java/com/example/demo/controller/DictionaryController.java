package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Dictionary;
import com.example.demo.service.DictionaryService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {
	private final DictionaryService dictionaryService;

	public DictionaryController(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	@GetMapping
	public ResponseEntity<List<Dictionary>> getAllDictionaries() {
		List<Dictionary> dictionaries = dictionaryService.getAllDictionaries();
		return ResponseEntity.ok(dictionaries);
	}

	@GetMapping("/type")
	public ResponseEntity<List<Dictionary>> getDictionaryByType(@RequestParam String type) {
		List<Dictionary> dictionaries = dictionaryService.findByDictType(type);
		return ResponseEntity.ok(dictionaries);
	}

	@PostMapping
	public ResponseEntity<Dictionary> addDictionary(@RequestBody Dictionary dictionary) {
		Dictionary newDictionary = dictionaryService.createDictionary(dictionary);
		return ResponseEntity.ok(newDictionary);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Dictionary> updateDictionary(@PathVariable Long id, @RequestBody Dictionary dictionary) {
		Dictionary updatedDictionary = dictionaryService.updateDictionary(id, dictionary);
		if (updatedDictionary != null) {
			return ResponseEntity.ok(updatedDictionary);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDictionary(@PathVariable Long id) {
		dictionaryService.deleteDictionary(id);
		return ResponseEntity.noContent().build();
	}
}
