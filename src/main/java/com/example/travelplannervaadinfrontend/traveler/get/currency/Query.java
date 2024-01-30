package com.example.travelplannervaadinfrontend.traveler.get.currency;

import java.math.BigDecimal;

public record Query(
        String from,
        String to,
        BigDecimal amount
) {
}
