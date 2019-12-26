package com.telegramBOT.telegramBOT.repository;

import com.telegramBOT.telegramBOT.model.FootballTeams;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballTeamsRepository extends JpaRepository<FootballTeams, String> {
    FootballTeams findById(Long id);
    FootballTeams findByName(String name);
}
