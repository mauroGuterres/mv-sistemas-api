package com.guterres.people.management.service;

import com.guterres.people.management.entity.Profissional;
import com.guterres.people.management.filters.*;
import com.guterres.people.management.filters.predicates.ProfissionalPredicate;
import com.guterres.people.management.repository.ProfissionalRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProfissionalService {

    private String mensagem;
    private ProfissionalPredicate profissionalPredicate = new ProfissionalPredicate();

    @Autowired
    ProfissionalRepository repository;

    @Transactional
    public String create(Profissional profissional) {
        mensagem = "";
        try {
            boolean canCreate = profissional.getId() == 0;
            if (canCreate) {
                repository.save(profissional);
                mensagem = "Profissional inserido com sucesso!";
            }else{
                mensagem = "Tentativa de alterar dados de outro registro via create. A alteração não será feita!";
            }
        } catch (Exception ex) {
            mensagem = ex.getMessage();
        }

        return mensagem;
    }

    @Transactional
    public String Update(Profissional profissional) {
        try {
            Profissional toUpdate = repository.getOne(profissional.getId());
            boolean canUpdate = toUpdate.getId() != 0 && (toUpdate.getId() == profissional.getId());
            if (canUpdate) {
                toUpdate.setNome(profissional.getNome());
                toUpdate.setCelular(profissional.getCelular());
                toUpdate.setEndereco(profissional.getEndereco());
                toUpdate.setFuncao(profissional.getFuncao());
                toUpdate.setTelefoneResidencial(profissional.getTelefoneResidencial());
                if(profissional.getEstabelecimento().size() > 0){
                    toUpdate.setEstabelecimento(profissional.getEstabelecimento());
                }
                repository.save(toUpdate);
                mensagem = "Registro atualizado com sucesso!";
            } else {
                mensagem = "Não foi possível encontrar o profissional!";
            }
        } catch (Exception ex) {
            mensagem = ex.getMessage();
        }
        return mensagem;
    }

    @Transactional
    public String Delete(Integer id) {
        try {
            boolean profissionalExiste = repository.findById(id).isPresent();
            if (profissionalExiste) {
                Profissional result = repository.getOne(id);
                repository.delete(result);
                mensagem = "Profissional excluído com sucesso!";
            } else {
                mensagem = "Profissional não encontrado!";
            }
        } catch (Exception ex) {
            mensagem = "Não foi possível excluir. Profissional não encontrado.";
        }
        return mensagem;
    }

    public List<Profissional> getByFilter(ProfissionalFilter pf, Pageable pageable) {
        List<Profissional> result = pf != null ? repository.findAll(pageable).
                stream().
                filter(this.profissionalPredicate.getPredicate(pf)).collect(Collectors.toList()) :
                repository.findAll(pageable).stream().collect(Collectors.toList());
        return result;
    }

    public List<Profissional> findAll(Pageable pageable) {
        return repository.findAll(pageable).toList();
    }


}
