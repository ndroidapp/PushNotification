package com.example.noor.pushnotification;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Noor on 2/17/2018.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>{
    private Context context;
    public List<Users> usersList;

    public UsersRecyclerAdapter(Context context,List<Users> usersList) {
        this.usersList=usersList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvUserName.setText(usersList.get(position).getName());
        CircleImageView userImageView = holder.circleProfilePic;
        Glide.with(context).load(usersList.get(position).getImage()).into(userImageView);
        final String user_id=usersList.get(position).userId;
        final String user_name=usersList.get(position).getName();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSendActivity = new Intent(context,SendActivity.class);
                intentSendActivity.putExtra("user_id",user_id);
                intentSendActivity.putExtra("user_name",user_name);
                context.startActivity(intentSendActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        private TextView tvUserName;
        private CircleImageView circleProfilePic;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            tvUserName=mView.findViewById(R.id.tvUserName);
            circleProfilePic=mView.findViewById(R.id.circleProfilePic);
        }
    }
}
