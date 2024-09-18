package com.PI.Back.PIBackend.jwt;

import com.PI.Back.PIBackend.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final String SECRET_KEY = "aB1cD2eF3gH4iJ5kL6mN7oP8qR9sT0uVaB1cD2eF3gH4iJ5kL6mN7oP8qR9sT0uV";
    // comente esto porque no se estaban pasando las claims
    //    public String getToken(UserDetails usuario) {
//        return getToken(new HashMap<>(), usuario);
//    }

    // en este metodo se agregan las claims y el rol del usuario
    public String getToken(UserDetails usuario) {
        Usuario userClaims = (Usuario) usuario;
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("nombre", userClaims.getNombre());
        claims.put("apellido", userClaims.getApellido());
        return getToken(claims, usuario);
    }
//    private static final String SECRET_KEY = "5552020scscS584784ddc1aDW23E541521202CDEWFECDWFWVDSDSA235482E";
//    public String getToken(Usuario usuario) {
//        return getToken(new HashMap<>(), usuario);
//


    private String getToken(Map<String, Object> extraClaims , UserDetails usuario){
//        extraClaims.put("nombre", usuario.getNombre());
//        extraClaims.put("apellido", usuario.getApellido());
//        extraClaims.put("email", usuario.getEmail());
//        extraClaims.put("role", usuario.getRole());
//        extraClaims.put("id", usuario.getId());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256 )
                .compact();
    }

    private Key getKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
                return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

}
