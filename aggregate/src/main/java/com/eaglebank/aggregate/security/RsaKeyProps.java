package com.eaglebank.aggregate.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProps(
        RSAPublicKey pub,
        RSAPrivateKey priv
) {}