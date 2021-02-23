package com.guterres.people.management.controller;

import com.guterres.people.management.entity.Estabelecimento;
import com.guterres.people.management.entity.Profissional;
import com.guterres.people.management.filters.ProfissionalFilter;
import com.guterres.people.management.service.ProfissionalService;
import org.aspectj.weaver.ast.Var;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


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


    @PostMapping("buscar/{Page}/{Quantity}")
    public List<Profissional> BuscaPaginada(@RequestBody(required = false) ProfissionalFilter pf, @PathVariable Integer Page, @PathVariable Integer Quantity){
        Pageable pageable = PageRequest.of(Page, Quantity);
        List<Profissional> result = service.getByFilter(pf,pageable);
        return result;
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("cadastrar")
    public Object cadastrar(@RequestBody Profissional profissional) {

        try {
            if(profissional.getId() == null){
                mensagem = service.create(profissional);
            }else{
                mensagem = service.update(profissional);
            }
        } catch (Exception ex) {
            mensagem =  ex.getMessage();
        }
        return new String[]{mensagem};
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

    @GetMapping("get/{Id}")
    public Profissional getById(@PathVariable Integer Id) {
        Profissional result;
        try {
            result = service.getById(Id);
            return result;
        } catch (Exception ex) {
            result = new Profissional();
        }

        return result;

    }


}