package com.pencilwith.apiserver.crew.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenMaker {

    public static void main(String[] args) {
        byte[] keyBytes = Decoders.BASE64.decode("c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK");
        Key key = Keys.hmacShaKeyFor(keyBytes);
        String authorities = "ROLE_USER, ROLE_ADMIN";
        Date expiration = new Date(9999999999999L);

        String jwt = Jwts.builder()
                .setSubject("user1")
                .claim("auth", authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiration)
                .compact();
        System.out.println(jwt + "\n");

        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
        System.out.println("expiration:" + claimsJws.getBody().getExpiration().toString());
    }
}
