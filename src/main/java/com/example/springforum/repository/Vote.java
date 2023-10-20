package com.example.springforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Vote extends JpaRepository<Vote, Long> {
}
