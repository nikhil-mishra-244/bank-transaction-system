package com.weetech.bank_transaction_system.service;


import com.weetech.bank_transaction_system.entity.Users;
import com.weetech.bank_transaction_system.enums.Role;
import com.weetech.bank_transaction_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


//    Create New User
    public void createUser(Users user){

        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("User already exists!");
        }

        user.setRole(Role.USER);
        userRepository.save(user);
    }

//    Remove User
    public void removeUser(long id){
        userRepository.deleteById(id);
    }

//    Get All User
    public List<Users> fetchAllUser(){
        return userRepository.findAll();
    }

//    Get One User By ID
    public Optional<Users> fetchById(long id){
        return userRepository.findById(id);
    }


}
