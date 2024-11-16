package com.hms.repository;

import com.hms.entity.AppUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AppUserRepositoryTest {



    @Autowired
    private AppUserRepository appUserRepository;
    AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setUsername("testUser");
        appUser.setName("Test Name");
        appUser.setEmail("test@example.com");
        appUser.setPassword("testPassword");
        appUser.setRole("USER");
        appUserRepository.save(appUser);
    }

    @AfterEach
    void tearDown() {
        appUser=null;
        appUserRepository.deleteAll();
    }
    //Test case SUCCESS

    @Test
    void testFindByUsername_Found() {
        AppUser foundUser = appUserRepository.findByUsername(appUser.getUsername()).orElse(null);
        assertThat(foundUser.getUsername()).isEqualTo(appUser.getUsername());
        assertThat(foundUser.getName()).isEqualTo(appUser.getName());
        assertThat(foundUser.getEmail()).isEqualTo(appUser.getEmail());
        assertThat(foundUser.getPassword()).isEqualTo(appUser.getPassword());
        assertThat(foundUser.getRole()).isEqualTo(appUser.getRole());
        assertThat(foundUser.getId()).isEqualTo(appUser.getId());
    }

    //Test case FAIL
    @Test
    void testFindByUsername_Not_Found() {
        AppUser foundUser = appUserRepository.findByUsername("nonExistingUser").orElse(null);
        assertThat(foundUser).isNull();
    }
}
