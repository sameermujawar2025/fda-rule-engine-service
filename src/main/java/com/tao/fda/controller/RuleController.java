package com.tao.fda.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tao.fda.dto.RuleDto;
import com.tao.fda.service.RuleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rules")
public class RuleController {

	private final RuleService ruleService;

	public RuleController(RuleService ruleService) {
		this.ruleService = ruleService;
	}

	@PostMapping
	public ResponseEntity<RuleDto> create(@Valid @RequestBody RuleDto dto) {
		RuleDto created = ruleService.create(dto);
		return ResponseEntity.created(URI.create("/api/v1/rules/" + created.id())).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RuleDto> update(@PathVariable Long id, @Valid @RequestBody RuleDto dto) {
		return ResponseEntity.ok(ruleService.update(id, dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RuleDto> get(@PathVariable Long id) {
		return ResponseEntity.ok(ruleService.get(id));
	}

	@GetMapping
	public ResponseEntity<List<RuleDto>> listByClient(@RequestParam String clientId) {
		return ResponseEntity.ok(ruleService.listByClient(clientId));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		ruleService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
