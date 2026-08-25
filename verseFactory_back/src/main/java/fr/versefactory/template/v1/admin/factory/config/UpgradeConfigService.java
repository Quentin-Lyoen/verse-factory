package fr.versefactory.template.v1.admin.factory.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.io.InputStream;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpgradeConfigService {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    private Map<String, UpgradeDetails> upgradeConfigs;

    @PostConstruct
    public void init() {
        try {
            Resource resource = resourceLoader.getResource("classpath:upgrades.json");
            try (InputStream inputStream = resource.getInputStream()) {
                upgradeConfigs = objectMapper.readValue(inputStream, new TypeReference<Map<String, UpgradeDetails>>() {});
            }
        } catch (Exception e) {
            log.error("Failed to load upgrades.json config", e);
        }
    }

    public BigDecimal getNextLevelCost(String upgradeId, int currentLevel) {
        if (upgradeConfigs == null || !upgradeConfigs.containsKey(upgradeId)) {
            return null;
        }
        UpgradeDetails upgradeDetails = upgradeConfigs.get(upgradeId);
        if (upgradeDetails == null || upgradeDetails.getLevels() == null) {
            return null;
        }
        int nextLevel = currentLevel + 1;
        LevelDetails levelDetails = upgradeDetails.getLevels().get(String.valueOf(nextLevel));
        return levelDetails != null ? levelDetails.getCost() : null;
    }

    @Data
    public static class UpgradeDetails {
        private Map<String, LevelDetails> levels;
    }

    @Data
    public static class LevelDetails {
        private BigDecimal cost;
        @JsonProperty("effect_value")
        private BigDecimal effectValue;
    }
}
