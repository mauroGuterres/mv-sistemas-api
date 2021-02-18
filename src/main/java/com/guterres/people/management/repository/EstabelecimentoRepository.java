package com.guterres.people.management.repository;

import com.guterres.people.management.entity.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer> {


}
