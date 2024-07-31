package com.example.biservice.controller.inverter;

import com.example.biservice.payload.inverter.InverterCapacityDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.inverter.capacity.InverterCapacityService;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InverterCapacityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class InverterCapacityControllerTest {

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
    private InverterCapacityService inverterCapacityService;

    private InverterCapacityDto mockInverterCapacityDto;

    @BeforeEach
    void setUp() {
        mockInverterCapacityDto = InverterCapacityDto.builder()
                .capacityId(1)
                .capacity(12)
                .build();
    }

    @AfterEach
    void tearDown(){
        mockInverterCapacityDto = null;
    }

    @Test
    public void inverterCapacityController_createInverterCapacity_returnCreated() throws Exception{
        //given
        InverterCapacityDto inputInverterCapacity = InverterCapacityDto.builder()
                .capacity(12)
                .build();
        when(inverterCapacityService.createCapacity(any(InverterCapacityDto.class))).thenReturn(mockInverterCapacityDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/inverter/admin/capacities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputInverterCapacity)));
        //then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.capacity").value(inputInverterCapacity.getCapacity()));
    }

    @Test
    public void inverterCapacityController_createInverterCapacity_returnBadRequest() throws Exception{
        //given
        InverterCapacityDto inputInverterCapacity = InverterCapacityDto.builder()
                .build();
        when(inverterCapacityService.createCapacity(any(InverterCapacityDto.class))).thenReturn(mockInverterCapacityDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/inverter/admin/capacities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputInverterCapacity)));
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void inverterCapacityController_findAllInverterCapacities_returnInverterCapacityList() throws Exception{
        //given
        List<InverterCapacityDto> inverterCapacities = List.of(
                InverterCapacityDto.builder()
                        .capacityId(1)
                        .capacity(12)
                        .build(),
                InverterCapacityDto.builder()
                        .capacityId(2)
                        .capacity(24)
                        .build()
        );
        when(inverterCapacityService.findAllCapacities()).thenReturn(inverterCapacities);

        //when&then
        mockMvc.perform(get("/api/v1/inverter/capacities"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void inverterCapacityController_findInverterCapacityById_returnInverterCapacity() throws Exception{
        when(inverterCapacityService.findCapacityById(anyInt())).thenReturn(mockInverterCapacityDto);

        //when&then
        mockMvc.perform(get("/api/v1/inverter/capacities/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.capacity").value(mockInverterCapacityDto.getCapacity()));
    }

    @Test
    public void inverterCapacityController_updateInverterCapacity_returnInverterCapacity() throws Exception{
        //given
        InverterCapacityDto capacityToBeUpdated = InverterCapacityDto.builder()
                .capacityId(1)
                .capacity(14)
                .build();
        when(inverterCapacityService.updateCapacity(anyInt(),any(InverterCapacityDto.class))).thenReturn(capacityToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverter/admin/capacities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(capacityToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.capacity").value(capacityToBeUpdated.getCapacity()));
    }

    @Test
    public void inverterCapacityController_updateInverterCapacity_returnBadRequest() throws Exception{
        //given
        InverterCapacityDto capacityToBeUpdated = InverterCapacityDto.builder()
                .capacityId(1)
                .build();
        when(inverterCapacityService.updateCapacity(anyInt(),any(InverterCapacityDto.class))).thenReturn(capacityToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverter/admin/capacities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(capacityToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void inverterCapacityController_deleteInverterCapacity_returnNull() throws Exception{
        //given
        doNothing().when(inverterCapacityService).deleteCapacity(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/inverter/admin/capacities/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}