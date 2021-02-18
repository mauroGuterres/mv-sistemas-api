package com.guterres.people.management.repository;



import com.guterres.people.management.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {



}
