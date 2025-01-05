package com.gizasystems.deliveryservice.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.gizasystems.deliveryservice.dto.DeliveryPersonDTO;
import com.gizasystems.deliveryservice.entity.DeliveryPerson;
import com.gizasystems.deliveryservice.exception.DeliveryPersonAlreadyExistsException;
import com.gizasystems.deliveryservice.exception.DeliveryPersonNotFoundException;
import com.gizasystems.deliveryservice.repository.DeliveryPersonRepository;

@SpringBootTest
class DeliveryPersonServiceTest {

    @Mock
    private DeliveryPersonRepository deliveryPersonRepository;

    @InjectMocks
    private DeliveryPersonService deliveryPersonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Register new delivery person - success")
    void registerDeliveryPersonSuccess() {
        Long deliveryPersonId = 1L;
        DeliveryPersonDTO dto = new DeliveryPersonDTO(deliveryPersonId);
        when(deliveryPersonRepository.findById(deliveryPersonId)).thenReturn(Optional.empty());
        when(deliveryPersonRepository.save(any(DeliveryPerson.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DeliveryPerson result = deliveryPersonService.register(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(deliveryPersonId);
        assertThat(result.getAvailability()).isFalse();
        verify(deliveryPersonRepository, times(1)).save(any(DeliveryPerson.class));
    }

    @Test
    @DisplayName("Register delivery person - already exists")
    void registerDeliveryPersonAlreadyExists() {
        Long deliveryPersonId = 1L;
        DeliveryPersonDTO dto = new DeliveryPersonDTO(deliveryPersonId);
        DeliveryPerson existingPerson = new DeliveryPerson(deliveryPersonId);
        when(deliveryPersonRepository.findById(deliveryPersonId)).thenReturn(Optional.of(existingPerson));

        assertThatThrownBy(() -> deliveryPersonService.register(dto))
            .isInstanceOf(DeliveryPersonAlreadyExistsException.class)
            .hasMessageContaining("DeliveryPerson already exists");
        verify(deliveryPersonRepository, never()).save(any(DeliveryPerson.class));
    }

    @Test
    @DisplayName("Update availability - success")
    void updateAvailabilitySuccess() {
        Long deliveryPersonId = 1L;
        DeliveryPerson existingPerson = new DeliveryPerson(deliveryPersonId);
        when(deliveryPersonRepository.findById(deliveryPersonId)).thenReturn(Optional.of(existingPerson));
        when(deliveryPersonRepository.save(any(DeliveryPerson.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DeliveryPerson result = deliveryPersonService.updateAvailability(deliveryPersonId, true);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(deliveryPersonId);
        assertThat(result.getAvailability()).isTrue();
        verify(deliveryPersonRepository, times(1)).save(existingPerson);
    }

    @Test
    @DisplayName("Update availability - delivery person not found")
    void updateAvailabilityDeliveryPersonNotFound() {
        Long deliveryPersonId = 1L;
        when(deliveryPersonRepository.findById(deliveryPersonId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deliveryPersonService.updateAvailability(deliveryPersonId, true))
            .isInstanceOf(DeliveryPersonNotFoundException.class)
            .hasMessageContaining("DeliveryPerson not found");
        verify(deliveryPersonRepository, never()).save(any(DeliveryPerson.class));
    }
}
