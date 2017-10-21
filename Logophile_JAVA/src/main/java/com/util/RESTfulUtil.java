package com.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class RESTfulUtil {
    public static AtomicInteger JWTIDcount = new AtomicInteger(0);
    public static final String JWTApiKey = "A01092@_wWQD";
    public static final String JWTIssuerID = "100200300";
    public static final String JWTSubject = "someSubject";
    
    public static Long expireTime = -1000L;
    
  //Sample method to construct a JWT
    public static String createJWT() {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JWTApiKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(String.valueOf(JWTIDcount.incrementAndGet()))
                                    .setIssuedAt(now)
                                    .setSubject(JWTSubject)
                                    .setIssuer(JWTIssuerID)
                                    .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (expireTime >= 0) {
        long expMillis = nowMillis + expireTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
    
    //Sample method to validate and read the JWT
    public static void parseJWT(String jwt) {
     
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()         
           .setSigningKey(DatatypeConverter.parseBase64Binary(JWTApiKey))
           .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }
}
