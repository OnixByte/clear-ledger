package com.onixbyte.clearledger.generator;

import com.onixbyte.clearledger.constant.IdType;
import com.onixbyte.clearledger.service.SerialService;
import com.onixbyte.clearledger.common.Formatters;
import com.onixbyte.guid.GuidCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LedgerIdCreator implements GuidCreator<String> {

    private final SerialService serialService;

    @Autowired
    public LedgerIdCreator(SerialService serialService) {
        this.serialService = serialService;
    }

    @Override
    public String nextId() {
        var date = LocalDate.now().format(Formatters.SHORTENED_DATE_FORMATTER);
        return "%s%s%04d".formatted(IdType.LEDGER.getCode(), date, serialService.nextSerial("ledger"));
    }
}
