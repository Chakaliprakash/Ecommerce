package com.prakash.Ecomerce.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.prakash.Ecomerce.Model.User;
import com.prakash.Ecomerce.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

/**
 * Ensures exactly one ADMIN account exists on startup.
 *
 * Public /api/auth/register always creates USER accounts — it must never let
 * the client choose its own role. To promote someone to ADMIN, either:
 *   1) Let this seeder create the default admin below (override the
 *      credentials via ADMIN_USERNAME / ADMIN_PASSWORD env vars in real
 *      deployments — do not ship the fallback password to production), or
 *   2) Run: UPDATE users SET role='ADMIN' WHERE username='someone';
 */
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.seed.username:admin}")
    private String adminUsername;

    @Value("${admin.seed.password:admin}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        boolean anyAdminExists = !userRepo.findAll().isEmpty()
                && userRepo.findAll().stream().anyMatch(u -> "ADMIN".equals(u.getRole()));

        if (anyAdminExists) {
            return;
        }

        if (userRepo.findByUsername(adminUsername).isPresent()) {
            // Username taken by a non-admin account — don't silently overwrite it.
            log.warn("AdminSeeder: username '{}' already exists but is not an ADMIN. " +
                    "No admin account was created. Promote a user manually via SQL if needed.", adminUsername);
            return;
        }

        User admin = User.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .role("ADMIN")
                .build();
        userRepo.save(admin);

        log.info("AdminSeeder: created default admin account '{}'. " +
                "Log in and consider changing this password.", adminUsername);
    }
}
