package com.example.ecommerce_backend.service;
import com.example.ecommerce_backend.exception.ErrorCode;
import com.example.ecommerce_backend.dto.request.AuthenticationRequest;
import com.example.ecommerce_backend.dto.request.IntrospectRequest;
import com.example.ecommerce_backend.dto.response.AuthenticationResponse;
import com.example.ecommerce_backend.dto.response.IntrospectResponse;
import com.example.ecommerce_backend.exception.AppException;
import com.example.ecommerce_backend.models.User;
import com.example.ecommerce_backend.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    String SIGNER_KEY;
    public IntrospectResponse introspect(IntrospectRequest request) {
//        System.out.println("introspect");
        String token = request.getToken();

        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY);

            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

            boolean valid = signedJWT.verify(verifier);

            return IntrospectResponse.builder()
                    .valid(valid && expiration.after(new Date()))
                    .build();

        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }

    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//        System.out.println(user.getPassword());
//        System.out.println(passwordEncoder.encode(request.getPassword()));
        boolean authenticated = passwordEncoder.matches((request.getPassword()), user.getPassword());
//        System.out.println(authenticated);
        if (authenticated) {

            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .isSuccess(true)
                .build();
    }

    private String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("nqt.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = claimsSet.toPayload();

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner joiner = new StringJoiner(" ");
        user.getRoles().forEach(
                role -> {
                    joiner.add("ROLE_" + role.getName());
                    if (!CollectionUtils.isEmpty(role.getPermissions()))
                        role.getPermissions().forEach(
                                permission -> joiner.add(permission.getName())
                        );
                }
        );

        return joiner.toString();
    }
}
