package com.placetracker.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.placetracker.R;
import com.placetracker.domain.PlaceSelfieRest;
import com.placetracker.utility.CommonUtility;
import com.placetracker.utility.CurrentJob;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceSelfieAdapter extends RecyclerView.Adapter<PlaceSelfieAdapter.MyViewHolder> {
    ArrayList<PlaceSelfieRest> data;


    public PlaceSelfieAdapter(ArrayList<PlaceSelfieRest> data) {
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
        PlaceSelfieRest selfie = data.get(i);
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
        if (selfie.getFirstSelfie() != null && selfie.getFirstSelfie().length() != 0) {
            byte[] outImage = CommonUtility.decodeImage(selfie.getFirstSelfie());
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
        if(selfie.getAddress1() != null) {
            myViewHolder.address.setText(selfie.getAddress1());
        }
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
        TextView address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            selfie = itemView.findViewById(R.id.selfie1);
            jobName = itemView.findViewById(R.id.jobname);
            jobName.setTextColor(Color.BLACK);
            jobDesc = itemView.findViewById(R.id.jobdescription);
            jobDesc.setTextColor(Color.BLUE);
            date = itemView.findViewById(R.id.dateTime);
            date.setTextColor(Color.CYAN);
            address = itemView.findViewById(R.id.geoaddress);
            address.setTextColor(Color.CYAN);
        }

        @Override
        public void onClick(View v) {
            CurrentJob.getInstance().getMainActivity().clickAction();
            CurrentJob.getInstance().setPlaceSelfie(data.get(getAdapterPosition()));
        }
    }
}
