package com.corcino.users.infrastructure.component;

import com.corcino.users.domain.entities.Role;
import com.corcino.users.domain.entities.User;
import com.corcino.users.infrastructure.repository.RoleRepository;
import com.corcino.users.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LoadDataComponent implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SHA256Component sha256Component;

    @Autowired
    public LoadDataComponent(UserRepository userRepository, RoleRepository roleRepository, SHA256Component sha256Component) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.sha256Component = sha256Component;
    }

    @Override
    public void run(ApplicationArguments args) {
        Role defaultRole = roleRepository.findByName("DEFAULT");
        if (defaultRole == null) {
            defaultRole = new Role();
            defaultRole.setRoleId(UUID.randomUUID());
            defaultRole.setName("DEFAULT");

            roleRepository.save(defaultRole);
        }

        Role adminRole = roleRepository.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setRoleId(UUID.randomUUID());
            adminRole.setName("ADMIN");

            roleRepository.save(adminRole);
        }

        User adminUser = userRepository.findByEmail("admin@email.com");
        if (adminUser == null) {
            adminUser = new User();
            adminUser.setUserId(UUID.randomUUID());
            adminUser.setName("Administrador");
            adminUser.setEmail("admin@email.com");
            adminUser.setPass(sha256Component.hash("@Admin123"));
            adminUser.setRole(adminRole);

            userRepository.save(adminUser);
        }
    }
}


