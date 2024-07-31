package com.example.biservice.controller.commercial;

import com.example.biservice.payload.commercial.CommercialVModelDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.commercial.model.CommercialVModelService;
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

@WebMvcTest(controllers = CommercialVModelController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CommercialVModelControllerTest {

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
    private CommercialVModelService commercialVModelService;

    private CommercialVModelDto mockCommercialVModelDto;

    @BeforeEach
    void setUp() {
        mockCommercialVModelDto = CommercialVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockCommercialVModelDto = null;
    }

    @Test
    public void commercialVModelController_createModel_returnCreated() throws Exception{
        //given
        CommercialVModelDto inputModel = CommercialVModelDto.builder()
                .modelName("Model A")
                .build();
        when(commercialVModelService.createModel(any(CommercialVModelDto.class),anyInt(),anyInt())).thenReturn(mockCommercialVModelDto);

        //when
        mockMvc.perform(post("/api/v1/commercialv/admin/brands/1/segments/1/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputModel)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inputModel.getModelName()));
    }

    @Test
    public void commercialVModelController_createModel_returnBadRequest() throws Exception{
        //given
        CommercialVModelDto inputModel = CommercialVModelDto.builder()
                .build();
        when(commercialVModelService.createModel(any(CommercialVModelDto.class),anyInt(),anyInt())).thenReturn(mockCommercialVModelDto);

        //when&then
        mockMvc.perform(post("/api/v1/commercialv/admin/brands/1/segments/1//models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputModel)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void commercialVModelController_findAllModels_returnCommercialVModelList() throws Exception{
        //given
        List<CommercialVModelDto> commercialVModels = List.of(
                CommercialVModelDto.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                CommercialVModelDto.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(commercialVModelService.findAllModels()).thenReturn(commercialVModels);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/models"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void commercialVModelController_findAllModelsByBrandAndSegment_returnCommercialVModelList() throws Exception{
        //given
        List<CommercialVModelDto> commercialVModels = List.of(
                CommercialVModelDto.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                CommercialVModelDto.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(commercialVModelService.findAllModelsByBrandAndSegment(anyInt(),anyInt())).thenReturn(commercialVModels);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/brands/1/segments/1/models"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void commercialVModelController_findModelById_returnCommercialVModel() throws Exception{
        //given
        when(commercialVModelService.findModelById(anyInt())).thenReturn(mockCommercialVModelDto);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/models/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(mockCommercialVModelDto.getModelName()));
    }

    @Test
    public void commercialVModelController_updateModel_returnCommercialVModel() throws Exception{
        //given
        CommercialVModelDto modelToBeUpdated = CommercialVModelDto.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();
        when(commercialVModelService.updateModel(anyInt(),any(CommercialVModelDto.class))).thenReturn(modelToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/commercialv/admin/models/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(modelToBeUpdated.getModelName()));
    }

    @Test
    public void commercialVModelController_updateModel_returnBadRequest() throws Exception{
        //given
        CommercialVModelDto modelToBeUpdated = CommercialVModelDto.builder()
                .modelId(1)
                .build();
        when(commercialVModelService.updateModel(anyInt(),any(CommercialVModelDto.class))).thenReturn(modelToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/commercialv/admin/models/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void commercialVModelController_deleteModel_returnNull() throws Exception{
        //given
        doNothing().when(commercialVModelService).deleteModel(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/commercialv/admin/models/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void commercialVModelController_addBatteryToModel_returnNull() throws Exception{
        //given
        doNothing().when(commercialVModelService).addBatteryToModel(anyInt(),anyInt());

        //when&then
        mockMvc.perform(post("/api/v1/commercialv/admin/models/1/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}