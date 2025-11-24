package com.tao.fda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tao.fda.model.RuleEvaluationRequest;
import com.tao.fda.model.RuleEvaluationResponse;
import com.tao.fda.service.RuleEvaluationService;

@RestController
@RequestMapping("/api/v1/rules")
public class RuleEvaluationController {

	private final RuleEvaluationService evaluationService;

	public RuleEvaluationController(RuleEvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

	@PostMapping("/evaluate")
	public ResponseEntity<RuleEvaluationResponse> evaluate(@RequestBody RuleEvaluationRequest request) {
		RuleEvaluationResponse response = evaluationService.evaluate(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/health")
	public ResponseEntity<String> health() {
		return ResponseEntity.ok("UP");
	}

}
