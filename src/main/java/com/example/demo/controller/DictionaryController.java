package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PageResponse;
import com.example.demo.model.Dictionary;
import com.example.demo.service.DictionaryService;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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
	public PageResponse<Dictionary> getAllDictionaries(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String dictType,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return dictionaryService.getAllDictionaries(name, dictType, page, size);
	}

	@GetMapping("/type")
	public List<Dictionary> getDictionaryByType(@RequestParam String type) {
		return dictionaryService.findByDictType(type);
	}

	@GetMapping("/{id}")
	public Dictionary getDictById(@PathVariable Long id) {
		return dictionaryService.findById(id);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Dictionary addDictionary(@RequestBody Dictionary dictionary) {
		return dictionaryService.createDictionary(dictionary);
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Dictionary updateDictionary(@RequestBody Dictionary dictionary) {
		return dictionaryService.updateDictionary(dictionary);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteDictionary(@PathVariable Long id) {
		dictionaryService.deleteDictionary(id);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteDictionaries(@RequestBody List<Long> ids) {
		dictionaryService.deleteDictionaries(ids);
	}

	@GetMapping("/types")
	public List<String> getTypes() {
		return dictionaryService.getAllDictTypes();
	}
}
