package com.guterres.people.management.filters.predicates;

import com.guterres.people.management.entity.Profissional;
import com.guterres.people.management.filters.ProfissionalFilter;

import java.util.function.Predicate;

public class ProfissionalPredicate {


    public Predicate<Profissional> getPredicate(ProfissionalFilter pf){
        Predicate<Profissional> predicate = p -> ((pf.Nome != null && p.getNome().contains(pf.Nome)) || pf.Nome == null)
                && ((pf.Celular != null && p.getCelular().contains(pf.Celular)) || pf.Celular == null)
                && ((pf.TelefoneResidencial != null && p.getTelefoneResidencial().contains(pf.TelefoneResidencial)) || pf.TelefoneResidencial == null)
                && ((pf.Funcao != null && p.getFuncao().contains(pf.Funcao)) || pf.Funcao == null)
                && ((pf.Endereco != null && p.getEndereco().contains(pf.Endereco)) || pf.Endereco == null)
                && ((pf.NomeEstabelecimento != null && p.getEstabelecimento().stream().anyMatch(e -> e.getNome().contains(pf.NomeEstabelecimento)) || pf.NomeEstabelecimento == null));
        return predicate;
    }


    public ProfissionalPredicate(){

    }

}
