package com.telegramBOT.telegramBOT.model;


import javax.persistence.*;

@Entity
@Table(name = "football_teams")
public class FootballTeams {
    @Id
    @Column
    private Long id;

    @Column
    private String name;

    public FootballTeams() {
    }

    public FootballTeams(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
