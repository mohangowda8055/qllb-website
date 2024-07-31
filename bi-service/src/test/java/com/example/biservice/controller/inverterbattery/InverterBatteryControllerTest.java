package com.example.biservice.controller.inverterbattery;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.inverterbattery.InverterBatteryDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.inverterbattery.inverterbattery.InverterBatteryService;
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

@WebMvcTest(controllers = InverterBatteryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class InverterBatteryControllerTest {

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
    private InverterBatteryService inverterBatteryService;

    private InverterBatteryDto mockInverterBatteryDto;

    @BeforeEach
    void setUp() {
        mockInverterBatteryDto = InverterBatteryDto.builder()
                .productId(1)
                .modelName("abc")
                .brandName("brand a")
                .voltage(12)
                .imageMainUrl("www.bgv.com")
                .mrp(3000.0000f)
                .discountPercentage(5.00f)
                .stock(4)
                .rebate(250.0000f)
                .series("PRO")
                .ah(20)
                .terminalLayout("Left")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockInverterBatteryDto = null;
    }

    @Test
    public void inverterBatteryController_createBattery_returnCreated() throws Exception{
        //given
        InverterBatteryDto inputBattery = InverterBatteryDto.builder()
                .modelName("abc")
                .productName("product A")
                .brandName("brand a")
                .voltage(12)
                .imageMainUrl("www.bgv.com")
                .mrp(3000.0000f)
                .discountPercentage(5.00f)
                .stock(4)
                .rebate(250.0000f)
                .series("PRO")
                .ah(20)
                .terminalLayout("Left")
                .build();
        when(inverterBatteryService.createInverterBattery(any(InverterBatteryDto.class))).thenReturn(mockInverterBatteryDto);

        //when&then
        mockMvc.perform(post("/api/v1/inverterbattery/admin/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBattery)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inputBattery.getModelName()));
    }

    @Test
    public void inverterBatteryController_createBattery_returnBadRequest() throws Exception{
        //given
        InverterBatteryDto inputBattery = InverterBatteryDto.builder()
                .build();
        when(inverterBatteryService.createInverterBattery(any(InverterBatteryDto.class))).thenReturn(mockInverterBatteryDto);

        //when&then
        mockMvc.perform(post("/api/v1/inverterbattery/admin/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBattery)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void inverterBatteryController_findAllBattery_returnInverterBatteryList() throws Exception{
        //given
        List<InverterBatteryDto> inverterBatteries = List.of(
                InverterBatteryDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                InverterBatteryDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(inverterBatteryService.findAllInverterBatteries(anyString())).thenReturn(inverterBatteries);

        //when&then
        mockMvc.perform(get("/api/v1/inverterbattery/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sortBy","modelName"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void inverterBatteryController_findAllBatteriesByBackupDurationAndWarranty_returnInverterBatteryList() throws Exception{
        //given
        List<InverterBatteryDto> inverterBatteries = List.of(
                InverterBatteryDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                InverterBatteryDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        PageResponse<InverterBatteryDto> pageResponse = PageResponse.<InverterBatteryDto>builder()
                .data(inverterBatteries)
                .pageNumber(0)
                .build();
        when(inverterBatteryService.findAllInverterBatteriesByBackupDurationAndWarranty(anyInt(),anyInt(),anyInt(),anyInt(),anyString())).thenReturn(pageResponse);

        //when&then
        mockMvc.perform(get("/api/v1/inverterbattery/backupdurations/1/warranties/1/batteries")
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
    public void inverterBatteryController_findBatteryById_returnInverterBattery() throws Exception{
        //given
        when(inverterBatteryService.findInverterBatteryById(anyInt())).thenReturn(mockInverterBatteryDto);

        //when&then
        mockMvc.perform(get("/api/v1/inverterbattery/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(mockInverterBatteryDto.getModelName()));
    }

    @Test
    public void inverterBatteryController_updateBattery_returnInverterBattery() throws Exception{
        //given
        InverterBatteryDto batteryToBeUpdated = InverterBatteryDto.builder()
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
                .series("PRO")
                .ah(20)
                .terminalLayout("Left")
                .build();
        when(inverterBatteryService.updateInverterBattery(anyInt(),any(InverterBatteryDto.class))).thenReturn(batteryToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverterbattery/admin/batteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(batteryToBeUpdated.getModelName()));
    }

    @Test
    public void inverterBatteryController_updateBattery_returnBadRequest() throws Exception{
        //given
        InverterBatteryDto batteryToBeUpdated = InverterBatteryDto.builder()
                .productId(1)
                .build();
        when(inverterBatteryService.updateInverterBattery(anyInt(),any(InverterBatteryDto.class))).thenReturn(batteryToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverterbattery/admin/batteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void inverterBatteryController_deleteBattery_returnNull() throws Exception{
        //given
        doNothing().when(inverterBatteryService).deleteInverterBattery(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/inverterbattery/admin/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void inverterBatteryController_addBackupDurationWithWarrantyToBattery_returnNull() throws Exception{
        //given
        doNothing().when(inverterBatteryService).addBackupDurationWithWarrantyToBattery(anyInt(),anyInt(),anyInt());

        //when&then
        mockMvc.perform(post("/api/v1/inverterbattery/admin/backupdurations/1/warranties/1/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}