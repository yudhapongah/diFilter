package com.yudha.difilter.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yudha.difilter.R;
import com.yudha.difilter.model.Lesson;

import java.util.List;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.LessonItemViewHolder> {

    private List<Lesson> lessonList;

    public LessonListAdapter(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }


    @NonNull
    @Override
    public LessonItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_lesson, parent, false);
        return new LessonItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonItemViewHolder holder, int position) {
        holder.bind(lessonList.get(position),position+1);
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
        this.notifyDataSetChanged();
    }

    static class LessonItemViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvLessonIndicator;
        private final TextView tvLessonTitle;
        private final ImageButton ibLessonLink;
        public LessonItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLessonIndicator = itemView.findViewById( R.id.tv_lesson_indicator);
            tvLessonTitle = itemView.findViewById( R.id.tv_lesson_title);
            ibLessonLink = itemView.findViewById(R.id.iv_lesson_link);
        }

        public void bind(final Lesson lesson, int pos){
            tvLessonTitle.setText(lesson.judul);
            tvLessonIndicator.setText("Materi "+ pos + " :");
            ibLessonLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(lesson.link)));
                }
            });
        }
    }
}

