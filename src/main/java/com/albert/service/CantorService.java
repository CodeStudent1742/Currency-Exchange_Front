package com.albert.service;


import com.albert.client.CantorClient;
import com.albert.domain.dto.CantorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CantorService {


    private final CantorClient cantorClient;
    private static CantorService cantorService;

    public static CantorService getInstance() {
        if (cantorService == null) {
            cantorService = new CantorService(CantorClient.getInstance());
        }
        return cantorService;
    }
    public CantorDto getCantor() {
        return cantorClient.getCantor();
    }

}
