package com.pencilwith.apiserver.crew.util;

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
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 365 * 10);

        System.out.println(Jwts.builder()
                .setSubject("user1")
                .claim("auth", authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiration)
                .compact()
        );
    }
}
