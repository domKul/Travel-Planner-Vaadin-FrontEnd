package com.example.travelplannervaadinfrontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BackEndApiLink {
    @Value("https://github.com/domKul/Travel-Planner")
    private String linkToBackEnd;

    public String getLinkToBackEnd() {
        return linkToBackEnd;
    }
}
