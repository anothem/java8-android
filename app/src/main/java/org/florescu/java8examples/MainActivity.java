package org.florescu.java8examples;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.florescu.java8examples.data.ParticipantGenerator;
import org.florescu.java8examples.model.Participant;
import org.florescu.java8examples.model.TeamColour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button1)
    Button button1;

    @BindView(R.id.main_panel)
    ViewGroup mainPanel;

    @BindView(R.id.participant_red_list)
    RecyclerView participantRedList;

    @BindView(R.id.participant_green_list)
    RecyclerView participantGreenList;

    @SuppressWarnings("Convert2Lambda")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiSetup();

        // Lambda with no parameters
        Runnable r2 = () -> Timber.d("Lambda test");


        r2.run();

        // Lambda with parameter, not used
        // Android OnClickListener
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mainPanel, "Click listener triggered", Snackbar.LENGTH_SHORT).show();
            }
        });

        button1.setOnClickListener(v -> Snackbar.make(mainPanel, "Click listener triggered", Snackbar.LENGTH_SHORT).show());

        List<Participant> participants = ParticipantGenerator.generateParticipants(12);

        //noinspection Convert2Lambda
        participantRedList.setAdapter(new ParticipantAdapter(filterParticipantsOldJavaWay(participants,
                new FilterCondition<Participant>() {
                    @Override
                    public boolean accept(Participant candidate) {
                        return candidate.getTeamColour() == TeamColour.RED;
                    }
                })));

        participantGreenList.setAdapter(new ParticipantAdapter(filterParticipantsOldJavaWay(participants,
                (p) -> p.getTeamColour() == TeamColour.GREEN)));

    }

    // This is the old java way
    private List<Participant> filterParticipantsOldJavaWay(List<Participant> participants,
                                                           FilterCondition<Participant> filterCondition) {
        List<Participant> output = new ArrayList<>();
        for (Participant participant : participants) {
            if (filterCondition.accept(participant)) {
                output.add(participant);
            }
        }
        return output;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private List<Participant> filterParticipantsNewJavaWay(List<Participant> participants,
                                                           FilterCondition<Participant> filterCondition) {
        return participants.stream()
                .filter(filterCondition::accept)
                .collect(Collectors.toList());
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Participant> filterParticipantsAndroidNWay(List<Participant> participants) {
        return participants.stream()
                .filter(participant -> participant.getTeamColour() == TeamColour.RED)
                .collect(Collectors.toList());
    }

    @TargetApi(Build.VERSION_CODES.N)
    private List<Participant> filterParticipantsAndroidNWayGeneric(List<Participant> participants,
                                                                   Predicate<Participant> predicate) {
        return participants.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void printParticipants(List<Participant> participants) {
        participants.forEach(participant -> {
            if (participant.getTeamColour() == TeamColour.RED) {
                // TODO do something
            }
        });
    }

    @SuppressWarnings({"unused", "Convert2Lambda"})
    private void sortParticipantsByAge(List<Participant> participants) {
        Collections.sort(participants, new Comparator<Participant>() {
            @Override
            public int compare(Participant p1, Participant p2) {
                return Integer.compare(p1.getAge(), p2.getAge());
            }
        });
    }

    @SuppressWarnings({"unused"})
    private void sortParticipantsByAgeWithLambda(List<Participant> participants) {
        Collections.sort(participants, (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
    }

    @SuppressWarnings({"unused", "Convert2MethodRef"})
    private void sortParticipantsByAgeWithLambda2(List<Participant> participants) {
        Collections.sort(participants, (p1, p2) -> Participant.compareByAge(p1, p2));
    }


    @SuppressWarnings({"unused"})
    private void sortParticipantsByAgeWithMethodRef(List<Participant> participants) {
        Collections.sort(participants, Participant::compareByAge);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @SuppressWarnings({"unused"})
    private void sortParticipantsByAgeWithMethodRefN(List<Participant> participants) {
        Collections.sort(participants, Comparator.comparingInt(Participant::getAge));
    }


    // Who is the youngest?
    @SuppressWarnings("unused")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void funWithStreams(List<Participant> participants) {
        // Find out the minimum age
        OptionalInt minimumAge = participants.stream()
                .mapToInt(Participant::getAge)
                .min();


        // Find the youngest participant
        Optional<Participant> youngestParticipant = participants.stream()
                .min(Comparator.comparingInt(Participant::getAge));
        if (youngestParticipant.isPresent()) {
            // ....
        }

        // Find the youngest participant
        participants.stream()
                .min(Comparator.comparingInt(Participant::getAge))
                .ifPresent(participant -> Timber.d("Youngest participant %s", participant.getName()));

        participants.parallelStream()
                .min(Comparator.comparingInt(Participant::getAge))
                .ifPresent((participant -> Timber.d("Youngest participant %s", participant.getName())));


        // Sort participants by age
        participants.sort(Comparator.comparingInt(Participant::getAge));

        // Get the names of all participants
        SortedSet<String> names =
                participants.stream()
                        .map(Participant::getName)
                        .sorted()
                        .limit(2)
                        .collect(Collectors.toCollection(TreeSet::new));

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void howMany20YearOlds(List<Participant> participants) {
        participants.stream()
                .filter(participant -> participant.getAge() == 20)
                .count();
    }

    private void uiSetup() {
        // Butterknife
        ButterKnife.bind(this);

        participantRedList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        participantGreenList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
