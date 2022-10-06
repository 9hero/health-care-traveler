package com.healthtrip.travelcare.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProgramCategoryController {

    private final String domain = "/MedicalProgram";
    private final String admin = "/admin"+domain;
}
