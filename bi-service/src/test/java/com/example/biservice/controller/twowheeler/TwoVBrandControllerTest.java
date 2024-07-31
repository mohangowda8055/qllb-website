package com.example.biservice.controller.twowheeler;

import com.example.biservice.payload.twowheeler.TwoVBrandDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.twowheeler.brand.TwoVBrandService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TwoVBrandController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TwoVBrandControllerTest {

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
    private TwoVBrandService twoVBrandService;

    private TwoVBrandDto mockTwoVBrandDto;

    @BeforeEach
    void setUp() {
        mockTwoVBrandDto = TwoVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockTwoVBrandDto = null;
    }

    @Test
    public void twoVBrandController_createBrand_returnCreated() throws Exception{
        //given
        TwoVBrandDto inputBrand = TwoVBrandDto.builder()
                .brandName("Brand A")
                .build();
        when(twoVBrandService.createBrand(any(TwoVBrandDto.class))).thenReturn(mockTwoVBrandDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/twov/admin/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBrand)));
        //then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.brandName").value(inputBrand.getBrandName()));
    }

    @Test
    public void twoVBrandController_createBrand_returnBadRequest() throws Exception{
        //given
        TwoVBrandDto inputBrand = TwoVBrandDto.builder()
                .build();
        when(twoVBrandService.createBrand(any(TwoVBrandDto.class))).thenReturn(mockTwoVBrandDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/twov/admin/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBrand)));
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void twoVBrandController_findAllBrands_returnTwoVBrandList() throws Exception{
        //given
        List<TwoVBrandDto> twoVBrands = List.of(
                TwoVBrandDto.builder()
                        .brandId(1)
                        .brandName("Brand A")
                        .build(),
                TwoVBrandDto.builder()
                        .brandId(2)
                        .brandName("Brand B")
                        .build()
        );
        when(twoVBrandService.findAllBrands()).thenReturn(twoVBrands);

        //when&then
        mockMvc.perform(get("/api/v1/twov/brands"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void twoVBrandController_findBrandById_returnTwoVBrand() throws Exception{
        when(twoVBrandService.findBrandById(anyInt())).thenReturn(mockTwoVBrandDto);

        //when&then
        mockMvc.perform(get("/api/v1/twov/brands/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.brandName").value(mockTwoVBrandDto.getBrandName()));
    }

    @Test
    public void twoVBrandController_updateBrand_returnTwoVBrand() throws Exception{
        //given
        TwoVBrandDto brandToBeUpdated = TwoVBrandDto.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();
        when(twoVBrandService.updateBrand(anyInt(),any(TwoVBrandDto.class))).thenReturn(brandToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/twov/admin/brands/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.brandName").value(brandToBeUpdated.getBrandName()));
    }

    @Test
    public void twoVBrandController_updateBrand_returnBadRequest() throws Exception{
        //given
        TwoVBrandDto brandToBeUpdated = TwoVBrandDto.builder()
                .brandId(1)
                .build();
        when(twoVBrandService.updateBrand(anyInt(),any(TwoVBrandDto.class))).thenReturn(brandToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/twov/admin/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void twoVBrandController_deleteBrand_returnNull() throws Exception{
        //given
        doNothing().when(twoVBrandService).deleteBrand(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/twov/admin/brands/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}