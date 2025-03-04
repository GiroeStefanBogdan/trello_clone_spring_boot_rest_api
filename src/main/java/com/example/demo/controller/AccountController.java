package com.example.demo.controller;

import com.example.demo.entities.AppUser;
import com.example.demo.entities.Role;
import com.example.demo.model.LoginDto;
import com.example.demo.model.RegistrationDto;
import com.example.demo.repositories.AppUserRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.AuthService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    @Value("${security.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;


    @GetMapping("/profile")
    public ResponseEntity<Object> profile(Authentication auth) {
        var response = new HashMap<String, Object>();
        response.put("email", auth.getName());
        response.put("Authorities", auth.getAuthorities());

        var appUser = appUserRepository.findByEmail(auth.getName());
        response.put("User", appUser);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegistrationDto registrationDto,
            BindingResult result){
        if(result.hasErrors()){
            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i=0; i< errorsList.size(); i++){
                var error = (FieldError) errorsList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorsMap);
        }

        var bCryptEncoder = new BCryptPasswordEncoder();

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role not found")); // Handle missing role

        Set<Role> roles = new HashSet<>();
        roles.add(role); // ✅ Wrap role inside a Set

        AppUser appUser = AppUser.builder()
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .email(registrationDto.getEmail())
                .roles(roles)
                .pass(bCryptEncoder.encode(registrationDto.getPass()))
                .build();


        try{
            //check if email are used or not
            var otherUser = appUserRepository.findByEmail(registrationDto.getEmail());
            if(otherUser != null){
                return ResponseEntity.badRequest().body("Email already used");
            }
            appUserRepository.save(appUser);

            String jwtToken = createJwtToken(appUser);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", appUser);

            return ResponseEntity.ok(response);
        }catch (Exception e){
            System.out.println("There is an Exception :");
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Error");
    }



    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @Valid @RequestBody LoginDto loginDto,
            BindingResult result){

        if(result.hasErrors()){
            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for (int i=0; i< errorsList.size(); i++){
                var error = (FieldError) errorsList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorsMap);
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPass()
                    )
            );
            AppUser appUser = appUserRepository.findByEmail(loginDto.getEmail());

            String jwtToken = createJwtToken(appUser);
            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", appUser);

            return ResponseEntity.ok(response);
        }catch(Exception e){
            System.out.println("There is an Exception :");
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Bad email or password");

    }


    private String createJwtToken(AppUser appUser) {
        Instant now = Instant.now();

        Set<String> roleName = appUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24 * 3600))
                .subject(appUser.getEmail())
                .claim("role", roleName)
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return encoder.encode(params).getTokenValue();

    }
}
