package com.example.biservice.controller.inverterbattery;

import com.example.biservice.payload.inverterbattery.InverterBatteryWarrantyDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.inverterbattery.inverterbatterywarranty.InverterBatteryWarrantyService;
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

@WebMvcTest(controllers = InverterBatteryWarrantyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class InverterBatteryWarrantyControllerTest {

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
    private InverterBatteryWarrantyService inverterBatteryWarrantyService;

    private InverterBatteryWarrantyDto mockInverterBatteryWarrantyDto;

    @BeforeEach
    void setUp() {
        mockInverterBatteryWarrantyDto = InverterBatteryWarrantyDto.builder()
                .warrantyId(1)
                .warranty(12)
                .guarantee(12)
                .build();
    }

    @AfterEach
    void tearDown(){
        mockInverterBatteryWarrantyDto = null;
    }

    @Test
    public void inverterBatteryWarrantyController_createInverterBatteryWarranty_returnCreated() throws Exception{
        //given
        InverterBatteryWarrantyDto inputInverterBatteryWarranty = InverterBatteryWarrantyDto.builder()
                .warranty(12)
                .guarantee(12)
                .build();
        when(inverterBatteryWarrantyService.createInverterBatteryWarranty(any(InverterBatteryWarrantyDto.class))).thenReturn(mockInverterBatteryWarrantyDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/inverterbattery/admin/warranties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputInverterBatteryWarranty)));
        //then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.warranty").value(inputInverterBatteryWarranty.getWarranty()));
    }

    @Test
    public void inverterBatteryWarrantyController_createInverterBatteryWarranty_returnBadRequest() throws Exception{
        //given
        InverterBatteryWarrantyDto inputInverterBatteryWarranty = InverterBatteryWarrantyDto.builder()
                .build();
        when(inverterBatteryWarrantyService.createInverterBatteryWarranty(any(InverterBatteryWarrantyDto.class))).thenReturn(mockInverterBatteryWarrantyDto);
        //when
        ResultActions response = mockMvc.perform(post("/api/v1/inverterbattery/admin/warranties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputInverterBatteryWarranty)));
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void inverterBatteryWarrantyController_findAllInverterBatteryWarranties_returnInverterBatteryWarrantyList() throws Exception{
        //given
        List<InverterBatteryWarrantyDto> inverterBatteryWarranties = List.of(
                InverterBatteryWarrantyDto.builder()
                        .warrantyId(1)
                        .warranty(12)
                        .guarantee(12)
                        .build(),
                InverterBatteryWarrantyDto.builder()
                        .warrantyId(2)
                        .warranty(24)
                        .guarantee(24)
                        .build()
        );
        when(inverterBatteryWarrantyService.findAllInverterBatteryWarranties()).thenReturn(inverterBatteryWarranties);

        //when&then
        mockMvc.perform(get("/api/v1/inverterbattery/warranties"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void inverterBatteryWarrantyController_findInverterBatteryWarrantyById_returnInverterBatteryWarranty() throws Exception{
        when(inverterBatteryWarrantyService.findInverterBatteryWarrantyById(anyInt())).thenReturn(mockInverterBatteryWarrantyDto);

        //when&then
        mockMvc.perform(get("/api/v1/inverterbattery/warranties/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.warranty").value(mockInverterBatteryWarrantyDto.getWarranty()));
    }

    @Test
    public void inverterBatteryWarrantyController_updateInverterBatteryWarranty_returnInverterBatteryWarranty() throws Exception{
        //given
        InverterBatteryWarrantyDto inverterBatteryWarrantyToBeUpdated = InverterBatteryWarrantyDto.builder()
                .warrantyId(1)
                .warranty(14)
                .guarantee(14)
                .build();
        when(inverterBatteryWarrantyService.updateInverterBatteryWarranty(anyInt(),any(InverterBatteryWarrantyDto.class))).thenReturn(inverterBatteryWarrantyToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverterbattery/admin/warranties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inverterBatteryWarrantyToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.warranty").value(inverterBatteryWarrantyToBeUpdated.getWarranty()));
    }

    @Test
    public void inverterBatteryWarrantyController_updateInverterBatteryWarranty_returnBadRequest() throws Exception{
        //given
        InverterBatteryWarrantyDto inverterBatteryWarrantyToBeUpdated = InverterBatteryWarrantyDto.builder()
                .warrantyId(1)
                .build();
        when(inverterBatteryWarrantyService.updateInverterBatteryWarranty(anyInt(),any(InverterBatteryWarrantyDto.class))).thenReturn(inverterBatteryWarrantyToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverterbattery/admin/warranties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inverterBatteryWarrantyToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void inverterBatteryWarrantyController_deleteInverterBatteryWarranty_returnNull() throws Exception{
        //given
        doNothing().when(inverterBatteryWarrantyService).deleteInverterBatteryWarranty(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/inverterbattery/admin/warranties/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}