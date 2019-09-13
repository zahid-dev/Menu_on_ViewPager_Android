package com.example.zahid.majorbackend;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsActivity extends AppCompatActivity {
    RequestQueue rQueue;
    String url = "http://192.168.137.1/FramesPie/wp-json/wc/v2/products";
    List<Object> productslist;
    private List<ProductItem> productItems;
    List<Object> links;
    Gson gson;
    Map<String,Object> singleproduct;
    Woocommerce wc;
    boolean isScrolling = false;
    LinearLayoutManager manager;
    Integer pageno = 2;
    int currentItems, totalItems, scrollOutItems;
    ProgressDialog progressDialog;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recyclerView = (RecyclerView) findViewById(R.id.products);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        productItems = new ArrayList<>();
        rQueue = Volley.newRequestQueue(ProductsActivity.this);
        wc = new Woocommerce();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                productslist = wc.GetListfromJson(response);
                for(int i = 0; i < productslist.size();++i) {
                    singleproduct = wc.GetMapfromList(productslist,i);
                    List<Object> images = wc.GetListfromMap(singleproduct,"images");
                    Map<String,Object> imagedata = wc.GetMapfromList(images,0);
                    String image = wc.GetDatafromMap(imagedata,"src");
                    ProductItem productItem = new ProductItem(
                            wc.GetDatafromMap(singleproduct,"name"),
                            wc.GetDatafromMap(singleproduct,"price"),
                            image
                            );
                    productItems.add(productItem);
                }
                if (productslist.isEmpty()) {
                    //products.append("No Products Found");
                }

                adapter = new MyAdapter(productItems,getApplicationContext());
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductsActivity.this, "Some error occurred", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", Woocommerce.auth);
                return params;
            }
        };
        rQueue.add(request);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    fetchData();
                }
            }
        });
    }

    private void fetchData() {
        String myurl = "http://192.168.137.1/FramesPie/wp-json/wc/v2/products?page="+pageno;
        StringRequest request = new StringRequest(Request.Method.GET, myurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                productslist = wc.GetListfromJson(response);
                for(int i = 0; i < productslist.size();++i) {
                    singleproduct = wc.GetMapfromList(productslist,i);
                    List<Object> images = wc.GetListfromMap(singleproduct,"images");
                    Map<String,Object> imagedata = wc.GetMapfromList(images,0);
                    String image = wc.GetDatafromMap(imagedata,"src");
                    ProductItem productItem = new ProductItem(
                            wc.GetDatafromMap(singleproduct,"name"),
                            wc.GetDatafromMap(singleproduct,"price"),
                            image
                    );
                    productItems.add(productItem);
                }
                if (productslist.isEmpty()) {
                    //products.append("No Products Found");
                }
                adapter.notifyDataSetChanged();
                pageno++;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductsActivity.this, "Some error occurred", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", Woocommerce.auth);
                return params;
            }
        };
        rQueue.add(request);
    }
}
