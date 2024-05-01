package com.workintech.S17D2.rest;


import com.workintech.S17D2.dto.DeveloperResponse;
import com.workintech.S17D2.model.Developer;
import com.workintech.S17D2.model.DeveloperFactory;
import com.workintech.S17D2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")

public class DeveloperController {

    private Map<Integer, Developer> developers;


    private Taxable taxable;

    @Autowired
    public DeveloperController(Taxable taxable){
        this.taxable = taxable;
    }


    @PostConstruct
    public void init(){
        this.developers = new HashMap<>();
    }

    @PostMapping
    public DeveloperResponse create(@RequestBody Developer developer){
        Developer createdDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        this.developers.put(createdDeveloper.getId(), createdDeveloper);
        return new DeveloperResponse(createdDeveloper,"create işlemi başarılı bir şekilde tamamlandı", HttpStatus.CREATED.value());
    }


    @GetMapping
    public List<Developer> getAll(){
        return developers.values().stream().toList(); // burada arraylist e çevirdik
    }

    @GetMapping("/{id}")
    public DeveloperResponse get(@PathVariable("id") Integer id){
        Developer developer = developers.get(id);
        if(developer == null){
            return new DeveloperResponse(null,id+ "id li değere ait kayıt bulunamadı",HttpStatus.NOT_FOUND.value());
        }
        return new DeveloperResponse(developer,"kayıt bulundu", HttpStatus.OK.value());
    }

    @PutMapping("/{id}")
    public DeveloperResponse update(@PathVariable("id") Integer id, @RequestBody Developer developer ){
        developer.setId(id);
       Developer newDeveloper =  DeveloperFactory.createDeveloper(developer,taxable);
       developers.put(newDeveloper.getId(), newDeveloper);
       return new DeveloperResponse(newDeveloper,"update başarılı",HttpStatus.OK.value());
    }


    @DeleteMapping("/{id}")
    public DeveloperResponse delete(@PathVariable("id") Integer id){
        Developer removedDeveloper = developers.get(id);
        developers.remove(id);
        return new DeveloperResponse(removedDeveloper,"silme işlemi başarılı",HttpStatus.NO_CONTENT.value());
    }


}
