package org.example;

import java.time.Instant;

public record Product(Long id, Instant creationDate, String type, String uuid) {
}
