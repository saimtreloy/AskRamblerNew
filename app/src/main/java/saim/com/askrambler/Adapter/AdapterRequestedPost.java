package saim.com.askrambler.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import saim.com.askrambler.Model.ModelManagePost;
import saim.com.askrambler.Model.ModelRequestedPost;
import saim.com.askrambler.R;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterRequestedPost extends RecyclerView.Adapter<AdapterRequestedPost.PostViewHolder>{

    ArrayList<ModelRequestedPost> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterRequestedPost(ArrayList<ModelRequestedPost> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterRequestedPost(ArrayList<ModelRequestedPost> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_requested_post, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.txtFromWhere.setText(adapterList.get(position).getFrom_where());
        holder.txtRLPhone.setText(adapterList.get(position).getPhone());
        holder.txtEmail.setText(adapterList.get(position).getEmail());
        holder.txtPostDate.setText(adapterList.get(position).getDate());
        holder.txtType.setText(adapterList.get(position).getAd_type());
        holder.txtEDate.setText(adapterList.get(position).getDate2());
        holder.txtRDate.setText(adapterList.get(position).getTo_date());
        holder.txtStatus.setText(adapterList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtFromWhere, txtRLPhone, txtEmail, txtType, txtPostDate, txtRDate, txtEDate, txtStatus;

        public PostViewHolder(View itemView) {
            super(itemView);

            txtFromWhere = (TextView) itemView.findViewById(R.id.txtFromWhere);
            txtRLPhone = (TextView) itemView.findViewById(R.id.txtRLPhone);
            txtEmail = (TextView) itemView.findViewById(R.id.txtRLEmail);
            txtPostDate = (TextView) itemView.findViewById(R.id.txtPostDate);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtEDate = (TextView) itemView.findViewById(R.id.txtEDate);
            txtRDate = (TextView) itemView.findViewById(R.id.txtRDate);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
