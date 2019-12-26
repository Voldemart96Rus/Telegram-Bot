package com.telegramBOT.telegramBOT.service;

import com.telegramBOT.telegramBOT.model.FootballTeams;
import com.telegramBOT.telegramBOT.repository.FootballTeamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FootballTeamsService {
    @Autowired
    private final FootballTeamsRepository footballLeagueRepository;

    public FootballTeamsService(FootballTeamsRepository footballLeagueRepository) {
        this.footballLeagueRepository = footballLeagueRepository;
    }

    public FootballTeams findById(Long id){
        return footballLeagueRepository.findById(id);
    }

    public FootballTeams findByName(String name) { return  footballLeagueRepository.findByName(name);}

    public void createFootballLeague(FootballTeams league) {
        footballLeagueRepository.save(league);
    }
    public long count() {
        return footballLeagueRepository.count();
    }
}
