package org.florescu.java8examples.model;


public final class Participant {

    private final String name;
    private final TeamColour teamColour;
    private final int age;

    public Participant(String name, TeamColour teamColour, int age) {
        this.name = name;
        this.teamColour = teamColour;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public TeamColour getTeamColour() {
        return teamColour;
    }

    public int getAge() {
        return age;
    }

    public static int compareByAge(Participant a, Participant b) {
        return Integer.compare(a.getAge(), b.getAge());
    }
}
