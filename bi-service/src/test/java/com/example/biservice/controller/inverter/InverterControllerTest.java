package com.example.biservice.controller.inverter;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverter.InverterDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.inverter.inverter.InverterService;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InverterController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class InverterControllerTest {

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
    private InverterService inverterService;

    private InverterDto mockInverterDto;

    @BeforeEach
    void setUp() {
        mockInverterDto = InverterDto.builder()
                .productId(1)
                .modelName("abc")
                .brandName("brand a")
                .voltage(12)
                .imageMainUrl("www.bgv.com")
                .mrp(3000.0000f)
                .discountPercentage(5.00f)
                .stock(4)
                .rebate(0)
                .noOfBatteryReq(2)
                .warranty(12)
                .inverterType("Type A")
                .recBatteryCapacity("100-150")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockInverterDto = null;
    }

    @Test
    public void inverterController_createInverter_returnCreated() throws Exception{
        //given
        InverterDto inputInverter = InverterDto.builder()
                .modelName("abc")
                .productName("product A")
                .brandName("brand a")
                .voltage(12)
                .imageMainUrl("www.bgv.com")
                .mrp(3000.0000f)
                .discountPercentage(5.00f)
                .stock(4)
                .rebate(0)
                .noOfBatteryReq(2)
                .warranty(12)
                .inverterType("Type A")
                .recBatteryCapacity("100-150")
                .build();
        when(inverterService.createInverter(any(InverterDto.class),anyInt())).thenReturn(mockInverterDto);

        //when
        mockMvc.perform(post("/api/v1/inverter/admin/capacities/1/inverters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputInverter)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inputInverter.getModelName()));
    }

    @Test
    public void inverterController_createInverter_returnBadRequest() throws Exception{
        //given
        InverterDto inputInverter = InverterDto.builder()
                .build();
        when(inverterService.createInverter(any(InverterDto.class),anyInt())).thenReturn(mockInverterDto);

        //when&then
        mockMvc.perform(post("/api/v1/inverter/admin/capacities/1/inverters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputInverter)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void inverterController_findAllInverters_returnInverterList() throws Exception{
        //given
        List<InverterDto> inverters = List.of(
                InverterDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                InverterDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(inverterService.findAllInverters(anyString())).thenReturn(inverters);

        //when&then
        mockMvc.perform(get("/api/v1/inverter/inverters"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void inverterController_findAllInvertersByInverterCapacity_returnInverterList() throws Exception{
        //given
        List<InverterDto> inverters = List.of(
                InverterDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                InverterDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        PageResponse<InverterDto> pageResponse = PageResponse.<InverterDto>builder()
                .data(inverters)
                .pageNumber(0)
                .build();
        when(inverterService.findAllInvertersByInverterCapacity(anyInt(),anyInt(),anyInt(),anyString())).thenReturn(pageResponse);

        //when&then
        mockMvc.perform(get("/api/v1/inverter/capacities/1/inverters"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void inverterController_findInverterById_returnInverter() throws Exception{
        //given
        when(inverterService.findInverterById(anyInt())).thenReturn(mockInverterDto);

        //when&then
        mockMvc.perform(get("/api/v1/inverter/inverters/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(mockInverterDto.getModelName()));
    }

    @Test
    public void inverterController_updateInverter_returnInverter() throws Exception{
        //given
        InverterDto inverterToBeUpdated = InverterDto.builder()
                .productId(1)
                .modelName("abc")
                .productName("product A")
                .brandName("brand a")
                .voltage(12)
                .imageMainUrl("www.bgv.com")
                .mrp(3000.0000f)
                .discountPercentage(5.00f)
                .stock(4)
                .rebate(250.0000f)
                .noOfBatteryReq(2)
                .warranty(12)
                .inverterType("Type A")
                .recBatteryCapacity("100-150")
                .build();
        when(inverterService.updateInverter(anyInt(),any(InverterDto.class))).thenReturn(inverterToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverter/admin/inverters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inverterToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inverterToBeUpdated.getModelName()));
    }

    @Test
    public void inverterController_updateInverter_returnBadRequest() throws Exception{
        //given
        InverterDto inverterToBeUpdated = InverterDto.builder()
                .productId(1)
                .build();
        when(inverterService.updateInverter(anyInt(),any(InverterDto.class))).thenReturn(inverterToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverter/admin/inverters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inverterToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void inverterController_deleteInverter_returnNull() throws Exception{
        //given
        doNothing().when(inverterService).deleteInverter(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/inverter/admin/inverters/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}