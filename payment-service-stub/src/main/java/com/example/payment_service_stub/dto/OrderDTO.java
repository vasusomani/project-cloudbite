package com.example.payment_service_stub.dto;

import java.time.LocalDateTime;

public record OrderDTO(Long id, String status, LocalDateTime creationDate) {
}