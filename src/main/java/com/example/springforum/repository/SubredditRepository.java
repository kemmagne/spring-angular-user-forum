package com.example.springforum.repository;

import com.example.springforum.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}
