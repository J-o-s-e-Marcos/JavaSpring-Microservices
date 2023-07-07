package com.ead.authuser.controllers;


import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;


@RestController // indica classe é um controlador do Spring que trata solicitações HTTP e retorna objetos JSON.
@CrossOrigin(origins = "*", maxAge = 3600) //permite que as solicitações cruzadas (cross-origin) sejam aceitas pelo
// controlador. O asterisco (*) significa que qualquer domínio pode acessar o controlador e maxAge define o tempo em
// segundos em que o navegador pode armazenar em cache a resposta da solicitação.
@RequestMapping("/auth") //mapeia a URL /auth para o controlador
public class AuthenticationController {

        @Autowired //injeta a dependência do serviço UserService no controlador
        UserService userService;

        @PostMapping("/signup")//mapeia a URL /auth/signup para o método registerUser(), que manipula as
        // solicitações HTTP POST.
        public ResponseEntity<Object> registerUser(@RequestBody
                                                           @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto){
          if(userService.existsByUsername(userDto.getUsername())){
              return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Alredy Taken!");
          }
          if(userService.existsByEmail(userDto.getEmail())){
              return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Alredy Taken!");
          }
            var userModel = new UserModel();
            BeanUtils.copyProperties(userDto, userModel); //converte user dto em user model
            //prencher atributos que não contemplam no userDTO
            userModel.setUserStatus(UserStatus.ACTIVE);
            userModel.setUserType(UserType.STUDENT);
            userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
        }
}
