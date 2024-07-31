package com.example.biservice.controller.user;

import com.example.biservice.payload.user.UserDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private LogoutHandler logoutHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDto mockUserDto;

    @BeforeEach
    void setUp() {
        mockUserDto = UserDto.builder()
                .userId(1L)
                .firstName("Mohan")
                .email("abc@gmail.com")
                .password("Abcde123#")
                .phoneNumber("1234567890")
                .build();
    }

    @AfterEach
    void tearDown() {
        mockUserDto = null;
    }

    @Test
    public void userController_getUserById_returnUser() throws Exception{
        //given
        when(userService.getUserById(anyLong())).thenReturn(mockUserDto);

        //when&then
        mockMvc.perform(get("/api/v1/user/users/id/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.email").value(mockUserDto.getEmail()));
    }

    @Test
    public void userController_getUserByPhoneNumber_returnUser() throws Exception{
        //given
        when(userService.getUserByPhoneNumber(anyString())).thenReturn(mockUserDto);

        //when&then
        mockMvc.perform(get("/api/v1/user/phone/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("phone","1234567890"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.phoneNumber").value(mockUserDto.getPhoneNumber()));
    }

    @Test
    public void userController_activateOrDeactivateUser_returnNull() throws Exception{
        //given
        doNothing().when(userService).activateOrDeactivateUser(anyInt(),anyLong());

        //when&then
        mockMvc.perform(put("/api/v1/user/users/1/isActive/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void userController_updateUser_returnUser() throws Exception{
        //given
        UserDto userToBeUpdated = UserDto.builder()
                .userId(1L)
                .firstName("Gagan")
                .email("abc@gmail.com")
                .password("Abcde123#")
                .phoneNumber("1234567890")
                .build();
        when(userService.updateUser(any(UserDto.class),anyLong())).thenReturn(userToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/user/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.firstName").value(userToBeUpdated.getFirstName()));
    }

    @Test
    public void userController_updateUser_returnBadRequest() throws Exception{
        //given
        UserDto userToBeUpdated = UserDto.builder()
                .userId(1L)
                .firstName("Gagan")
                .email("abc@gmail.com")
                .password("Abcde123")
                .phoneNumber("1234567890")
                .build();
        when(userService.updateUser(any(UserDto.class),anyLong())).thenReturn(userToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/user/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}