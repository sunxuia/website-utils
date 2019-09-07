package net.sunxu.website.test.helputil.authtoken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class AuthTokenBuilder {

    public static Resource publicKey() {
        return new ClassPathResource("client.crt");
    }

    public static String publicKeyType() {
        return "X.509";
    }

    public AuthTokenBuilder() {
        Resource resource = new ClassPathResource("key.keystore");
        setPassword(resource, "PKCS12", "123456", "client");
    }

    private PrivateKey privateKey;

    public AuthTokenBuilder setPassword(Resource resource, String keyType, String password, String alias) {
        var pwd = password.toCharArray();
        try {
            KeyStore keyStore = KeyStore.getInstance(keyType);
            keyStore.load(resource.getInputStream(), pwd);
            privateKey = (PrivateKey) keyStore.getKey(alias, pwd);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return this;
    }

    @Setter
    @Accessors(fluent = true)
    private Long id = 100L;

    @Setter
    @Accessors(fluent = true)
    private String name = "unit-test-name";

    private List<String> roles = new ArrayList<>();

    public AuthTokenBuilder addRole(@NonNull String roleName) {
        roleName = roleName.toUpperCase();
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }
        roles.add(roleName);
        return this;
    }

    @Setter
    @Accessors(fluent = true)
    private Long exipreSeconds = 60L * 30;

    @Setter
    @Accessors(fluent = true)
    private String issuer = "test-issuer";

    @Setter
    @Accessors(fluent = true)
    private boolean service = false;

    private Map<String, Object> custom = new HashMap<>();

    public AuthTokenBuilder custom(String name, Object value) {
        custom.put(name, value);
        return this;
    }

    public String build() {
        var builder = Jwts.builder()
                .signWith(SignatureAlgorithm.RS256, privateKey);
        builder.claim("id", id);
        builder.claim("name", name);
        builder.claim("roles", roles);
        builder.claim("service", service);
        builder.setIssuedAt(new Date());
        builder.setExpiration(new Date(System.currentTimeMillis() + exipreSeconds * 1000));
        builder.setIssuer(issuer);
        for (Entry<String, Object> entry : custom.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        return builder.compact();
    }
}
