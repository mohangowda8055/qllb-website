package com.example.biservice.service.user;

import com.example.biservice.entity.user.User;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.user.UserDto;
import com.example.biservice.repository.user.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.profiles.active=test")
class UserServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImp userServiceImp;

    private User mockUser;

    private UserDto mockUserDto;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .userId(1L)
                .firstName("First Name")
                .lastName("Last Name")
                .email("abc@gmail.com")
                .password("Abcd123$")
                .phoneNumber("1234567890")
                .build();
        mockUserDto = UserDto.builder()
                .userId(1L)
                .firstName("First Name")
                .lastName("Last Name")
                .email("abc@gmail.com")
                .password("Abcd123$")
                .phoneNumber("1234567890")
                .build();
        when(modelMapper.map(any(UserDto.class),eq(User.class))).thenReturn(mockUser);
        when(modelMapper.map(any(User.class),eq(UserDto.class))).thenReturn(mockUserDto);
    }

    @AfterEach
    void tearDown() {
        mockUser = null;
        mockUserDto = null;
    }

    @Test
    public void userService_getUserById_returnUser(){
        //given
        Long userId = 1L;
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(mockUser));

        //when
        UserDto response = userServiceImp.getUserById(userId);
        //then
        assertNotNull(response);
        assertEquals(mockUserDto.getEmail(),response.getEmail());
    }

    @Test
    public void userService_getUserById_throwResourceNotFoundException(){
        //given
        Long userId = 1L;
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> userServiceImp.getUserById(userId));
    }

    @Test
    public void userService_getUserByEmail_returnUser(){
        //given
        String phoneNumber = "1234567890";
        when(userRepo.findByPhoneNumber(anyString())).thenReturn(Optional.of(mockUser));

        //when
        UserDto response = userServiceImp.getUserByPhoneNumber(phoneNumber);
        //then
        assertNotNull(response);
        assertEquals(mockUserDto.getEmail(),response.getEmail());
    }

    @Test
    public void userService_getUserByEmail_throwResourceNotFoundException(){
        //given
        String phoneNumber = "1234567890";
        when(userRepo.findByPhoneNumber(anyString())).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> userServiceImp.getUserByPhoneNumber(phoneNumber));
    }

    @Test
    public void userService_activateOrDeactivateUser_returnNull(){
        //given
        int value = 1;
        Long userId = 1L;
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(userRepo.save(any(User.class))).thenReturn(null);

        //when
        assertAll(()->userServiceImp.activateOrDeactivateUser(value,userId));
    }

    @Test
    public void userService_activateOrDeactivateUser_throwResourceNotFoundException(){
        //given
        int value = 1;
        Long userId = 1L;
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenReturn(null);

        //when
        assertThrows(ResourceNotFoundException.class, () ->userServiceImp.activateOrDeactivateUser(value,userId));
    }

    @Test
    public void userService_updateUser_returnUser(){
        //given
        Long userId = 1L;
        UserDto userToBeUpdatedDto = UserDto.builder()
                .userId(1L)
                .firstName("First Name")
                .lastName("Last Name")
                .email("abcd@gmail.com")
                .password("Abcd123$")
                .phoneNumber("1234567890")
                .build();
        User userToBeUpdated = User.builder()
                .userId(1L)
                .firstName("First Name")
                .lastName("Last Name")
                .email("abcd@gmail.com")
                .password("Abcd123$")
                .phoneNumber("1234567890")
                .build();
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(userRepo.save(any(User.class))).thenReturn(userToBeUpdated);
        when(modelMapper.map(any(UserDto.class),eq(User.class))).thenReturn(userToBeUpdated);
        when(modelMapper.map(any(User.class),eq(UserDto.class))).thenReturn(userToBeUpdatedDto);

        //when
        UserDto response = userServiceImp.updateUser(userToBeUpdatedDto,userId);
        //then
        assertNotNull(response);
        assertEquals(userToBeUpdatedDto.getEmail(),response.getEmail());
    }

    @Test
    public void userService_updateUser_throwResourceNotFoundException(){
        //given
        Long userId = 1L;
        UserDto userToBeUpdatedDto = UserDto.builder()
                .userId(1L)
                .firstName("First Name")
                .lastName("Last Name")
                .email("abcd@gmail.com")
                .password("Abcd123$")
                .phoneNumber("1234567890")
                .build();
        User userToBeUpdated = User.builder()
                .userId(1L)
                .firstName("First Name")
                .lastName("Last Name")
                .email("abcd@gmail.com")
                .password("Abcd123$")
                .phoneNumber("1234567890")
                .build();
        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenReturn(userToBeUpdated);
        when(modelMapper.map(any(UserDto.class),eq(User.class))).thenReturn(userToBeUpdated);
        when(modelMapper.map(any(User.class),eq(UserDto.class))).thenReturn(userToBeUpdatedDto);

        //when&then
        assertThrows(ResourceNotFoundException.class, () ->userServiceImp.updateUser(userToBeUpdatedDto,userId));
    }

    @Test
    public void userService_updateUser_throwUniqueConstraintException(){
        //given
        Long userId = 1L;
        UserDto userToBeUpdatedDto = UserDto.builder()
                .userId(1L)
                .firstName("First Name")
                .lastName("Last Name")
                .email("abcd@gmail.com")
                .password("Abcd123$")
                .phoneNumber("1234567890")
                .build();
        User userToBeUpdated = User.builder()
                .userId(1L)
                .firstName("First Name")
                .lastName("Last Name")
                .email("abcd@gmail.com")
                .password("Abcd123$")
                .phoneNumber("1234567890")
                .build();
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(userRepo.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);
        when(modelMapper.map(any(UserDto.class),eq(User.class))).thenReturn(userToBeUpdated);
        when(modelMapper.map(any(User.class),eq(UserDto.class))).thenReturn(userToBeUpdatedDto);

        //when&then
        assertThrows(UniqueConstraintException.class, () ->userServiceImp.updateUser(userToBeUpdatedDto,userId));
    }
}

