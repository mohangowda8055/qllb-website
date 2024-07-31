package com.example.biservice.controller.fourwheeler;

import com.example.biservice.payload.fourwheeler.FourVBrandDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.fourwheeler.brand.FourVBrandService;
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

@WebMvcTest(controllers = FourVBrandController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class FourVBrandControllerTest {

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
    private FourVBrandService fourVBrandService;

    private FourVBrandDto mockFourVBrandDto;

    @BeforeEach
    void setUp() {
        mockFourVBrandDto = FourVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockFourVBrandDto = null;
    }

    @Test
    public void fourVBrandController_createBrand_returnCreated() throws Exception{
        //given
        FourVBrandDto inputBrand = FourVBrandDto.builder()
                .brandName("Brand A")
                .build();
        when(fourVBrandService.createBrand(any(FourVBrandDto.class))).thenReturn(mockFourVBrandDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/fourv/admin/brands")
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
    public void fourVBrandController_createBrand_returnBadRequest() throws Exception{
        //given
        FourVBrandDto inputBrand = FourVBrandDto.builder()
                .build();
        when(fourVBrandService.createBrand(any(FourVBrandDto.class))).thenReturn(mockFourVBrandDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/fourv/admin/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBrand)));
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void fourVBrandController_findAllBrands_returnFourVBrandList() throws Exception{
        //given
        List<FourVBrandDto> fourVBrands = List.of(
                FourVBrandDto.builder()
                        .brandId(1)
                        .brandName("Brand A")
                        .build(),
                FourVBrandDto.builder()
                        .brandId(2)
                        .brandName("Brand B")
                        .build()
        );
        when(fourVBrandService.findAllBrands()).thenReturn(fourVBrands);

        //when&then
        mockMvc.perform(get("/api/v1/fourv/brands"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void fourVBrandController_findBrandById_returnFourVBrand() throws Exception{
        when(fourVBrandService.findBrandById(anyInt())).thenReturn(mockFourVBrandDto);

        //when&then
        mockMvc.perform(get("/api/v1/fourv/brands/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.brandName").value(mockFourVBrandDto.getBrandName()));
    }

    @Test
    public void fourVBrandController_updateBrand_returnFourVBrand() throws Exception{
        //given
        FourVBrandDto brandToBeUpdated = FourVBrandDto.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();
        when(fourVBrandService.updateBrand(anyInt(),any(FourVBrandDto.class))).thenReturn(brandToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/fourv/admin/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.brandName").value(brandToBeUpdated.getBrandName()));
    }

    @Test
    public void fourVBrandController_updateBrand_returnBadRequest() throws Exception{
        //given
        FourVBrandDto brandToBeUpdated = FourVBrandDto.builder()
                .brandId(1)
                .build();
        when(fourVBrandService.updateBrand(anyInt(),any(FourVBrandDto.class))).thenReturn(brandToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/fourv/admin/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void fourVBrandController_deleteBrand_returnNull() throws Exception{
        //given
        doNothing().when(fourVBrandService).deleteBrand(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/fourv/admin/brands/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}