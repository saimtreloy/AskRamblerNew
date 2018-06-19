package saim.com.askrambler.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import saim.com.askrambler.Activity.PostDetailActivity;
import saim.com.askrambler.Activity.RequestList;
import saim.com.askrambler.Model.ModelManagePost;
import saim.com.askrambler.Model.ModelPostShort;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.CircleTransform;
import saim.com.askrambler.Util.MySingleton;

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

        ProgressDialog progressDialog;

        CardView imgEditList, imgDeleteList;
        TextView txtFromWhere, txtPhone, txtType, txtEDate, txtAPDate, txtRequest;

        public PostViewHolder(final View itemView) {
            super(itemView);

            progressDialog = new ProgressDialog(itemView.getContext());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);

            imgEditList = (CardView) itemView.findViewById(R.id.imgEditList);
            imgDeleteList = (CardView) itemView.findViewById(R.id.imgDeleteList);

            txtFromWhere = (TextView) itemView.findViewById(R.id.txtFromWhere);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            txtEDate = (TextView) itemView.findViewById(R.id.txtEDate);
            txtAPDate = (TextView) itemView.findViewById(R.id.txtAPDate);
            txtRequest = (TextView) itemView.findViewById(R.id.txtRequest);

            itemView.setOnClickListener(this);

            imgDeleteList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Are you sure you want to delete this post?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Toast.makeText(imgDeleteList.getContext(), adapterList.get(getAdapterPosition()).getId() + "", Toast.LENGTH_SHORT).show();
                                    DeletePost(itemView, adapterList.get(getAdapterPosition()).getId());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            v.getContext().startActivity(new Intent(v.getContext().getApplicationContext(), RequestList.class).putExtra("AD_ID", adapterList.get(getAdapterPosition()).getId()));
        }


        public void DeletePost(final View v, final String ad_id) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.getPostDelete,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String code = jsonObject.getString("code");
                                if (code.equals("success")) {
                                    Toast.makeText(v.getContext(), "Post deleted.", Toast.LENGTH_SHORT).show();
                                    ((Activity) v.getContext()).finish();
                                }else {
                                    Log.d("SAIM SPLASH 3", response);
                                }
                            } catch (Exception e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("ad_id", ad_id);

                    return params;
                }
            };
            stringRequest.setShouldCache(false);
            MySingleton.getInstance(v.getContext()).addToRequestQueue(stringRequest);

        }
    }
}
