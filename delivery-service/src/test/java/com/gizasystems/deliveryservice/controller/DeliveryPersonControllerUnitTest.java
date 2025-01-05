package com.gizasystems.deliveryservice.controller;

import com.gizasystems.deliveryservice.dto.OrderDTO;
import com.gizasystems.deliveryservice.dto.DeliveryPersonDTO;
import com.gizasystems.deliveryservice.entity.DeliveryPerson;
import com.gizasystems.deliveryservice.service.DeliveryPersonService;
import com.gizasystems.deliveryservice.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class DeliveryPersonControllerUnitTest {

    @Mock
    private OrderService orderService;

    @Mock
    private DeliveryPersonService deliveryPersonService;

    @InjectMocks
    private DeliveryPersonController deliveryPersonController;

    @Autowired
    private HttpServletRequest request;

    private DeliveryPersonDTO deliveryPersonDTO;
    private DeliveryPerson deliveryPerson;
    private OrderDTO orderDTO;

    @BeforeEach
    public void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        deliveryPersonDTO = new DeliveryPersonDTO(1L);
        deliveryPerson = new DeliveryPerson(1L);
        orderDTO = new OrderDTO(1L, 1L, 1L, BigDecimal.TEN, BigDecimal.ONE);
        Mockito.when(request.getHeader("X-User-Id")).thenReturn("1");
    }

    @Test
    public void testRegisterDeliveryPerson() {
        when(deliveryPersonService.register(deliveryPersonDTO)).thenReturn(deliveryPerson);
        ResponseEntity<DeliveryPerson> response = deliveryPersonController.register(request, deliveryPersonDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deliveryPerson, response.getBody());
    }

    @Test
    public void testUpdateAvailability() {
        when(deliveryPersonService.updateAvailability(1L, true)).thenReturn(deliveryPerson);
        ResponseEntity<DeliveryPerson> response = deliveryPersonController.updateAvailability(request, true);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deliveryPerson, response.getBody());
    }

    @Test
    public void testViewWaitingOrders() {
        when(orderService.getOrdersByState("WAITING_FOR_DELIVERY")).thenReturn(Arrays.asList(orderDTO));
        ResponseEntity<List<OrderDTO>> response = deliveryPersonController.viewWaitingOrders(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        // TODO: check the response body
    }

    @Test
    public void testAcceptOrder() {
        when(orderService.assignDeliveryToOrder(1L, 1L)).thenReturn(orderDTO);
        ResponseEntity<OrderDTO> response = deliveryPersonController.acceptOrder(request, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDTO, response.getBody());
        // TODO: check the response body
    }

    @Test
    public void testViewAssignedOrders() {
        when(orderService.getAssignedOrders("ON_WAY", 1L)).thenReturn(Arrays.asList(orderDTO));
        ResponseEntity<List<OrderDTO>> response = deliveryPersonController.viewAssignedOrders(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        // TODO: check the response body
    }

    @Test
    public void testViewDeliveredOrders() {
        when(orderService.getAssignedOrders("DELIVERED", 1L)).thenReturn(Arrays.asList(orderDTO));
        ResponseEntity<List<OrderDTO>> response = deliveryPersonController.viewDeliveredOrders(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        // TODO: check the response body
    }

    @Test
    public void testConfirmPayment() {
        when(orderService.updateOrderState(1L, "DELIVERED")).thenReturn(orderDTO);
        ResponseEntity<?> response = deliveryPersonController.confirmPayment(request, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // TODO: check the response body
    }

    @Test
    public void testViewProfit() {
        when(orderService.getAssignedOrders("DELIVERED", 1L)).thenReturn(Arrays.asList(orderDTO));
        ResponseEntity<BigDecimal> response = deliveryPersonController.viewProfit(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(1), response.getBody());
    }

    @Test
    public void testUnauthorizedRequest() {
        Mockito.when(request.getHeader("X-User-Id")).thenReturn(null);
        ResponseEntity<DeliveryPerson> response = deliveryPersonController.updateAvailability(request, true);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}

