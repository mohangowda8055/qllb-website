package com.example.biservice.controller.fourwheeler;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.fourwheeler.FourVBatteryDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.fourwheeler.battery.FourVBatteryService;
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

@WebMvcTest(controllers = FourVBatteryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class FourVBatteryControllerTest {

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
    private FourVBatteryService fourVBatteryService;

    private FourVBatteryDto mockFourVBatteryDto;

    @BeforeEach
    void setUp() {
        mockFourVBatteryDto = FourVBatteryDto.builder()
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
        mockFourVBatteryDto = null;
    }

    @Test
    public void fourVBatteryController_createBattery_returnCreated() throws Exception{
        //given
        FourVBatteryDto inputBattery = FourVBatteryDto.builder()
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
        when(fourVBatteryService.createBattery(any(FourVBatteryDto.class))).thenReturn(mockFourVBatteryDto);

        //when&then
        mockMvc.perform(post("/api/v1/fourv/admin/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBattery)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inputBattery.getModelName()));
    }

    @Test
    public void fourVBatteryController_createBattery_returnBadRequest() throws Exception{
        //given
        FourVBatteryDto inputBattery = FourVBatteryDto.builder()
                .build();
        when(fourVBatteryService.createBattery(any(FourVBatteryDto.class))).thenReturn(mockFourVBatteryDto);

        //when&then
        mockMvc.perform(post("/api/v1/fourv/admin/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBattery)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void fourVBatteryController_findAllBattery_returnFourVBatteryList() throws Exception{
        //given
        List<FourVBatteryDto> fourVBatteries = List.of(
                FourVBatteryDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                FourVBatteryDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(fourVBatteryService.findAllBatteries(anyString())).thenReturn(fourVBatteries);

        //when&then
        mockMvc.perform(get("/api/v1/fourv/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sortBy","modelName"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void fourVBatteryController_findAllBatteriesByModelAndFuelType_returnFourVBatteryList() throws Exception{
        //given
        List<FourVBatteryDto> fourVBatteries = List.of(
                FourVBatteryDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                FourVBatteryDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        PageResponse<FourVBatteryDto> pageResponse = PageResponse.<FourVBatteryDto>builder()
                .data(fourVBatteries)
                .pageNumber(0)
                .build();
        when(fourVBatteryService.findAllBatteriesByModelAndFuelType(anyInt(),anyInt(),anyInt(),anyInt(),anyString())).thenReturn(pageResponse);

        //when&then
        mockMvc.perform(get("/api/v1/fourv/models/1/fueltypes/1/batteries")
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
    public void fourVBatteryController_findBatteryById_returnFourVBattery() throws Exception{
        //given
        when(fourVBatteryService.findBatteryById(anyInt())).thenReturn(mockFourVBatteryDto);

        //when&then
        mockMvc.perform(get("/api/v1/fourv/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(mockFourVBatteryDto.getModelName()));
    }

    @Test
    public void fourVBatteryController_updateBattery_returnFourVBattery() throws Exception{
        //given
        FourVBatteryDto batteryToBeUpdated = FourVBatteryDto.builder()
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
        when(fourVBatteryService.updateBattery(anyInt(),any(FourVBatteryDto.class))).thenReturn(batteryToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/fourv/admin/batteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(batteryToBeUpdated.getModelName()));
    }

    @Test
    public void fourVBatteryController_updateBattery_returnBadRequest() throws Exception{
        //given
        FourVBatteryDto batteryToBeUpdated = FourVBatteryDto.builder()
                .productId(1)
                .build();
        when(fourVBatteryService.updateBattery(anyInt(),any(FourVBatteryDto.class))).thenReturn(batteryToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/fourv/admin/batteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void fourVBatteryController_deleteBattery_returnNull() throws Exception{
        //given
        doNothing().when(fourVBatteryService).deleteBattery(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/fourv/admin/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}