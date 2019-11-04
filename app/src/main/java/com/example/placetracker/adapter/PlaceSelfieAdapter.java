package com.example.placetracker.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.placetracker.MainActivity;
import com.example.placetracker.R;
import com.example.placetracker.domain.PlaceSelfie;
import com.example.placetracker.utility.CurrentJob;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceSelfieAdapter extends RecyclerView.Adapter<PlaceSelfieAdapter.MyViewHolder> {
    ArrayList<PlaceSelfie> data;


    public PlaceSelfieAdapter(ArrayList<PlaceSelfie> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.screen_list, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(myViewHolder);


        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        PlaceSelfie selfie = data.get(i);
        CurrentJob.getInstance().setPlaceSelfie(selfie);
        myViewHolder.jobName.setText(selfie.getJobName());
        myViewHolder.jobDesc.setText( selfie.getJobDescription());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = "";
        if (selfie.getDateOfJob() != null) {
            strDate= formatter.format(selfie.getDateOfJob());
        }
        myViewHolder.date.setText( strDate);

        //convert byte to bitmap take from contact class
        if (selfie.getFirstSelfie() != null && selfie.getFirstSelfie().length != 0) {
            byte[] outImage = selfie.getFirstSelfie();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);

            myViewHolder.selfie.setImageBitmap(theImage);
        } /*else {
            try {
                byte[] outImage = new Image() ;
                ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                myViewHolder.selfie.setImageBitmap(theImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }





    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        ImageView selfie;
        TextView jobName;
        TextView jobDesc;
        TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            selfie = itemView.findViewById(R.id.selfie1);
            jobName = itemView.findViewById(R.id.jobname);
            jobName.setTextColor(Color.BLACK);
            jobDesc = itemView.findViewById(R.id.jobdescription);
            jobDesc.setTextColor(Color.BLUE);
            date = itemView.findViewById(R.id.dateTime);
            date.setTextColor(Color.CYAN);
        }

        @Override
        public void onClick(View v) {
            CurrentJob.getInstance().getMainActivity().clickAction();
            CurrentJob.getInstance().setPlaceSelfie(data.get(getAdapterPosition()));
        }
    }
}
