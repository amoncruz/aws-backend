package com.example.AwsTrab.Aws.controller;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.example.AwsTrab.Aws.service.AwsService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class AwsController {

    @Autowired
    AwsService awsService;

    @GetMapping("/instances")
    public  List<Instance> getAllIntances(){
      List<Reservation> reservations = awsService.describeInstances();
      List<Instance> instancesGlobal = new ArrayList<>();
        for (Reservation reservation : reservations) {
            List<Instance> instances = reservation.getInstances();

            for (Instance instance : instances) {
                instancesGlobal.add(instance);

            }
        }
    return instancesGlobal;
    }

    @PostMapping("/stop/{id}")
    public ResponseEntity stopInstance(@PathVariable("id") String id){
        awsService.stopInstance(id);
        return new ResponseEntity<String>("Instancia Parada com sucesso", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity createInstance(){
        awsService.createInstance();
        return new ResponseEntity<String>("Instancia Criada com sucesso", HttpStatus.OK);
    }

    @PostMapping("/start/{id}")
    public ResponseEntity startInstance(@PathVariable("id") String id){
        awsService.startInstance(id);
        return new ResponseEntity<String>("Instancia Inicializada com sucesso", HttpStatus.OK);
    }

    @PostMapping("/terminate/{id}")
    public ResponseEntity terminateInstance(@PathVariable("id") String id){
        awsService.terminateInstance(id);
        return new ResponseEntity<String>("Instancia Terminada com sucesso", HttpStatus.OK);
    }


}
