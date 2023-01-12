/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobapws.pwsa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Mahasiswa;
import java.util.ArrayList;
import java.util.List;
import jpa.MahasiswaJpaController;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@CrossOrigin
@RequestMapping("/mydata")
public class myController {
    
    Mahasiswa data = new Mahasiswa();
    MahasiswaJpaController ctr = new MahasiswaJpaController();
    
    @GetMapping
    public List<Mahasiswa> getAll() {
        List<Mahasiswa> dummy = new ArrayList<>();
        try {
            dummy = ctr.findMahasiswaEntities();
        } catch (Exception e) {
            dummy = List.of();
        }
        return dummy;
    }
    
    @GetMapping("/{id}")
    public List<Mahasiswa> getNameById(@PathVariable("id") int id) {
        List<Mahasiswa> dummy = new ArrayList<>(); // Declare new LIST
        try {
            data = ctr.findMahasiswa(id); // get data from db
            dummy.add(data); // fill data from db to list
        } catch (Exception e) {
            dummy = List.of(); // data not found
        }
        return dummy;
    }
    
    @PostMapping()
    public String createData(HttpEntity<String> paket) {
        String message = "";

        try {
            String json_receive = paket.getBody();

            ObjectMapper map = new ObjectMapper();
        
            data = map.readValue(json_receive, Mahasiswa.class);

            ctr.create(data);
            message = data.getNama()+ " Data Saved";

        } catch (Exception e) {
            message = "Failed";
        }

        return message;
    }
    
    @PutMapping()
    public String editData(HttpEntity<String> paket) {
        String message = "";

        try {
            String json_receive = paket.getBody();

            ObjectMapper map = new ObjectMapper();
        
            data = map.readValue(json_receive, Mahasiswa.class);

            ctr.edit(data);
            message = data.getNama()+ " Data Update";

        } catch (Exception e) {
            message = "Failed";
        }

        return message;
    }
    
    @DeleteMapping()
    public String deleteData(HttpEntity<String> paket) {
        String message = "";

        try {
            String json_receive = paket.getBody();

            ObjectMapper map = new ObjectMapper();
        
            data = map.readValue(json_receive, Mahasiswa.class);

            ctr.destroy(data.getId());
            message = "ID = " +data.getId().toString() + " Data Delete";

        } catch (Exception e) {
            message = "Failed";
        }

        return message;
    }
    
    
}
