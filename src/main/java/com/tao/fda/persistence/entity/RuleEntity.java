package com.tao.fda.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "fraud_rule")
public class RuleEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String clientId;

    @Column(nullable = false, length = 32)
    private String ruleCode;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(length = 512)
    private String description;

    @Column(nullable = false, length = 32)
    private String type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String configJson;

    private Integer priority;

    @Column(nullable = false)
    private boolean active = true;

    @Column(length = 32)
    private String version;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    void beforeInsert() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void beforeUpdate() {
        this.updatedAt = Instant.now();
    }

    // ---------- Getters & Setters -----------

    public Long getId() { return id; }
    public String getClientId() { return clientId; }
    public String getRuleCode() { return ruleCode; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getConfigJson() { return configJson; }
    public Integer getPriority() { return priority; }
    public boolean isActive() { return active; }
    public String getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setId(Long id) { this.id = id; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setType(String type) { this.type = type; }
    public void setConfigJson(String configJson) { this.configJson = configJson; }
    public void setPriority(Integer priority) { this.priority = priority; }
    public void setActive(boolean active) { this.active = active; }
    public void setVersion(String version) { this.version = version; }

}
