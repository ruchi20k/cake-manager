package com.example.demo.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import com.example.demo.entity.Cake;

public interface ICakeService {
    public List<Cake> getAllCakes();
    public Cake getCakeById(Integer Id);
    public File downloadCakes();
    public Cake saveCake(Cake cake);
    public void deleteById(Integer Id);
    public void deleteAllCakes();
}
