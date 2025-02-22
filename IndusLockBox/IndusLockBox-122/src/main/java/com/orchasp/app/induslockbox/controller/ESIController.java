
package com.orchasp.app.induslockbox.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orchasp.app.induslockbox.entity.ESI;
import com.orchasp.app.induslockbox.service.ESIService;

@RestController
@RequestMapping("/api/admin/esi")
@CrossOrigin(origins = "http://localhost:4200")
public class ESIController {

    @Autowired
    private ESIService esiService;

    @GetMapping("/fetchall")
    public List<ESI> getAllESIs() {
        return esiService.findAll();
    }

    @GetMapping("/fetchbyid/{id}")
    public ResponseEntity<ESI> getESIById(@PathVariable Long id) {
        Optional<ESI> ESI = esiService.findById(id);
        return ESI.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")//http://localhost:8080/api/ESIs
    public ESI createESI(@RequestBody ESI ESI) {
        return esiService.save(ESI);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ESI> updateESI(@PathVariable Long id, @RequestBody ESI ESIDetails) {
        try {
            ESI updatedESI = esiService.updateESI(id, ESIDetails);
            return ResponseEntity.ok(updatedESI);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteESI(@PathVariable Long id) {
    	esiService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/activate/{id}")
    public void activateESI(@PathVariable Long id) {
        esiService.activateById(id);
    }
    
    @GetMapping("/company/{company_id}")//http://localhost:8080/api/ESI/company/1
    public ResponseEntity<List<ESI>> getESIByCompanyId(@PathVariable Long company_id) {
        List<ESI> ESIDetails = esiService.findByCompany_id(company_id);
        return ResponseEntity.ok(ESIDetails);
    }
    @GetMapping("/decrypt/{id}")
    public String getDecryptPassword(@PathVariable Long id) {
        return esiService.GetDecryptPassword(id);
    }
}