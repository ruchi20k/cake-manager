package com.example.demo.controlller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.example.demo.entity.Cake;
import com.example.demo.exception.CakeNotFoundException;
import com.example.demo.exception.NoInputException;
import com.example.demo.service.CakeService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class CakeControlller {

    @Autowired
    private CakeService cakeService;

    @PostMapping("/saveCakes")
    public  ResponseEntity<Cake> saveCake(@RequestBody List<Cake> cakes) {
	try {
	    if(cakes.size() > 0) {
		cakes.forEach(cake -> cakeService.saveCake(cake));
		return new ResponseEntity<Cake>(HttpStatus.CREATED);
	    } else {
		throw new NoInputException("No Cake found in the input body.Bad Request");
	    }
	}catch (HttpServerErrorException e) {
	    return new ResponseEntity<Cake>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

    @GetMapping("/cakes")
    public  ResponseEntity<?> getCakes() {
	List<Cake> cakes = cakeService.getAllCakes();
	if (cakes == null || cakes.isEmpty())
	    throw new CakeNotFoundException("No Cakes found in Database");
	return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(cakes);
    }

    @GetMapping("/cakes/{id}")
    public  ResponseEntity<?> getCakeById(@PathVariable Integer id) {
	Cake cake = cakeService.getCakeById(id);
	if (cake == null)
	    throw new CakeNotFoundException("No Cake found with id " +id);
	return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(cake);
    }

    @GetMapping("/download")
    public ResponseEntity<File> downloadCakes() {
	File file = cakeService.downloadCakes();
	if (file != null) {
	    return new ResponseEntity<File>(file, HttpStatus.OK);
	}
	return new ResponseEntity<File>(file, HttpStatus.BAD_REQUEST); 
    }


    @DeleteMapping({"/delete/{id}"})
    public void deleteCakeById(@PathVariable Integer id) {
	cakeService.deleteById(id);
    }

    @DeleteMapping({"/delete"})
    public void deleteAllCakes() {
	cakeService.deleteAllCakes();;
    }

}
