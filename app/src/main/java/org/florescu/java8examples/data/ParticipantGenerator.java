package org.florescu.java8examples.data;


import org.florescu.java8examples.model.Participant;
import org.florescu.java8examples.model.TeamColour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticipantGenerator {

    private static final String[] VALID_NAMES = {
            "Alice",
            "Bob",
            "Charlie",
            "David",
            "Ellen",
            "Frank",
            "George",
            "Harry",
            "Iain",
            "Jake",
            "Kyle",
            "Laura",
            "Mary",
            "Nick",
            "Ollie",
            "Peter"
    };


    public static List<Participant> generateParticipants(int size) {
        List<Participant> participants = new ArrayList<>(size);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++) {
            String name = VALID_NAMES[i % VALID_NAMES.length];
            TeamColour teamColour = random.nextInt() % 2 == 0
                    ? TeamColour.RED
                    : TeamColour.GREEN;
            int age = 1 + random.nextInt(100);
            participants.add(new Participant(name, teamColour, age));
        }
        return participants;
    }
}