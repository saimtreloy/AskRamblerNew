package saim.com.askrambler.Adapter;

import android.content.Context;
import android.content.Intent;
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
import saim.com.askrambler.Model.ModelPostShort;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.CircleTransform;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.PostViewHolder>{

    ArrayList<ModelPostShort> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterPost(ArrayList<ModelPostShort> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterPost(ArrayList<ModelPostShort> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_post, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.txtListUser.setText(adapterList.get(position).getFull_name());
        holder.txtListCategory.setText(adapterList.get(position).getAd_type());
        holder.txtListDetail.setText(adapterList.get(position).getDetails());
        holder.txtToWhere.setText(adapterList.get(position).getTo_where());
        holder.txtToDate.setText(adapterList.get(position).getTo_date());
        Glide.with(holder.txtToWhere.getContext())
                .load(adapterList.get(position).getUser_photo()).transform(new CircleTransform(holder.imgAllPostList.getContext()))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imgAllPostList);
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgAllPostList;
        TextView txtListUser,txtListCategory,txtListDetail, txtToDate, txtToWhere;

        public PostViewHolder(View itemView) {
            super(itemView);

            imgAllPostList = (ImageView) itemView.findViewById(R.id.imgAllPostList);
            txtListUser = (TextView) itemView.findViewById(R.id.txtListUser);
            txtListCategory = (TextView) itemView.findViewById(R.id.txtListCategory);
            txtListDetail = (TextView) itemView.findViewById(R.id.txtListDetail);
            txtToWhere = (TextView) itemView.findViewById(R.id.txtToWhere);
            txtToDate = (TextView) itemView.findViewById(R.id.txtToDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            post_id = adapterList.get(getAdapterPosition()).ads_id;
            Log.d("SAIM POST ID", post_id);
            v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), PostDetailActivity.class));
        }
    }
}
