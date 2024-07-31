package com.example.biservice.controller.threewheeler;

import com.example.biservice.payload.threewheeler.ThreeVBrandDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.threewheeler.brand.ThreeVBrandService;
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

@WebMvcTest(controllers = ThreeVBrandController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ThreeVBrandControllerTest {

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
    private ThreeVBrandService threeVBrandService;

    private ThreeVBrandDto mockThreeVBrandDto;

    @BeforeEach
    void setUp() {
        mockThreeVBrandDto = ThreeVBrandDto.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockThreeVBrandDto = null;
    }

    @Test
    public void threeVBrandController_createBrand_returnCreated() throws Exception{
        //given
        ThreeVBrandDto inputBrand = ThreeVBrandDto.builder()
                .brandName("Brand A")
                .build();
        when(threeVBrandService.createBrand(any(ThreeVBrandDto.class))).thenReturn(mockThreeVBrandDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/threev/admin/brands")
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
    public void threeVBrandController_createBrand_returnBadRequest() throws Exception{
        //given
        ThreeVBrandDto inputBrand = ThreeVBrandDto.builder()
                .build();
        when(threeVBrandService.createBrand(any(ThreeVBrandDto.class))).thenReturn(mockThreeVBrandDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/threev/admin/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBrand)));
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void threeVBrandController_findAllBrands_returnThreeVBrandList() throws Exception{
        //given
        List<ThreeVBrandDto> threeVBrands = List.of(
                ThreeVBrandDto.builder()
                        .brandId(1)
                        .brandName("Brand A")
                        .build(),
                ThreeVBrandDto.builder()
                        .brandId(2)
                        .brandName("Brand B")
                        .build()
        );
        when(threeVBrandService.findAllBrands()).thenReturn(threeVBrands);

        //when&then
        mockMvc.perform(get("/api/v1/threev/brands"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void threeVBrandController_findBrandById_returnThreeVBrand() throws Exception{
        when(threeVBrandService.findBrandById(anyInt())).thenReturn(mockThreeVBrandDto);

        //when&then
        mockMvc.perform(get("/api/v1/threev/brands/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.brandName").value(mockThreeVBrandDto.getBrandName()));
    }

    @Test
    public void threeVBrandController_updateBrand_returnThreeVBrand() throws Exception{
        //given
        ThreeVBrandDto brandToBeUpdated = ThreeVBrandDto.builder()
                .brandId(1)
                .brandName("Updated Brand")
                .build();
        when(threeVBrandService.updateBrand(anyInt(),any(ThreeVBrandDto.class))).thenReturn(brandToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/threev/admin/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.brandName").value(brandToBeUpdated.getBrandName()));
    }

    @Test
    public void threeVBrandController_updateBrand_returnBadRequest() throws Exception{
        //given
        ThreeVBrandDto brandToBeUpdated = ThreeVBrandDto.builder()
                .brandId(1)
                .build();
        when(threeVBrandService.updateBrand(anyInt(),any(ThreeVBrandDto.class))).thenReturn(brandToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/threev/admin/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void threeVBrandController_deleteBrand_returnNull() throws Exception{
        //given
        doNothing().when(threeVBrandService).deleteBrand(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/threev/admin/brands/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}