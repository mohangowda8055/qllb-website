package com.example.biservice.controller.threewheeler;

import com.example.biservice.payload.threewheeler.ThreeVModelDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.threewheeler.model.ThreeVModelService;
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

@WebMvcTest(controllers = ThreeVModelController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ThreeVModelControllerTest {

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
    private ThreeVModelService threeVModelService;

    private ThreeVModelDto mockThreeVModelDto;

    @BeforeEach
    void setUp() {
        mockThreeVModelDto = ThreeVModelDto.builder()
                .modelId(1)
                .modelName("Model A")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockThreeVModelDto = null;
    }

    @Test
    public void threeVModelController_createModel_returnCreated() throws Exception{
        //given
        ThreeVModelDto inputModel = ThreeVModelDto.builder()
                .modelName("Model A")
                .build();
        when(threeVModelService.createModel(any(ThreeVModelDto.class),anyInt())).thenReturn(mockThreeVModelDto);

        //when
        mockMvc.perform(post("/api/v1/threev/admin/brands/1/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputModel)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(inputModel.getModelName()));
    }

    @Test
    public void threeVModelController_createModel_returnBadRequest() throws Exception{
        //given
        ThreeVModelDto inputModel = ThreeVModelDto.builder()
                .build();
        when(threeVModelService.createModel(any(ThreeVModelDto.class),anyInt())).thenReturn(mockThreeVModelDto);

        //when&then
        mockMvc.perform(post("/api/v1/threev/admin/brands/1/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputModel)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void threeVModelController_findAllModels_returnThreeVModelList() throws Exception{
        //given
        List<ThreeVModelDto> threeVModels = List.of(
                ThreeVModelDto.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                ThreeVModelDto.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(threeVModelService.findAllModels()).thenReturn(threeVModels);

        //when&then
        mockMvc.perform(get("/api/v1/threev/models"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void threeVModelController_findAllModelsByBrand_returnThreeVModelList() throws Exception{
        //given
        List<ThreeVModelDto> threeVModels = List.of(
                ThreeVModelDto.builder()
                        .modelId(1)
                        .modelName("Model A")
                        .build(),
                ThreeVModelDto.builder()
                        .modelId(2)
                        .modelName("Model B")
                        .build()
        );
        when(threeVModelService.findAllModelsByBrand(anyInt())).thenReturn(threeVModels);

        //when&then
        mockMvc.perform(get("/api/v1/threev/brands/1/models"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void threeVModelController_findModelById_returnThreeVModel() throws Exception{
        //given
        when(threeVModelService.findModelById(anyInt())).thenReturn(mockThreeVModelDto);

        //when&then
        mockMvc.perform(get("/api/v1/threev/models/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(mockThreeVModelDto.getModelName()));
    }

    @Test
    public void threeVModelController_updateModel_returnThreeVModel() throws Exception{
        //given
        ThreeVModelDto modelToBeUpdated = ThreeVModelDto.builder()
                .modelId(1)
                .modelName("Updated Model")
                .build();
        when(threeVModelService.updateModel(anyInt(),any(ThreeVModelDto.class))).thenReturn(modelToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/threev/admin/models/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.modelName").value(modelToBeUpdated.getModelName()));
    }

    @Test
    public void threeVModelController_updateModel_returnBadRequest() throws Exception{
        //given
        ThreeVModelDto modelToBeUpdated = ThreeVModelDto.builder()
                .modelId(1)
                .build();
        when(threeVModelService.updateModel(anyInt(),any(ThreeVModelDto.class))).thenReturn(modelToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/threev/admin/models/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modelToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void threeVModelController_deleteModel_returnNull() throws Exception{
        //given
        doNothing().when(threeVModelService).deleteModel(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/threev/admin/models/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void threeVModelController_addBatteryToModel_returnNull() throws Exception{
        //given
        doNothing().when(threeVModelService).addBatteryToModel(anyInt(),anyInt(),anyInt());

        //when&then
        mockMvc.perform(post("/api/v1/threev/admin/models/1/fueltypes/1/batteries/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}