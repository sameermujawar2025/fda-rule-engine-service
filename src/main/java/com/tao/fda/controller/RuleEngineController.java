package com.tao.fda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tao.fda.model.RuleEvaluationRequest;
import com.tao.fda.model.RuleEvaluationResponse;
import com.tao.fda.service.RuleEngineService;

@RestController
@RequestMapping("/api/v1/rules")
public class RuleEngineController {

	private final RuleEngineService ruleEngineService;

	public RuleEngineController(RuleEngineService ruleEngineService) {
		this.ruleEngineService = ruleEngineService;
	}

	@PostMapping("/evaluate")
	public ResponseEntity<RuleEvaluationResponse> evaluate(@RequestBody RuleEvaluationRequest request,
			@RequestHeader(value = "X-Client-Id", required = false) String clientIdHeader) {

		if (request.getClientId() == null || request.getClientId().isBlank()) {
			if (clientIdHeader != null && !clientIdHeader.isBlank()) {
				request.setClientId(clientIdHeader);
			} else {
				request.setClientId("default"); // or throw error, your choice
			}
		}

		RuleEvaluationResponse response = ruleEngineService.evaluate(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/health")
	public ResponseEntity<String> health() {
		return ResponseEntity.ok("UP");
	}

}
