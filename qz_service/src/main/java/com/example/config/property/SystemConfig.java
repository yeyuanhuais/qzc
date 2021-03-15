package com.example.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "system")
@Data
public class SystemConfig {
	private PasswordKeyConfig pwdKey;
}
