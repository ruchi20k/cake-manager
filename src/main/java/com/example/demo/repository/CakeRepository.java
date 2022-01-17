package com.example.demo.repository;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Cake;

public interface CakeRepository extends CrudRepository<Cake, Integer>{

}
