package org.florescu.java8examples;

import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.florescu.java8examples.model.Participant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {

    private final List<Participant> participants;

    ParticipantAdapter(List<Participant> participants) {
        this.participants = participants;
    }

    @Override
    public ParticipantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ParticipantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ParticipantViewHolder holder, int position) {
        Participant participant = participants.get(position);
        holder.participantName.setText(participant.getName());
        @ColorInt int color = ContextCompat.getColor(holder.participantName.getContext(), participant.getTeamColour().getColorRes());
        holder.participantName.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    class ParticipantViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.participant_name)
        TextView participantName;

        public ParticipantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
