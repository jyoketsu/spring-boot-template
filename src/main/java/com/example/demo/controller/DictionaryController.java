package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Dictionary;
import com.example.demo.service.DictionaryService;

import java.util.List;

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
	public List<Dictionary> getAllDictionaries() {
		return dictionaryService.getAllDictionaries();
	}

	@GetMapping("/type")
	public List<Dictionary> getDictionaryByType(@RequestParam String type) {
		return dictionaryService.findByDictType(type);
	}

	@PostMapping
	public Dictionary addDictionary(@RequestBody Dictionary dictionary) {
		return dictionaryService.createDictionary(dictionary);
	}

	@PutMapping("/{id}")
	public Dictionary updateDictionary(@PathVariable Long id, @RequestBody Dictionary dictionary) {
		return dictionaryService.updateDictionary(id, dictionary);
	}

	@DeleteMapping("/{id}")
	public void deleteDictionary(@PathVariable Long id) {
		dictionaryService.deleteDictionary(id);
	}
}
