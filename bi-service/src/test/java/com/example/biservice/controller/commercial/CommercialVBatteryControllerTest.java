package com.example.biservice.controller.commercial;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.commercial.CommercialVBatteryDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.commercial.battery.CommercialVBatteryService;
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

@WebMvcTest(controllers = CommercialVBatteryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CommercialVBatteryControllerTest {

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
    private CommercialVBatteryService commercialVBatteryService;

    private CommercialVBatteryDto mockCommercialVBatteryDto;

    @BeforeEach
    void setUp() {
        mockCommercialVBatteryDto = CommercialVBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .brandName("brand a")
                .voltage(12)
                .imageMainUrl("www.bgv.com")
                .mrp(3000.0000f)
                .discountPercentage(5.00f)
                .stock(4)
                .rebate(250.0000f)
                .guarantee(12)
                .warranty(12)
                .series("PRO")
                .ah(20)
                .terminalLayout("Left")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockCommercialVBatteryDto = null;
    }

    @Test
    public void commercialVBatteryController_createBattery_returnCreated() throws Exception{
        //given
        CommercialVBatteryDto inputBattery = CommercialVBatteryDto.builder()
                .modelName("abc")
                .brandName("brand a")
                .productName("product A")
                .voltage(12)
                .imageMainUrl("www.bgv.com")
                .mrp(3000.0000f)
                .discountPercentage(5.00f)
                .stock(4)
                .rebate(250.0000f)
                .guarantee(12)
                .warranty(12)
                .series("PRO")
                .ah(20)
                .terminalLayout("Left")
                .build();
        when(commercialVBatteryService.createBattery(any(CommercialVBatteryDto.class))).thenReturn(mockCommercialVBatteryDto);

        //when&then
        mockMvc.perform(post("/api/v1/commercialv/admin/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBattery)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inputBattery.getModelName()));
    }

    @Test
    public void commercialVBatteryController_createBattery_returnBadRequest() throws Exception{
        //given
        CommercialVBatteryDto inputBattery = CommercialVBatteryDto.builder()
                .build();
        when(commercialVBatteryService.createBattery(any(CommercialVBatteryDto.class))).thenReturn(mockCommercialVBatteryDto);

        //when&then
        mockMvc.perform(post("/api/v1/commercialv/admin/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBattery)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void commercialVBatteryController_findAllBattery_returnCommercialVBatteryList() throws Exception{
        //given
        List<CommercialVBatteryDto> commercialVBatteries = List.of(
                CommercialVBatteryDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                CommercialVBatteryDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(commercialVBatteryService.findAllBatteries(anyString())).thenReturn(commercialVBatteries);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sortBy","modelName"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void commercialVBatteryController_findAllBatteryByModel_returnCommercialVBatteryList() throws Exception{
        //given
        List<CommercialVBatteryDto> commercialVBatteries = List.of(
                CommercialVBatteryDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                CommercialVBatteryDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        PageResponse<CommercialVBatteryDto> pageResponse = PageResponse.<CommercialVBatteryDto>builder()
                .data(commercialVBatteries)
                .pageNumber(0)
                .build();
        when(commercialVBatteryService.findAllBatteriesByModel(anyInt(),anyInt(),anyInt(),anyString())).thenReturn(pageResponse);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/models/1/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNumber","0")
                        .param("pageSize","4")
                        .param("sortBy","modelName"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void commercialVBatteryController_findBatteryById_returnCommercialVBattery() throws Exception{
        //given
        when(commercialVBatteryService.findBatteryById(anyInt())).thenReturn(mockCommercialVBatteryDto);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(mockCommercialVBatteryDto.getModelName()));
    }

    @Test
    public void commercialVBatteryController_updateBattery_returnCommercialVBattery() throws Exception{
        //given
        CommercialVBatteryDto batteryToBeUpdated = CommercialVBatteryDto.builder()
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
                .guarantee(12)
                .warranty(12)
                .series("PRO")
                .ah(20)
                .terminalLayout("Left")
                .build();
        when(commercialVBatteryService.updateBattery(anyInt(),any(CommercialVBatteryDto.class))).thenReturn(batteryToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/commercialv/admin/batteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(batteryToBeUpdated.getModelName()));
    }

    @Test
    public void commercialVBatteryController_updateBattery_returnBadRequest() throws Exception{
        //given
        CommercialVBatteryDto batteryToBeUpdated = CommercialVBatteryDto.builder()
                .productId(1)
                .build();
        when(commercialVBatteryService.updateBattery(anyInt(),any(CommercialVBatteryDto.class))).thenReturn(batteryToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/commercialv/admin/batteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void commercialVBatteryController_deleteBattery_returnNull() throws Exception{
        //given
        doNothing().when(commercialVBatteryService).deleteBattery(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/commercialv/admin/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}