package com.guterres.people.management.controller;

import com.guterres.people.management.dto.ProfissionalEstabelecimento;
import com.guterres.people.management.entity.Estabelecimento;

import com.guterres.people.management.filters.EstabelecimentoFilter;
import com.guterres.people.management.service.EstabelecimentoService;
import com.guterres.people.management.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("estabelecimento")
public class EstabelecimentoController {

    String mensagem;

    @Autowired
    EstabelecimentoService service;

    @GetMapping("")
    public List<Estabelecimento> BuscarTodos() {
        Pageable pageable = PageRequest.of(0, 100);
        return service.findAll(pageable);
    }

    @PostMapping("buscar/{Page}/{Quantity}")
    public List<Estabelecimento> BuscaPaginada(@RequestBody(required = false) EstabelecimentoFilter ef, @PathVariable Integer Page, @PathVariable  Integer Quantity) {
        Pageable pageable = PageRequest.of(Page, Quantity);
        return service.getByFilter(ef,pageable);
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("cadastrar")
    public Object cadastrar(@RequestBody Estabelecimento estabelecimento) {
        try {
             if(estabelecimento.getId() == null){
                 mensagem = service.create(estabelecimento);
             }else{
                 mensagem = service.update(estabelecimento);
             }
        } catch (Exception ex) {
            mensagem =  ex.getMessage();
        }
        return new String[]{mensagem};
    }

    @PutMapping("atualizar")
    public String atualizar(@RequestBody Estabelecimento estabelecimento) {
        try {
            service.update(estabelecimento);
            return "Estabelecimento atualizado com sucesso!";

        } catch (Exception ex) {
            return ex.getMessage();

        }
    }

    @DeleteMapping("excluir/{Id}")
    public Object excluir(@PathVariable Integer Id) {
        try {
            mensagem = service.Delete(Id);
        } catch (Exception ex) {
            mensagem = ex.getMessage();
        }

        return new String[]{mensagem};

    }

    @GetMapping("get/{Id}")
    public Estabelecimento getById(@PathVariable Integer Id) {
        Estabelecimento result;
        try {
            result = service.getById(Id);
            return result;
        } catch (Exception ex) {
            result = new Estabelecimento();
        }

        return result;

    }

    @PutMapping("linkProfissionalEstabelecimento")
    public String linkProfissionalEstabelecimento(@RequestBody ProfissionalEstabelecimento pe) {
        mensagem = "";
        try{
            mensagem = service.linkProfissionalEstabelecimento(pe);
        }
        catch(Exception ex){
            mensagem = ex.getMessage();
        }
        return mensagem;
    }
    @PutMapping("unLinkProfissionalEstabelecimento")
    public String unLinkProfissionalEstabelecimento(@RequestBody ProfissionalEstabelecimento pe){
        mensagem = "";
        try{
            mensagem = service.unLinkProfissionalEstabelecimento(pe);
        }catch(Exception ex){
            mensagem = ex.getMessage();
        }
        return mensagem;
    }
}