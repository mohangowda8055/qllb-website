package com.example.biservice.controller.inverterbattery;

import com.example.biservice.payload.inverterbattery.BackupDurationDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.inverterbattery.backupduration.BackupDurationService;
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

@WebMvcTest(controllers = BackupDurationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BackupDurationControllerTest {

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
    private BackupDurationService backupDurationService;

    private BackupDurationDto mockBackupDurationDto;

    @BeforeEach
    void setUp() {
        mockBackupDurationDto = BackupDurationDto.builder()
                .backupDurationId(1)
                .backupDuration(2)
                .build();
    }

    @AfterEach
    void tearDown(){
        mockBackupDurationDto = null;
    }

    @Test
    public void backupDurationController_createBackupDuration_returnCreated() throws Exception{
        //given
        BackupDurationDto inputBackupDuration = BackupDurationDto.builder()
                .backupDuration(2)
                .build();
        when(backupDurationService.createBackupDuration(any(BackupDurationDto.class))).thenReturn(mockBackupDurationDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/inverterbattery/admin/backupdurations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBackupDuration)));
        //then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.backupDuration").value(inputBackupDuration.getBackupDuration()));
    }

    @Test
    public void backupDurationController_createBackupDuration_returnBadRequest() throws Exception{
        //given
        BackupDurationDto inputBackupDuration = BackupDurationDto.builder()
                .build();
        when(backupDurationService.createBackupDuration(any(BackupDurationDto.class))).thenReturn(mockBackupDurationDto);
        //when
        ResultActions response = mockMvc.perform(post("/api/v1/inverterbattery/admin/backupdurations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputBackupDuration)));
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void backupDurationController_findAllBackupDurations_returnBackupDurationList() throws Exception{
        //given
        List<BackupDurationDto> backupDurations = List.of(
                BackupDurationDto.builder()
                        .backupDurationId(1)
                        .backupDuration(2)
                        .build(),
                BackupDurationDto.builder()
                        .backupDurationId(2)
                        .backupDuration(2.5f)
                        .build()
        );
        when(backupDurationService.findAllBackupDurations()).thenReturn(backupDurations);

        //when&then
        mockMvc.perform(get("/api/v1/inverterbattery/backupdurations"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void backupDurationController_findBackupDurationById_returnBackupDuration() throws Exception{
        when(backupDurationService.findBackupDurationById(anyInt())).thenReturn(mockBackupDurationDto);

        //when&then
        mockMvc.perform(get("/api/v1/inverterbattery/backupdurations/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.backupDuration").value(mockBackupDurationDto.getBackupDuration()));
    }

    @Test
    public void backupDurationController_updateBackupDuration_returnBackupDuration() throws Exception{
        //given
        BackupDurationDto backupDurationToBeUpdated = BackupDurationDto.builder()
                .backupDurationId(1)
                .backupDuration(3)
                .build();
        when(backupDurationService.updateBackupDuration(anyInt(),any(BackupDurationDto.class))).thenReturn(backupDurationToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverterbattery/admin/backupdurations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(backupDurationToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.backupDuration").value(backupDurationToBeUpdated.getBackupDuration()));
    }

    @Test
    public void backupDurationController_updateBackupDuration_returnBadRequest() throws Exception{
        //given
        BackupDurationDto backupDurationToBeUpdated = BackupDurationDto.builder()
                .backupDurationId(1)
                .build();
        when(backupDurationService.updateBackupDuration(anyInt(),any(BackupDurationDto.class))).thenReturn(backupDurationToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/inverterbattery/admin/backupdurations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(backupDurationToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void backupDurationController_deleteBackupDuration_returnNull() throws Exception{
        //given
        doNothing().when(backupDurationService).deleteBackupDuration(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/inverterbattery/admin/backupdurations/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}