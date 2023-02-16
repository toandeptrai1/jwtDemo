package com.example.demoJwt.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;
    public void processOAuthPostLogin(String username) {
        User existUser=repo.getUserByUsername(username);
        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(username+"@gmail.com");
            newUser.setUsername(username);
            newUser.setPassword("$2a$10$7qV.pCHvzhxiN8SJrVOJzenUiPp//0it9GPdnI0J/HtXGyKHOKoZm");
            newUser.setProvider(Provider.GOOGLE);
            newUser.addRole(new Role(3));


            repo.save(newUser);
        }

    }
}
