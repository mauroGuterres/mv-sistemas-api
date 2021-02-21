package com.guterres.people.management.filters.predicates;

import com.guterres.people.management.entity.Estabelecimento;
import com.guterres.people.management.filters.EstabelecimentoFilter;

import java.util.function.Predicate;

public class EstabelecimentoPredicate {



    public EstabelecimentoPredicate(){

    }

    public Predicate<Estabelecimento> getPredicate(EstabelecimentoFilter ef){
        Predicate<Estabelecimento> predicate = est -> ((ef.Nome != null && est.getNome().contains(ef.Nome)) || ef.Nome == null)
                && ((ef.Telefone != null && est.getTelefone().contains(ef.Telefone)) || ef.Telefone == null)
                && ((ef.Endereco != null && est.getEndereco().contains(ef.Endereco)) || ef.Endereco == null)
                && ((ef.NomeProfissional != null && est.getProfissional().stream().anyMatch(e -> e.getNome().contains(ef.NomeProfissional)) || ef.NomeProfissional == null));
        return predicate;
    }

}
