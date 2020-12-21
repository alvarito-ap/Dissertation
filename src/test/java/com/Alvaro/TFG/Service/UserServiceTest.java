package com.Alvaro.TFG.Service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;

import com.Alvaro.TFG.Repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.Alvaro.TFG.Model.User;
import com.Alvaro.TFG.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {
	
	@Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository roleUserRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService adminServiceUnderTest;
    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        adminServiceUnderTest = new UserService(mockUserRepository, bCryptPasswordEncoder, roleUserRepository);
        user = new User();user.setName("alvaro");user.setPassword("password");user.setStatus(true);user.setUsername("alvaro");

        Mockito.when(mockUserRepository.save(any()))
                .thenReturn(user);
        Mockito.when(mockUserRepository.findUserByName(anyString()))
                .thenReturn(user);
    }

    @Test
    public void testFindUserByEmail() {
        // Setup
        final String name = "alvaro";

        // Run the test
        final User result = adminServiceUnderTest.findUserByName(name);

        // Verify the results
        assertEquals(name, result.getName());
    }

    @Test
    public void testSaveUser() {
        // Setup
        final String name = "alvaro";

        // Run the test
        User result = adminServiceUnderTest.saveUser(user);

        // Verify the results
        assertEquals(name, result.getName());
    }
}
