package saim.com.askrambler.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import saim.com.askrambler.Activity.PostDetailActivity;
import saim.com.askrambler.Activity.RequestList;
import saim.com.askrambler.Model.ModelManagePost;
import saim.com.askrambler.Model.ModelPostShort;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.CircleTransform;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterManagePost extends RecyclerView.Adapter<AdapterManagePost.PostViewHolder>{

    ArrayList<ModelManagePost> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterManagePost(ArrayList<ModelManagePost> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterManagePost(ArrayList<ModelManagePost> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_manage_post, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.txtFromWhere.setText(adapterList.get(position).getFrom_where());
        holder.txtPhone.setText(adapterList.get(position).getPhone());
        holder.txtType.setText(adapterList.get(position).getAd_type());
        holder.txtEDate.setText(adapterList.get(position).getDate());
        holder.txtAPDate.setText(adapterList.get(position).getTo_date());
        holder.txtRequest.setText(adapterList.get(position).getRequest() + " Request Found");

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView imgEditList, imgDeleteList;
        TextView txtFromWhere, txtPhone, txtType, txtEDate, txtAPDate, txtRequest;

        public PostViewHolder(View itemView) {
            super(itemView);

            imgEditList = (CardView) itemView.findViewById(R.id.imgEditList);
            imgDeleteList = (CardView) itemView.findViewById(R.id.imgDeleteList);

            txtFromWhere = (TextView) itemView.findViewById(R.id.txtFromWhere);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtEDate = (TextView) itemView.findViewById(R.id.txtEDate);
            txtAPDate = (TextView) itemView.findViewById(R.id.txtAPDate);
            txtRequest = (TextView) itemView.findViewById(R.id.txtRequest);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), RequestList.class).putExtra("AD_ID", adapterList.get(getAdapterPosition()).getId()));
        }
    }
}
