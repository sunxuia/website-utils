package net.sunxu.website.test.helputil.authtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class AuthTokenBuilderTest {

    @Test
    public void testPublicKey() {
        Assert.assertNotNull(AuthTokenBuilder.publicKey());
        Assert.assertNotNull(AuthTokenBuilder.publicKeyType());
    }

    @Test
    public void testBuilder() {
        var builder = new AuthTokenBuilder();
        String token = builder.id(1L)
                .name("test-name")
                .issuer("test-issuer")
                .exipreSeconds(10L)
                .addRole("ROLE_TEST")
                .addRole("service")
                .service(true)
                .custom("test", "test-value")
                .build();

        var res = decrypt(token);

        long exipre = res.getExpiration().getTime() - System.currentTimeMillis();
        Assert.assertTrue(8000 < exipre && exipre < 10000);
        Assert.assertEquals(Long.valueOf(1L), res.get("id", Long.class));
        Assert.assertEquals("test-name", res.get("name", String.class));
        Assert.assertEquals("test-issuer", res.getIssuer());
        Assert.assertArrayEquals(new String[]{"ROLE_TEST", "ROLE_SERVICE"},
                res.get("roles", List.class).toArray(new String[2]));
        Assert.assertTrue(res.get("service", Boolean.class));
        Assert.assertEquals("test-value", res.get("test", String.class));
    }

    private Claims decrypt(String token) {
        Resource resource = new ClassPathResource("client.crt");
        try {
            CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
            var cert = (X509Certificate) certificatefactory.generateCertificate(resource.getInputStream());
            var publicKey = cert.getPublicKey();
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(publicKey);
            var claims = parser.parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        }
    }

}
