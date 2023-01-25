package com.sandipbhattacharya.registerlogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etReenterPassword;
    private TextView tvStatus;
     Button btnregister;
    String sp;
    private String URL = "http://192.168.29.6/saphp/p2.php";
    private String name, email, password, reenterPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etReenterPassword = findViewById(R.id.etReenterPassword);
        tvStatus = findViewById(R.id.tvStatus);
        btnregister = findViewById(R.id.btnRegister);
        name = email = password = reenterPassword = "";
        
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                reenterPassword = etReenterPassword.getText().toString().trim();
                if(!password.equals(reenterPassword)){
                    //Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!name.equals("") && !email.equals("") && !password.equals("")) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(Register.this, response.toString(), Toast.LENGTH_LONG).show();
                                String s=response.toString();

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray array = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject json=array.getJSONObject(i);
                                        sp=json.getString("name");
                                        Toast.makeText(Register.this, sp.toString(), Toast.LENGTH_SHORT).show();

                                }
                                    if (sp.equals("fIL")) {
                                        tvStatus.setText("Successfully registered.");
                                        btnregister.setClickable(false);
                                        Intent intent = new Intent(Register.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else if (sp.equals("ins")) {
                                        tvStatus.setText("Something went wrong!");                    }


                                } catch (JSONException e) {
                                    Toast.makeText(Register.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                                //Toast.makeText(Register.this, response.toString(), Toast.LENGTH_SHORT).show();
                                 //JSONObject jsonObject=new JSONObject();
                                //  String s=jsonObject.getString("message");
                                //Toast.makeText(Register.this, response.toString(), Toast.LENGTH_SHORT).show();JSONObject jsonObject=new JSONObject();
                                if (response.equals("success")) {
                                    tvStatus.setText("Successfully registered.");
                                    btnregister.setClickable(false);
                                } else if (response.equals("failure")) {
                                    tvStatus.setText("Something went wrong!");
                                }
                            }

                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> data = new HashMap<>();
                                data.put("name", name);
                                data.put("email", email);
                                data.put("password", password);
                                return data;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
                    }
                }
            }

            public void login(View view) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
                
            })
        ;}
        
    }
    
    
//    
//    
//    public void save(View view) {
//        name = etName.getText().toString().trim();
//        email = etEmail.getText().toString().trim();
//        password = etPassword.getText().toString().trim();
//        reenterPassword = etReenterPassword.getText().toString().trim();
//        if(!password.equals(reenterPassword)){
//            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
//        }
//        else if(!name.equals("") && !email.equals("") && !password.equals("")){
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    if (response.equals("success")) {
//                        tvStatus.setText("Successfully registered.");
//                        btnRegister.setClickable(false);
//                    } else if (response.equals("failure")) {
//                        tvStatus.setText("Something went wrong!");                    }
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
//                }
//            }){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> data = new HashMap<>();
//                    data.put("name", name);
//                    data.put("email", email);
//                    data.put("password", password);
//                    return data;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            requestQueue.add(stringRequest);
//        }
//    }
//
//    public void login(View view) {
//        Intent intent = new Intent(Register.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }

