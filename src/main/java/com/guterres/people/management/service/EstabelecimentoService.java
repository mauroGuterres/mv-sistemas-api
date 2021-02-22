package com.guterres.people.management.service;

import com.guterres.people.management.dto.ProfissionalEstabelecimento;
import com.guterres.people.management.entity.Estabelecimento;
import com.guterres.people.management.entity.Profissional;
import com.guterres.people.management.filters.EstabelecimentoFilter;
import com.guterres.people.management.filters.ProfissionalFilter;
import com.guterres.people.management.filters.predicates.EstabelecimentoPredicate;
import com.guterres.people.management.repository.EstabelecimentoRepository;
import com.guterres.people.management.repository.ProfissionalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoService {

    String mensagem;
    EstabelecimentoPredicate estabelecimentoPredicate = new EstabelecimentoPredicate();

    @Autowired
    public EstabelecimentoRepository repository;

    @Autowired
    public ProfissionalRepository profissionalRepository;

    @Transactional
    public String create(Estabelecimento estabelecimento) {
        mensagem = "";
        try {
            repository.save(estabelecimento);
            mensagem = "Estabelecimento inserido com sucesso!";
        } catch (Exception ex) {
            mensagem = ex.getMessage();
        }

        return mensagem;
    }

    public List<Estabelecimento> getByFilter(EstabelecimentoFilter ef, Pageable pageable) {
        List<Estabelecimento> result = ef != null ? repository.findAll(pageable).
                stream().
                filter(this.estabelecimentoPredicate.getPredicate(ef)).collect(Collectors.toList()) : repository.findAll(pageable).stream().collect(Collectors.toList());
        return result;
    }

    @Transactional
    public String update(Estabelecimento estabelecimento) {
        mensagem = "";
        boolean estabelecimentoExiste = repository.findById(estabelecimento.getId()).isPresent();
        if (estabelecimentoExiste) {
            Estabelecimento toUpdate = estabelecimento;
            repository.save(toUpdate);
            mensagem = "Estabelecimento alterado com sucesso!";
        } else {
            mensagem = "Estabelecimento não encontrado";
        }
        return mensagem;
    }


    @Transactional
    public String Delete(Integer id) {
        mensagem = "";
        boolean existeEstabelecimento = repository.findById(id).isPresent();
        Estabelecimento result = repository.getOne(id);
        if (existeEstabelecimento) {
            repository.delete(result);
            mensagem = "Estabelecimento excluido com sucesso!";
        } else {
            mensagem = "Estabelecimento não encontrado!";
        }
        return mensagem;
    }

    public Estabelecimento getById(Integer id) {
        Estabelecimento estabelecimento = repository.findById(id).isPresent() ? repository.findById(id).get() : new Estabelecimento();
        return estabelecimento;
    }

    public List<Estabelecimento> findAll(Pageable pageable) {
        return repository.findAll(pageable).toList();
    }

    @Transactional
    public String linkProfissionalEstabelecimento(ProfissionalEstabelecimento pe) {
        mensagem = "Profissional ou Estabelecimento inexistente.";
        try {
            boolean profissionalExiste = profissionalRepository.findById(pe.getIdProfissional()).isPresent();
            boolean estabelecimentoExiste = repository.findById(pe.getIdEstabelecimento()).isPresent();

            if (estabelecimentoExiste && profissionalExiste) {
                Profissional profissional = profissionalRepository.getOne(pe.getIdProfissional());
                Estabelecimento estabelecimento = repository.getOne(pe.getIdEstabelecimento());
                Set<Profissional> profEst = estabelecimento.getProfissional();
                Set<Estabelecimento> estProf = profissional.getEstabelecimento();
                boolean estabelecimentoContemProfissional = estProf.contains(estabelecimento);
                boolean profissionalTrabalhaNoEstabelecimento = profEst.contains(profissional);
                if (estabelecimentoContemProfissional && profissionalTrabalhaNoEstabelecimento) {
                    mensagem = "O profissional " + profissional.getNome() + " já trabalha para o estabelecimento " + estabelecimento.getNome() + "!";
                } else if (!profissionalTrabalhaNoEstabelecimento && !estabelecimentoContemProfissional) {
                    profEst.add(profissional);
                    estabelecimento.setProfissional(profEst);
                    estProf.add(estabelecimento);
                    profissional.setEstabelecimento(estProf);
                    repository.save(estabelecimento);
                    profissionalRepository.save(profissional);
                    mensagem = "Vínculo entre profissional " + profissional.getNome() + " e estabelecimento " + estabelecimento.getNome() + " realizado com sucesso!";
                } else {
                    mensagem = "Profissional ou Estabelecimento não encontrado.";
                }
            }
        } catch (Exception ex) {
            mensagem = ex.getMessage();
        }
        return mensagem;
    }

    @Transactional
    public String unLinkProfissionalEstabelecimento(ProfissionalEstabelecimento pe) {
        mensagem = "Profissional ou Estabelecimento inexistente.";
        try {
            boolean profissionalExiste = profissionalRepository.findById(pe.getIdProfissional()).isPresent();
            boolean estabelecimentoExiste = repository.findById(pe.getIdEstabelecimento()).isPresent();

            if (estabelecimentoExiste && profissionalExiste) {
                Profissional profissional = profissionalRepository.getOne(pe.getIdProfissional());
                Estabelecimento estabelecimento = repository.getOne(pe.getIdEstabelecimento());
                Set<Profissional> profEst = estabelecimento.getProfissional();
                Set<Estabelecimento> estProf = profissional.getEstabelecimento();
                boolean estabelecimentoContemProfissional = estProf.contains(estabelecimento);
                boolean profissionalTrabalhaNoEstabelecimento = profEst.contains(profissional);
                if (estabelecimentoContemProfissional && profissionalTrabalhaNoEstabelecimento) {
                    estProf.remove(estabelecimento);
                    profEst.remove(profissional);
                    estabelecimento.setProfissional(profEst);
                    profissional.setEstabelecimento(estProf);
                    repository.save(estabelecimento);
                    profissionalRepository.save(profissional);
                    mensagem = "O Vínculo entre o profissional " + profissional.getNome() + " e o estabelecimento " + estabelecimento.getNome() + " foi desfeito com sucesso!";
                } else {
                    mensagem = "Vínculo inexistente.";
                }
            }
        } catch (Exception ex) {
            mensagem = ex.getMessage();
        }

        return mensagem;
    }


}
