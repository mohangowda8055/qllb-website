package com.example.biservice.controller.threewheeler;

import com.example.biservice.payload.PageResponse;
import com.example.biservice.payload.threewheeler.ThreeVBatteryDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.threewheeler.battery.ThreeVBatteryService;
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

@WebMvcTest(controllers = ThreeVBatteryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ThreeVBatteryControllerTest {

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
    private ThreeVBatteryService threeVBatteryService;

    private ThreeVBatteryDto mockThreeVBatteryDto;

    @BeforeEach
    void setUp() {
        mockThreeVBatteryDto = ThreeVBatteryDto.builder()
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
        mockThreeVBatteryDto = null;
    }

    @Test
    public void threeVBatteryController_createBattery_returnCreated() throws Exception{
        //given
        ThreeVBatteryDto inputBattery = ThreeVBatteryDto.builder()
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
        when(threeVBatteryService.createBattery(any(ThreeVBatteryDto.class))).thenReturn(mockThreeVBatteryDto);

        //when&then
        mockMvc.perform(post("/api/v1/threev/admin/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBattery)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inputBattery.getModelName()));
    }

    @Test
    public void threeVBatteryController_createBattery_returnBadRequest() throws Exception{
        //given
        ThreeVBatteryDto inputBattery = ThreeVBatteryDto.builder()
                .build();
        when(threeVBatteryService.createBattery(any(ThreeVBatteryDto.class))).thenReturn(mockThreeVBatteryDto);

        //when&then
        mockMvc.perform(post("/api/v1/threev/admin/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBattery)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void threeVBatteryController_findAllBattery_returnThreeVBatteryList() throws Exception{
        //given
        List<ThreeVBatteryDto> threeVBatteries = List.of(
                ThreeVBatteryDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                ThreeVBatteryDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        when(threeVBatteryService.findAllBatteries(anyString())).thenReturn(threeVBatteries);

        //when&then
        mockMvc.perform(get("/api/v1/threev/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sortBy","modelName"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void threeVBatteryController_findAllBatteryByModel_returnThreeVBatteryList() throws Exception{
        //given
        List<ThreeVBatteryDto> threeVBatteries = List.of(
                ThreeVBatteryDto.builder()
                        .productId(1)
                        .modelName("abc")
                        .build(),
                ThreeVBatteryDto.builder()
                        .productId(2)
                        .modelName("acb")
                        .build()
        );
        PageResponse<ThreeVBatteryDto> pageResponse = PageResponse.<ThreeVBatteryDto>builder()
                .data(threeVBatteries)
                .pageNumber(0)
                .build();
        when(threeVBatteryService.findAllBatteriesByModelAndFuel(anyInt(),anyInt(),anyInt(),anyInt(),anyString())).thenReturn(pageResponse);

        //when&then
        mockMvc.perform(get("/api/v1/threev/models/1/fueltypes/1/batteries")
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
    public void threeVBatteryController_findBatteryById_returnThreeVBattery() throws Exception{
        //given
        when(threeVBatteryService.findBatteryById(anyInt())).thenReturn(mockThreeVBatteryDto);

        //when&then
        mockMvc.perform(get("/api/v1/threev/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(mockThreeVBatteryDto.getModelName()));
    }

    @Test
    public void threeVBatteryController_updateBattery_returnThreeVBattery() throws Exception{
        //given
        ThreeVBatteryDto batteryToBeUpdated = ThreeVBatteryDto.builder()
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
        when(threeVBatteryService.updateBattery(anyInt(),any(ThreeVBatteryDto.class))).thenReturn(batteryToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/threev/admin/batteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(batteryToBeUpdated.getModelName()));
    }

    @Test
    public void threeVBatteryController_updateBattery_returnBadRequest() throws Exception{
        //given
        ThreeVBatteryDto batteryToBeUpdated = ThreeVBatteryDto.builder()
                .productId(1)
                .build();
        when(threeVBatteryService.updateBattery(anyInt(),any(ThreeVBatteryDto.class))).thenReturn(batteryToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/threev/admin/batteries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batteryToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void threeVBatteryController_deleteBattery_returnNull() throws Exception{
        //given
        doNothing().when(threeVBatteryService).deleteBattery(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/threev/admin/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}