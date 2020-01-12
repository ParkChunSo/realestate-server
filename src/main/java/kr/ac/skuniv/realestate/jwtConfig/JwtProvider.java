package kr.ac.skuniv.realestate.jwtConfig;

import io.jsonwebtoken.*;
import kr.ac.skuniv.realestate.domain.enums.MemberRole;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * ########## JWT Token Handler ##########
 * Jwt 관련 함수 모음.
 *
 * @author chunso
 */
public final class JwtProvider {
    @Value("${jwt.secret}")
    private static String secretKey;
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    /**
     * 토큰을 발급한다.
     *
     * @param username: 사용자 아이디
     * @param roles: 사용자 권한
     * @return 해당 사용자의 토큰
     */
    public static String createToken(String username, Set<MemberRole> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_TOKEN_VALIDITY);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 토큰의 무결성 검사한다.
     * 1. secretKey가 일치하는지
     * 2. 유효기간이 일치하는지
     *
     * @param token: 토큰
     * @return 파싱한 토큰 바디
     * @throws JwtException: secretKey가 잘못들어가있거나 유효기간이 지난 토큰일 경우 발생
     * @throws IllegalArgumentException: 토큰을 파싱할 수 없을 경우 발생
     */
    public static boolean validateToken(String token) throws JwtException, IllegalArgumentException {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claims.getBody().getExpiration().after(new Date());
    }

    /**
     *request의 헤더를 가져와 Bearer 방식인지 확인 후 실 토큰값을 리턴한다.
     *
     * @param req: request 전체
     * @return 파싱 후 실제 토큰값
     */
    public static String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 토큰이 Bearer 방식인지 확인 후 실 토큰값을 리턴한다.
     *
     * @param bearerToken: request의 헤더 토큰
     * @return 파싱 후 실제 토큰값
     */
    public static String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 파싱된 토큰에서 사용자의 아이디를 추출한다.
     *
     * @param bearerToken: 파싱된 토큰
     * @return 사용자 아이디
     */
    public static String  getUserEmailByResolvedToken(String bearerToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(bearerToken).getBody()
                .getSubject();
    }

    /**
     * 파싱되지 않은 토큰에서 사용자의 아이디를 추출한다.
     *
     * @param bearerToken: 파싱되지 않은 토큰
     * @return 사용자 아이디
     */
    public static String getUserEmailByToken(String bearerToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(resolveToken(bearerToken))
                .getBody().getSubject();
    }

    /**
     * 파싱되지 않은 토큰에서 사용자의 권한을 추출한다.
     *
     * @param bearerToken: 파싱되지 않은 토큰
     * @return 사용자 권한
     */
    public static List<MemberRole> getUserRolesByToken(String bearerToken) {
        Set<MemberRole> roles = new HashSet<>();
        List list = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(resolveToken(bearerToken))
                .getBody().get("roles", List.class);
        for (Object role : list) {
            if (String.valueOf(role).equals("USER")) {
                roles.add(MemberRole.USER);
            } else {
                roles.add(MemberRole.ADMIN);
            }
        }
        return new ArrayList<>(roles);
    }
}
