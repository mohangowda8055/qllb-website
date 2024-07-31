package com.example.biservice.controller.commercial;

import com.example.biservice.payload.commercial.CommercialVBrandDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.commercial.brand.CommercialVBrandService;
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

@WebMvcTest(controllers = CommercialVBrandController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CommercialVBrandControllerTest {

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
    private CommercialVBrandService commercialVBrandService;

    private CommercialVBrandDto mockCommercialVBrandDto;

    @BeforeEach
    void setUp() {
        mockCommercialVBrandDto = CommercialVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockCommercialVBrandDto = null;
    }

    @Test
    public void commercialVBrandController_createBrand_returnCreated() throws Exception{
        //given
        CommercialVBrandDto inputBrand = CommercialVBrandDto.builder()
                .brandName("Brand A")
                .build();
        when(commercialVBrandService.createBrand(any(CommercialVBrandDto.class))).thenReturn(mockCommercialVBrandDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/commercialv/admin/brands")
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
    public void commercialVBrandController_createBrand_returnBadRequest() throws Exception{
        //given
        CommercialVBrandDto inputBrand = CommercialVBrandDto.builder()
                .build();
        when(commercialVBrandService.createBrand(any(CommercialVBrandDto.class))).thenReturn(mockCommercialVBrandDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/commercialv/admin/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBrand)));
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void commercialVBrandController_findAllBrands_returnCommercialVBrandList() throws Exception{
        //given
        List<CommercialVBrandDto> commercialVBrands = List.of(
                CommercialVBrandDto.builder()
                        .brandId(1)
                        .brandName("Brand A")
                        .build(),
                CommercialVBrandDto.builder()
                        .brandId(2)
                        .brandName("Brand B")
                        .build()
        );
        when(commercialVBrandService.findAllBrands()).thenReturn(commercialVBrands);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/brands"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void commercialVBrandController_findBrandById_returnCommercialVBrand() throws Exception{
        when(commercialVBrandService.findBrandById(anyInt())).thenReturn(mockCommercialVBrandDto);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/brands/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.brandName").value(mockCommercialVBrandDto.getBrandName()));
    }

    @Test
    public void commercialVBrandController_updateBrand_returnCommercialVBrand() throws Exception{
        //given
        CommercialVBrandDto brandToBeUpdated = CommercialVBrandDto.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();
        when(commercialVBrandService.updateBrand(anyInt(),any(CommercialVBrandDto.class))).thenReturn(brandToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/commercialv/admin/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.brandName").value(brandToBeUpdated.getBrandName()));
    }

    @Test
    public void commercialVBrandController_updateBrand_returnBadRequest() throws Exception{
        //given
        CommercialVBrandDto brandToBeUpdated = CommercialVBrandDto.builder()
                .brandId(1)
                .build();
        when(commercialVBrandService.updateBrand(anyInt(),any(CommercialVBrandDto.class))).thenReturn(brandToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/commercialv/admin/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void commercialVBrandController_deleteBrand_returnNull() throws Exception{
        //given
        doNothing().when(commercialVBrandService).deleteBrand(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/commercialv/admin/brands/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void commercialVBrandController_addSegmentToBrand_returnNull() throws Exception{
        //given
        doNothing().when(commercialVBrandService).addSegmentToBrand(anyInt(),anyInt());

        //when&then
        mockMvc.perform(post("/api/v1/commercialv/admin/brands/1/segments/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}