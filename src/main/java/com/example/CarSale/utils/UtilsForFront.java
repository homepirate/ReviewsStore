package com.example.CarSale.utils;


import com.example.CarSale.constants.Enums.Engine;
import com.example.CarSale.constants.Enums.Transmission;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class UtilsForFront {

    public List<Transmission> getAllTransmission(){
        return List.of(Transmission.values());
    }

    public List<Engine> getAllEngine(){
        return List.of(Engine.values());
    }

}
