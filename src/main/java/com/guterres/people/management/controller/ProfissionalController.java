package com.guterres.people.management.controller;

import com.guterres.people.management.entity.Profissional;
import com.guterres.people.management.filters.ProfissionalFilter;
import com.guterres.people.management.service.ProfissionalService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("profissional")
public class ProfissionalController {

    String mensagem;

    @Autowired
    ProfissionalService service;

    @GetMapping("")
    public List<Profissional> BuscarTodos(){
        Pageable pageable = PageRequest.of(0,100);
        return service.findAll(pageable);
    }


    @GetMapping("buscar/{Page}/{Quantity}")
    public List<Profissional> BuscaPaginada(@RequestBody(required = false) ProfissionalFilter pf, @PathVariable Integer Page, @PathVariable Integer Quantity){
        Pageable pageable = PageRequest.of(Page, Quantity);
        List<Profissional> result = service.getByFilter(pf,pageable);
        return result;
    }

    @PostMapping("cadastrar")
    public String cadastrar(@RequestBody Profissional profissional) {
        try {
            mensagem = service.create(profissional);
        } catch (Exception ex) {
            mensagem = ex.getMessage();
        }
        return mensagem;
    }

    @PutMapping("atualizar")
    public String atualizar(@RequestBody Profissional profissional) {
        try {
            mensagem = service.Update(profissional);
        } catch (Exception ex) {
            mensagem = ex.getMessage();
        }
        return mensagem;
    }

    @DeleteMapping("excluir/{Id}")
    public String excluir(@PathVariable  Integer Id){
        mensagem = "";
        try{
            mensagem = service.Delete(Id);
        }catch(Exception ex){
             mensagem = ex.getMessage();
        }
        return mensagem;
    }


}