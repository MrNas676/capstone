package com.bank.services.user;

import com.bank.dto.SignupRequest;
import com.bank.dto.UserDto;
import com.bank.entity.User;
import com.bank.repo.UserRepo;
import com.bank.responce.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserDto createUser(SignupRequest signupRequest) throws Exception {
        User user = new User(signupRequest.getEmail(), new BCryptPasswordEncoder().encode(signupRequest.getPassword()), signupRequest.getName());
        user = userRepo.save(user);
        if (user == null)
            return  null;

        return user.mapUsertoUserDto();
    }


    public Boolean hasUserWithEmail(String email) {
        return userRepo.findFirstByEmail(email) != null;
    }

    @Override
    public UserDto getUser(Long userId) {
        UserDto userDto = null;
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isPresent()){
            userDto = optionalUser.get().mapUsertoUserDto();
        }
        return userDto;
    }

    public GeneralResponse updateUser(UserDto userDto)  {
        GeneralResponse response = new GeneralResponse();
        User user = null;
            Optional<User> userOptional = userRepo.findById(userDto.getId());
            if (userOptional.isPresent()) {
                user = userOptional.get();

                user.setName(userDto.getName());

                userRepo.save(user);
                response.setMessage("User Updated Successfully");
                response.setStatus(HttpStatus.CREATED);
                return response;
            } else {
                response.setStatus(HttpStatus.NOT_ACCEPTABLE);
                response.setMessage("User Not Found");
                return response;
            }

    }


}
