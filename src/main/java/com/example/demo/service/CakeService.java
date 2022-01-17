package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cake;
import com.example.demo.exception.CakeNotFoundException;
import com.example.demo.repository.CakeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CakeService implements ICakeService {

    @Autowired
    CakeRepository cakeRepository;

    @Override
    public List<Cake> getAllCakes() {
	ArrayList<Cake> cakes = new ArrayList<Cake>();
	cakeRepository.findAll().forEach(cakes::add);
	return cakes;
    }

    @Override
    public Cake getCakeById(Integer id) {
	Optional<Cake> opt = cakeRepository.findById(id);
	if(opt.isPresent()) {
	    return opt.get();
	} else {
	    throw new CakeNotFoundException("Cake with id : "+id+" Not Found");
	}
    }

    @Override
    public File downloadCakes()  {
	List<Cake> cakes = (List<Cake>) cakeRepository.findAll();
	String filename = "cakes.json";
	File file = new File("/Users/aarsan1987/eclipse-workspace/cake-manager/" + filename);
	if(cakes != null) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    String json = null;
	    try {
		json = objectMapper.writeValueAsString(cakes);
		byte[] isr = json.getBytes();
		FileUtils.writeByteArrayToFile(file, isr);

		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentLength(isr.length);
		respHeaders.setContentType(new MediaType("text", "json"));
		respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return file;
	}
	return null;
    }

    @Override
    public Cake saveCake(Cake cake) {
	return cakeRepository.save(cake);
    }

    @Override
    public void deleteById(Integer id) {
	cakeRepository.deleteById(id); 
    }

    @Override
    public void deleteAllCakes() {
	cakeRepository.deleteAll();; 
    }

}
