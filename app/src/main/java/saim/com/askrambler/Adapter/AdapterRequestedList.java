package saim.com.askrambler.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import saim.com.askrambler.Model.ModelRequestList;
import saim.com.askrambler.Model.ModelRequestedPost;
import saim.com.askrambler.R;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterRequestedList extends RecyclerView.Adapter<AdapterRequestedList.PostViewHolder>{

    ArrayList<ModelRequestList> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterRequestedList(ArrayList<ModelRequestList> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterRequestedList(ArrayList<ModelRequestList> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_requested_list, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.txtFromWhere.setText(adapterList.get(position).getSender_user_name());
        holder.txtRLEmail.setText(adapterList.get(position).getSender_user_email());
        holder.txtRLPhone.setText(adapterList.get(position).getSender_user_mobile());
        holder.txtPostDate.setText(adapterList.get(position).getDate());

        if (adapterList.get(position).getStatus().equals("Confirm")) {
            holder.txtStatus.setTextColor(Color.GREEN);
        } else if (adapterList.get(position).getStatus().equals("Accept")) {
            holder.txtStatus.setTextColor(Color.BLUE);
        } else if (adapterList.get(position).getStatus().equals("Pending")) {
            holder.txtStatus.setTextColor(Color.CYAN);
        } else if (adapterList.get(position).getStatus().equals("Cancel")) {
            holder.txtStatus.setTextColor(Color.RED);
            holder.cardRequestListStatus.setCardBackgroundColor(Color.parseColor("#ffdddd"));
        }
        holder.txtStatus.setText(adapterList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtFromWhere, txtRLEmail, txtRLPhone, txtPostDate, txtStatus;
        CardView cardRequestListStatus;

        public PostViewHolder(View itemView) {
            super(itemView);

            txtFromWhere = (TextView) itemView.findViewById(R.id.txtFromWhere);
            txtRLEmail = (TextView) itemView.findViewById(R.id.txtRLEmail);
            txtRLPhone = (TextView) itemView.findViewById(R.id.txtRLPhone);
            txtPostDate = (TextView) itemView.findViewById(R.id.txtPostDate);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);

            cardRequestListStatus = (CardView) itemView.findViewById(R.id.cardRequestListStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (adapterList.get(position).getStatus().equals("Confirm")) {

            } else if (adapterList.get(position).getStatus().equals("Accept")) {

            } else if (adapterList.get(position).getStatus().equals("Pending")) {

            } else if (adapterList.get(position).getStatus().equals("Cancel")) {

            }
        }
    }
}
