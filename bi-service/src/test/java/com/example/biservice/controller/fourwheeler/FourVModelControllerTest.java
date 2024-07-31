package com.example.biservice.controller.fourwheeler;

import com.example.biservice.payload.fourwheeler.FourVModelDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.fourwheeler.model.FourVModelService;
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

@WebMvcTest(controllers = FourVModelController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class FourVModelControllerTest {

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
    private FourVModelService fourVModelService;

    private FourVModelDto mockFourVModelDto;

    @BeforeEach
    void setUp() {
        mockFourVModelDto = FourVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockFourVModelDto = null;
    }

    @Test
    public void fourVModelController_createModel_returnCreated() throws Exception{
        //given
        FourVModelDto inputModel = FourVModelDto.builder()
                .modelName("Model A")
                .build();
        when(fourVModelService.createModel(any(FourVModelDto.class),anyInt())).thenReturn(mockFourVModelDto);

        //when
        mockMvc.perform(post("/api/v1/fourv/admin/brands/1/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputModel)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inputModel.getModelName()));
    }

    @Test
    public void fourVModelController_createModel_returnBadRequest() throws Exception{
        //given
        FourVModelDto inputModel = FourVModelDto.builder()
                .build();
        when(fourVModelService.createModel(any(FourVModelDto.class),anyInt())).thenReturn(mockFourVModelDto);

        //when&then
        mockMvc.perform(post("/api/v1/fourv/admin/brands/1/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputModel)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void fourVModelController_findAllModels_returnFourVModelList() throws Exception{
        //given
        List<FourVModelDto> fourVModels = List.of(
                FourVModelDto.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                FourVModelDto.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(fourVModelService.findAllModels()).thenReturn(fourVModels);

        //when&then
        mockMvc.perform(get("/api/v1/fourv/models"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void fourVModelController_findAllModelsByBrand_returnFourVModelList() throws Exception{
        //given
        List<FourVModelDto> fourVModels = List.of(
                FourVModelDto.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                FourVModelDto.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(fourVModelService.findAllModelsByBrand(anyInt())).thenReturn(fourVModels);

        //when&then
        mockMvc.perform(get("/api/v1/fourv/brands/1/models"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void fourVModelController_findModelById_returnFourVModel() throws Exception{
        //given
        when(fourVModelService.findModelById(anyInt())).thenReturn(mockFourVModelDto);

        //when&then
        mockMvc.perform(get("/api/v1/fourv/models/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(mockFourVModelDto.getModelName()));
    }

    @Test
    public void fourVModelController_updateModel_returnFourVModel() throws Exception{
        //given
        FourVModelDto modelToBeUpdated = FourVModelDto.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();
        when(fourVModelService.updateModel(anyInt(),any(FourVModelDto.class))).thenReturn(modelToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/fourv/admin/models/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(modelToBeUpdated.getModelName()));
    }

    @Test
    public void fourVModelController_updateModel_returnBadRequest() throws Exception{
        //given
        FourVModelDto modelToBeUpdated = FourVModelDto.builder()
                .modelId(1)
                .build();
        when(fourVModelService.updateModel(anyInt(),any(FourVModelDto.class))).thenReturn(modelToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/fourv/admin/models/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void fourVModelController_deleteModel_returnNull() throws Exception{
        //given
        doNothing().when(fourVModelService).deleteModel(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/fourv/admin/models/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void fourVModelController_addFuelTypeToModel_returnNull() throws Exception{
        //given
        doNothing().when(fourVModelService).addFuelTypeToModel(anyInt(),anyInt());

        //when&then
        mockMvc.perform(post("/api/v1/fourv/admin/models/1/fueltypes/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void fourVModelController_addBatteryToModel_returnNull() throws Exception{
        //given
        doNothing().when(fourVModelService).addBatteryToModel(anyInt(),anyInt(),anyInt());

        //when&then
        mockMvc.perform(post("/api/v1/fourv/admin/models/1/fueltypes/1/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}