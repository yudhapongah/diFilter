package com.yudha.difilter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yudha.difilter.R;
import com.yudha.difilter.model.Lesson;
import com.yudha.difilter.model.Soal;

import java.util.List;

public class SoalAdapter extends RecyclerView.Adapter<SoalAdapter.SoalViewHolder> {

    private List<Soal> soalList;
    private OnAnswerChange onAnswerChange;

    public SoalAdapter(List<Soal> soalList, OnAnswerChange onAnswerChange) {
        this.soalList = soalList;
        this.onAnswerChange = onAnswerChange;
    }


    @NonNull
    @Override
    public SoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_soal,parent,false);
        return new SoalViewHolder(view, onAnswerChange);
    }

    @Override
    public void onBindViewHolder(@NonNull SoalViewHolder holder, int position) {
        holder.bind(soalList.get(position),position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return soalList.size();
    }

    public void setSoalList(List<Soal> list) {
        soalList = list;
        notifyDataSetChanged();

    }

    public interface OnAnswerChange{
        void changeAnswer(Boolean answer,int position);
    }

    static class SoalViewHolder  extends RecyclerView.ViewHolder{

        private final TextView tvSoal;
        private final RadioButton rbJawabanA,rbJawabanB,rbJawabanC,rbJawabanD;
        private final RadioGroup rgJawaban;
        private final OnAnswerChange onAnswerChange;


        public SoalViewHolder(@NonNull View itemView, OnAnswerChange onAnswerChange) {
            super(itemView);

            tvSoal = itemView.findViewById(R.id.tv_soal_desc);
            rbJawabanA = itemView.findViewById(R.id.rb_jawaban_a);
            rbJawabanB= itemView.findViewById(R.id.rb_jawaban_b);
            rbJawabanC= itemView.findViewById(R.id.rb_jawaban_c);
            rbJawabanD= itemView.findViewById(R.id.rb_jawaban_d);

            rgJawaban = itemView.findViewById(R.id.rg_jawaban);

            this.onAnswerChange = onAnswerChange;
        }

        public void bind(final Soal soal, final int position) {
            tvSoal.setText(soal.soal);
            rbJawabanA.setText(soal.jawabanA);
            rbJawabanB.setText(soal.jawabanB);
            rbJawabanC.setText(soal.jawabanC);
            rbJawabanD.setText(soal.jawabanD);

            rgJawaban.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    String jawaban = "";
                    switch (checkedId){
                        case R.id.rb_jawaban_a:
                            jawaban = "A";
                            break;
                        case R.id.rb_jawaban_b:
                            jawaban = "B";
                            break;
                        case R.id.rb_jawaban_c:
                            jawaban = "C";
                            break;
                        case R.id.rb_jawaban_d:
                            jawaban = "D";
                            break;
                    }

                    onAnswerChange.changeAnswer(jawaban.equalsIgnoreCase(soal.jawaban),position);
                }
            });


        }
    }
}
