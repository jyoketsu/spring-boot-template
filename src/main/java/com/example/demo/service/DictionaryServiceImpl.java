package com.example.demo.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Dictionary;
import com.example.demo.repository.DictionaryRepository;

@Service
public class DictionaryServiceImpl implements DictionaryService {
	private final DictionaryRepository dictionaryRepository;

	public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
		this.dictionaryRepository = dictionaryRepository;
	}

	@Override
	/**
	 * Cacheable
	 * 注解用于声明一个方法是可缓存的，当第一次调用这个方法时，它的结果会被缓存起来，等到下次使用同样的参数调用这个方法时，就可以直接从缓存中获取结果，而不需要再次执行这个方法。
	 * value 属性用于指定缓存的名称，这个名称是一个字符串，可以指定一个或多个缓存名称，多个缓存名称之间用逗号分隔。
	 * key 属性用于指定缓存的 key，这个 key 是一个字符串，可以使用 SpEL 表达式来指定 key 的值，这样可以根据方法的参数动态生成key。
	 * condition 属性用于指定缓存的条件，这个条件是一个字符串，可以使用 SpEL 表达式来指定条件，只有当条件为 true 时，才会进行缓存。
	 * unless 属性用于指定缓存的条件，这个条件是一个字符串，可以使用 SpEL 表达式来指定条件，只有当条件为 false 时，才会进行缓存。
	 */
	@Cacheable(value = "dictionaries")
	public List<Dictionary> getAllDictionaries() {
		System.out.println("Fetching dictionaries from database");
		return dictionaryRepository.findAll();
	}

	@Override
	@Cacheable(value = "dictionaries", key = "#dictType")
	public List<Dictionary> findByDictType(String dictType) {
		System.out.println("Fetching dictionaries by dictType from database");
		return dictionaryRepository.findByDictType(dictType);
	}

	@Override
	@CachePut(value = "dictionaries", key = "#result.id")
	@CacheEvict(value = "dictionaries", allEntries = true)
	public Dictionary createDictionary(Dictionary dictionary) {
		return dictionaryRepository.save(dictionary);
	}

	@Override
	@CachePut(value = "dictionaries", key = "#id")
	@CacheEvict(value = "dictionaries", allEntries = true)
	public Dictionary updateDictionary(Long id, Dictionary dictionary) {
		return dictionaryRepository.findById(id)
				.map(existingDictionary -> {
					existingDictionary.setDictType(dictionary.getDictType());
					existingDictionary.setDictCode(dictionary.getDictCode());
					existingDictionary.setName(dictionary.getName());
					Dictionary updatedDictionary = dictionaryRepository.save(existingDictionary);
					return updatedDictionary;
				})
				.orElseThrow(() -> new ResourceNotFoundException("dictionary not found with id " + id));
	}

	@Override
	// @CacheEvict(value = "dictionaries", key = "#id")
	/**
	 * 使用全局清除缓存，因为：
	 * deleteDictionary 使用了 @CacheEvict 注解，并且指定了 key 为 #id，但 getAllDictionaries 和
	 * findByDictType 缓存的数据范围是整个 dictionaries 或特定 dictType，而不是单个 id
	 */
	@CacheEvict(value = "dictionaries", allEntries = true)
	public void deleteDictionary(Long id) {
		dictionaryRepository.deleteById(id);
	}
}
