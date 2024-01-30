package com.example.travelplannervaadinfrontend.traveler.get.currency;

import java.math.BigDecimal;

public record CurrencyDto(
        Query query,
        BigDecimal result) {
}
