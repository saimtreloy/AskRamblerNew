package saim.com.askrambler.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import saim.com.askrambler.Model.ModelRequestList;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.MySingleton;

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

        ProgressDialog progressDialog;

        public PostViewHolder(View itemView) {
            super(itemView);

            progressDialog = new ProgressDialog(itemView.getContext());
            progressDialog.setMessage("Please wait status updateing");
            progressDialog.setCanceledOnTouchOutside(false);

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
            if (adapterList.get(position).getStatus().equals("Accept") || adapterList.get(position).getStatus().equals("Pending")) {
                showStatusList(v, adapterList.get(getAdapterPosition()).getId());
            }
        }


        public void showStatusList (final View v , final String ad_id) {
            LayoutInflater factory = LayoutInflater.from(v.getContext());
            final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
            final AlertDialog infoDialog = new AlertDialog.Builder(v.getContext()).create();
            infoDialog.setView(infoDialogView);
            infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
            txtDialog.setText("Select Status");

            ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1, v.getContext().getResources().getStringArray(R.array.post_request_status));
            listDialog.setAdapter(arrayAdapter);
            listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //editText.setText((String)parent.getItemAtPosition(position));
                    //infoDialog.dismiss();

                    String status_e = (String)parent.getItemAtPosition(position);
                    UpdateStatus(v, ad_id, status_e);
                }
            });
            infoDialog.show();
        }


        public void UpdateStatus(final View v, final String ad_id, final String status) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.getPostRequestStatusUpdate,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String code = jsonObject.getString("code");
                                if (code.equals("success")) {
                                    Toast.makeText(v.getContext(), "Status Updated", Toast.LENGTH_SHORT).show();
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
                    params.put("status", status);
                    params.put("ad_id", ad_id);

                    return params;
                }
            };
            stringRequest.setShouldCache(false);
            MySingleton.getInstance(v.getContext()).addToRequestQueue(stringRequest);

        }
    }
}
