package com.example.biservice.controller.commercial;

import com.example.biservice.payload.commercial.CommercialVSegmentDto;
import com.example.biservice.security.config.JwtAuthenticationEntryPoint;
import com.example.biservice.security.config.JwtAuthenticationFilter;
import com.example.biservice.security.config.JwtService;
import com.example.biservice.service.commercial.segment.CommercialVSegmentService;
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

@WebMvcTest(controllers = CommercialVSegmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CommercialVSegmentControllerTest {

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
    private CommercialVSegmentService commercialVSegmentService;

    private CommercialVSegmentDto mockCommercialVSegmentDto;

    @BeforeEach
    void setUp() {
        mockCommercialVSegmentDto = CommercialVSegmentDto.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();
    }

    @AfterEach
    void tearDown(){
        mockCommercialVSegmentDto = null;
    }

    @Test
    public void commercialVSegmentController_createSegment_returnCreated() throws Exception{
        //given
        CommercialVSegmentDto inputSegment = CommercialVSegmentDto.builder()
                .segmentName("Segment A")
                .build();
        when(commercialVSegmentService.createSegment(any(CommercialVSegmentDto.class))).thenReturn(mockCommercialVSegmentDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/commercialv/admin/segments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputSegment)));
        //then
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.success",is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.segmentName").value(inputSegment.getSegmentName()));
    }

    @Test
    public void commercialVSegmentController_createSegment_returnBadRequest() throws Exception{
        //given
        CommercialVSegmentDto inputSegment = CommercialVSegmentDto.builder()
                .build();
        when(commercialVSegmentService.createSegment(any(CommercialVSegmentDto.class))).thenReturn(mockCommercialVSegmentDto);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/commercialv/admin/segments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputSegment)));
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void commercialVSegmentController_findAllSegments_returnCommercialVSegmentList() throws Exception{
        //given
        List<CommercialVSegmentDto> commercialVSegments = List.of(
                CommercialVSegmentDto.builder()
                        .segmentId(1)
                        .segmentName("Segment A")
                        .build(),
                CommercialVSegmentDto.builder()
                        .segmentId(2)
                        .segmentName("Segment B")
                        .build()
        );
        when(commercialVSegmentService.findAllSegments()).thenReturn(commercialVSegments);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/segments"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void commercialVSegmentController_findAllSegmentsByBrand_returnCommercialVSegmentList() throws Exception{
        //given
        List<CommercialVSegmentDto> commercialVSegments = List.of(
                CommercialVSegmentDto.builder()
                        .segmentId(1)
                        .segmentName("Segment A")
                        .build(),
                CommercialVSegmentDto.builder()
                        .segmentId(2)
                        .segmentName("Segment B")
                        .build()
        );
        when(commercialVSegmentService.findAllSegmentsByBrand(anyInt())).thenReturn(commercialVSegments);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/brands/1/segments"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data",hasSize(2)));
    }

    @Test
    public void commercialVSegmentController_findSegmentById_returnCommercialVSegment() throws Exception{
        when(commercialVSegmentService.findSegmentById(anyInt())).thenReturn(mockCommercialVSegmentDto);

        //when&then
        mockMvc.perform(get("/api/v1/commercialv/segments/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.segmentName").value(mockCommercialVSegmentDto.getSegmentName()));
    }

    @Test
    public void commercialVSegmentController_updateSegment_returnCommercialVSegment() throws Exception{
        //given
        CommercialVSegmentDto segmentToBeUpdated = CommercialVSegmentDto.builder()
                .segmentId(1)
                .segmentName("Updated Segment")
                .build();
        when(commercialVSegmentService.updateSegment(anyInt(),any(CommercialVSegmentDto.class))).thenReturn(segmentToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/commercialv/admin/segments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(segmentToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.segmentName").value(segmentToBeUpdated.getSegmentName()));
    }

    @Test
    public void commercialVSegmentController_updateSegment_returnBadRequest() throws Exception{
        //given
        CommercialVSegmentDto segmentToBeUpdated = CommercialVSegmentDto.builder()
                .segmentId(1)
                .build();
        when(commercialVSegmentService.updateSegment(anyInt(),any(CommercialVSegmentDto.class))).thenReturn(segmentToBeUpdated);

        //when&then
        mockMvc.perform(put("/api/v1/commercialv/admin/segments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(segmentToBeUpdated)))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void commercialVSegmentController_deleteSegment_returnNull() throws Exception{
        //given
        doNothing().when(commercialVSegmentService).deleteSegment(anyInt());

        //when&then
        mockMvc.perform(delete("/api/v1/commercialv/admin/segments/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}