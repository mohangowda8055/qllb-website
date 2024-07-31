package com.example.biservice.service.commercial.segment;

import com.example.biservice.entity.commercial.CommercialVBrand;
import com.example.biservice.entity.commercial.CommercialVSegment;
import com.example.biservice.exception.ResourceNotFoundException;
import com.example.biservice.exception.UniqueConstraintException;
import com.example.biservice.payload.commercial.CommercialVSegmentDto;
import com.example.biservice.repository.commercial.CommercialVBrandRepo;
import com.example.biservice.repository.commercial.CommercialVSegmentRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommercialVSegmentServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CommercialVBrandRepo commercialVBrandRepo;

    @Mock
    private CommercialVSegmentRepo commercialVSegmentRepo;

    @InjectMocks
    private CommercialVSegmentServiceImp commercialVSegmentServiceImp;

    private CommercialVSegment mockCommercialVSegment;
    private CommercialVSegmentDto mockCommercialVSegmentDto;

    @BeforeEach
    void setUp() {
        mockCommercialVSegment = CommercialVSegment.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();
        mockCommercialVSegmentDto = CommercialVSegmentDto.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();

        when(modelMapper.map(Mockito.any(CommercialVSegmentDto.class), Mockito.eq(CommercialVSegment.class))).thenReturn(mockCommercialVSegment);
        when(modelMapper.map(Mockito.any(CommercialVSegment.class), Mockito.eq(CommercialVSegmentDto.class))).thenReturn(mockCommercialVSegmentDto);
    }

    @AfterEach
    void tearDown() {
        mockCommercialVSegment = null;
        mockCommercialVSegmentDto = null;
    }

    @Test
    public void commercialVSegmentService_createSegment_returnCommercialVSegment(){
        //given
        mockCommercialVSegment = CommercialVSegment.builder()
                .segmentName("Segment A")
                .build();
        mockCommercialVSegmentDto = CommercialVSegmentDto.builder()
                .segmentName("Segment A")
                .build();
        CommercialVSegment savedSegment = CommercialVSegment.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();
        CommercialVSegmentDto savedSegmentDto = CommercialVSegmentDto.builder()
                .segmentId(1)
                .segmentName("Segment A")
                .build();
        when((modelMapper.map(Mockito.any(CommercialVSegmentDto.class), Mockito.eq(CommercialVSegment.class)))).thenReturn(mockCommercialVSegment);
        when((modelMapper.map(Mockito.any(CommercialVSegment.class), Mockito.eq(CommercialVSegmentDto.class)))).thenReturn(savedSegmentDto);
        when(commercialVSegmentRepo.save(Mockito.any(CommercialVSegment.class))).thenReturn(savedSegment);

        //when
        CommercialVSegmentDto result = commercialVSegmentServiceImp.createSegment(mockCommercialVSegmentDto);
        //then
        assertNotNull(result);
        assertEquals(result.getSegmentName(),mockCommercialVSegmentDto.getSegmentName());
    }

    @Test
    public void commercialVSegmentService_createSegment_throwUniqueConstraintException(){
        //given
        when(commercialVSegmentRepo.save(Mockito.any(CommercialVSegment.class))).thenThrow(DataIntegrityViolationException.class);

        //when&then
        assertThrows(UniqueConstraintException.class, ()->commercialVSegmentServiceImp.createSegment(mockCommercialVSegmentDto));
    }

    @Test
    public void commercialVSegmentService_findAllSegments_returnEmptyList() {
        //given
        when(commercialVSegmentRepo.findAll(Mockito.any(Sort.class))).thenReturn(List.of());
        //when
        List<CommercialVSegmentDto> result = commercialVSegmentServiceImp.findAllSegments();
        //then
        assertEquals(0, result.size());
    }

    @Test
    public void commercialVSegmentService_findAllSegments_returnCommercialVSegmentList() {
        //given
        List<CommercialVSegment> commercialVSegments = Arrays.asList(
                CommercialVSegment.builder()
                        .segmentId(1)
                        .segmentName("Segment A")
                        .build(),
                CommercialVSegment.builder()
                        .segmentId(2)
                        .segmentName("Segment B")
                        .build()
        );
        when(commercialVSegmentRepo.findAll(Mockito.any(Sort.class))).thenReturn(commercialVSegments);
        when(modelMapper.map(Mockito.any(CommercialVSegment.class), Mockito.eq(CommercialVSegmentDto.class)))
                .thenAnswer(invocation -> {
                    CommercialVSegment segment = invocation.getArgument(0);
                    return new CommercialVSegmentDto(segment.getSegmentId(), segment.getSegmentName());
                });

        //when
        List<CommercialVSegmentDto> result = commercialVSegmentServiceImp.findAllSegments();
        //then
        assertEquals(commercialVSegments.size(), result.size());
        assertEquals("Segment A", result.get(0).getSegmentName());// Assert sorted order
        assertEquals("Segment B", result.get(1).getSegmentName());
    }

    @Test
    public void commercialVSegmentService_findAllSegmentsByBrand_returnCommercialVSegmentList(){
        //given
        Integer brandId = 1;
        CommercialVBrand mockBrand = CommercialVBrand.builder()
                .brandId(1)
                .brandName("Brand A")
                .build();
        List<CommercialVSegment> commercialVSegments = Arrays.asList(
                CommercialVSegment.builder()
                        .segmentId(1)
                        .segmentName("Segment A")
                        .build(),
                CommercialVSegment.builder()
                        .segmentId(2)
                        .segmentName("Segment B")
                        .build()
        );
        when(commercialVSegmentRepo.findSegmentsByBrand(Mockito.anyInt(),Mockito.any(Sort.class))).thenReturn(commercialVSegments);
        when(commercialVBrandRepo.findById(brandId)).thenReturn(Optional.of(mockBrand));
        when(modelMapper.map(Mockito.any(CommercialVSegment.class), Mockito.eq(CommercialVSegmentDto.class)))
                .thenAnswer(invocation -> {
                    CommercialVSegment segment = invocation.getArgument(0);
                    return new CommercialVSegmentDto(segment.getSegmentId(), segment.getSegmentName());
                });

        //when
        List<CommercialVSegmentDto> result = commercialVSegmentServiceImp.findAllSegmentsByBrand(brandId);
        //then
        assertEquals(commercialVSegments.size(), result.size());
        assertEquals("Segment A", result.get(0).getSegmentName());// Assert sorted order
        assertEquals("Segment B", result.get(1).getSegmentName());
    }

    @Test
    public void commercialVSegmentService_findSegmentById_returnCommercialVSegment(){
        //given
        Integer segmentId = 1;
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.of(mockCommercialVSegment));

        //when
        CommercialVSegmentDto result = commercialVSegmentServiceImp.findSegmentById(segmentId);
        //then
        assertNotNull(result);
        assertEquals(mockCommercialVSegmentDto, result);
    }

    @Test
    public void commercialVSegmentService_findSegmentById_throwResourceNotFoundException() {
        //given
        Integer segmentId = 1;
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVSegmentServiceImp.findSegmentById(segmentId));
    }

    @Test
    public void commercialVSegmentService_updateSegment_returnCommercialVSegment() {
        //given
        Integer segmentId = 1;
        CommercialVSegmentDto commercialVSegmentDto = CommercialVSegmentDto.builder()
                .segmentId(1)
                .segmentName("Updated Segment")
                .build();
        CommercialVSegment updatedCommercialVSegment = CommercialVSegment.builder()
                .segmentId(1)
                .segmentName("Updated Segment")
                .build();

        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.of(mockCommercialVSegment));
        when(modelMapper.map(Mockito.any(CommercialVSegmentDto.class), Mockito.eq(CommercialVSegment.class))).thenReturn(updatedCommercialVSegment);
        when(modelMapper.map(Mockito.any(CommercialVSegment.class), Mockito.eq(CommercialVSegmentDto.class))).thenReturn(commercialVSegmentDto);
        when(commercialVSegmentRepo.save(updatedCommercialVSegment)).thenReturn(updatedCommercialVSegment);

        //when
        CommercialVSegmentDto result = commercialVSegmentServiceImp.updateSegment(segmentId, commercialVSegmentDto);
        //then
        assertEquals("Updated Segment", result.getSegmentName());
    }

    @Test
    public void commercialVSegmentService_updateSegment_throwResourceNotFoundException() {
        //given
        Integer segmentId = 1;
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVSegmentServiceImp.updateSegment(segmentId, mockCommercialVSegmentDto));
    }

    @Test
    public void commercialVSegmentService_deleteSegment_returnVoid() {
        //given
        Integer segmentId = 1;
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.of(mockCommercialVSegment));
        Mockito.doNothing().when(commercialVSegmentRepo).delete(mockCommercialVSegment);

        //when&then
        assertAll(()->commercialVSegmentServiceImp.deleteSegment(segmentId));
        //then
        Mockito.verify(commercialVSegmentRepo, Mockito.times(1)).delete(mockCommercialVSegment);
    }

    @Test
    public void commercialVSegmentService_deleteSegment_throwResourceNotFoundException() {
        //given
        Integer segmentId = 1;
        when(commercialVSegmentRepo.findById(segmentId)).thenReturn(Optional.empty());

        //when&then
        assertThrows(ResourceNotFoundException.class, () -> commercialVSegmentServiceImp.deleteSegment(segmentId));
        //then
        Mockito.verify(commercialVSegmentRepo, Mockito.never()).delete(Mockito.any());
    }
}