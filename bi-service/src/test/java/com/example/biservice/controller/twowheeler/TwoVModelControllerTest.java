package com.example.biservice.controller.twowheeler;

import com.example.biservice.payload.twowheeler.TwoVModelDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.twowheeler.model.TwoVModelService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TwoVModelController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TwoVModelControllerTest {

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
    private TwoVModelService twoVModelService;

    private TwoVModelDto mockTwoVModelDto;

    @BeforeEach
    void setUp() {
        mockTwoVModelDto = TwoVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockTwoVModelDto = null;
    }

    @Test
    public void twoVModelController_createModel_returnCreated() throws Exception{
        //given
        TwoVModelDto inputModel = TwoVModelDto.builder()
                .modelName("Model A")
                .build();
        when(twoVModelService.createModel(any(TwoVModelDto.class),anyInt())).thenReturn(mockTwoVModelDto);

        //when
        mockMvc.perform(post("/api/v1/twov/admin/brands/1/models")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputModel)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inputModel.getModelName()));
    }

    @Test
    public void twoVModelController_createModel_returnBadRequest() throws Exception{
        //given
        TwoVModelDto inputModel = TwoVModelDto.builder()
                .build();
        when(twoVModelService.createModel(any(TwoVModelDto.class),anyInt())).thenReturn(mockTwoVModelDto);

        //when&then
        mockMvc.perform(post("/api/v1/twov/admin/brands/1/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputModel)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void twoVModelController_findAllModels_returnTwoVModelList() throws Exception{
        //given
        List<TwoVModelDto> twoVModels = List.of(
                TwoVModelDto.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                TwoVModelDto.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(twoVModelService.findAllModels()).thenReturn(twoVModels);

        //when&then
        mockMvc.perform(get("/api/v1/twov/models"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void twoVModelController_findAllModelsByBrand_returnTwoVModelList() throws Exception{
        //given
        List<TwoVModelDto> twoVModels = List.of(
                TwoVModelDto.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                TwoVModelDto.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(twoVModelService.findAllModelsByBrand(anyInt())).thenReturn(twoVModels);

        //when&then
        mockMvc.perform(get("/api/v1/twov/brands/1/models"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void twoVModelController_findModelById_returnTwoVModel() throws Exception{
        //given
        when(twoVModelService.findModelById(anyInt())).thenReturn(mockTwoVModelDto);

        //when&then
        mockMvc.perform(get("/api/v1/twov/models/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(mockTwoVModelDto.getModelName()));
    }

    @Test
    public void twoVModelController_updateModel_returnTwoVModel() throws Exception{
        //given
        TwoVModelDto modelToBeUpdated = TwoVModelDto.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();
        when(twoVModelService.updateModel(anyInt(),any(TwoVModelDto.class))).thenReturn(modelToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/twov/admin/models/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(modelToBeUpdated.getModelName()));
    }

    @Test
    public void twoVModelController_updateModel_returnBadRequest() throws Exception{
        //given
        TwoVModelDto modelToBeUpdated = TwoVModelDto.builder()
                .modelId(1)
                .build();
        when(twoVModelService.updateModel(anyInt(),any(TwoVModelDto.class))).thenReturn(modelToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/twov/admin/models/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void twoVModelController_deleteModel_returnNull() throws Exception{
        //given
        doNothing().when(twoVModelService).deleteModel(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/twov/admin/models/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void twoVModelController_addBatteryToModel_returnNull() throws Exception{
        //given
        doNothing().when(twoVModelService).addBatteryToModel(anyInt(),anyInt());

        //when&then
        mockMvc.perform(post("/api/v1/twov/admin/models/1/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}