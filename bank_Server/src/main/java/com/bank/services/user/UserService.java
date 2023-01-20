package com.bank.services.user;


import com.bank.dto.SignupRequest;
import com.bank.dto.UserDto;
import com.bank.responce.GeneralResponse;

public interface UserService {

     UserDto createUser(SignupRequest signupRequest) throws Exception;

     Boolean hasUserWithEmail(String email);

     UserDto getUser(Long userId);

     GeneralResponse updateUser(UserDto userDto);

}
