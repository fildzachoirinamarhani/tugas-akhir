package com.fildzachoirinamarhani.tugasakhirfinal;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class WisataFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private List<AplikasiModel> lstModel;
    private RequestQueue mRequest;
    private RecyclerViewAdapter mRecycler;
    private ProgressDialog progressDialog;

    private static final String URL="";

    public WisataFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wisata, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        lstModel = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        progressDialog=new ProgressDialog(getContext(),R.style.Base_Theme_AppCompat_Dialog);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        loadData();
        return view;
    }

    private void loadData() {
        //progressDialog.setMessage("Loading data...");
        //progressDialog.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response.length()>0) {
                    for (int i = 0; i < response.length(); i++) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray=response.getJSONArray("data");
                            JSONObject data=jsonArray.getJSONObject(i);
                            String namausaha=data.getString("namausaha");
                            String foto=data.getString("foto");
                            String harga=data.getString("harga");
                            int nominal=data.getInt("nominal");
                            String buka=data.getString("buka");
                            String tutup=data.getString("tutup");
                            String keterangan=data.getString("keterangan");
                            String latitud=data.getString("latitud");
                            String longitud=data.getString("longitud");

                            mRecycler=new RecyclerViewAdapter(getContext(),lstModel);
                            AplikasiModel item=new AplikasiModel(namausaha,foto,harga,nominal,buka,tutup,keterangan,latitud,longitud);
                            lstModel.add(item);
                            mRecycler.notifyItemInserted(lstModel.size()-1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mRecycler.addData(lstModel);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException | JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONObject response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);

    }
}
