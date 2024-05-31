package com.grupo3.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SesionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    RequestQueue rq;
    JsonRequest jrq;

    EditText cajaUser, cajaPwd;
    Button btnConsultar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sesion, container, false);

        View vista = inflater.inflate(R.layout.fragment_sesion, container, false);
        cajaUser=(EditText) vista.findViewById(R.id.txtUser);
        cajaPwd=(EditText) vista.findViewById(R.id.txtPsw);
        btnConsultar=(Button) vista.findViewById(R.id.btn_sesion);

        rq= Volley.newRequestQueue(getContext());
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        return vista;
    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"Error al Iniciar sesion", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        User usuario= new User();

        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObjectson=null;

        try {
            jsonObjectson=jsonArray.getJSONObject(0);
            usuario.setUser(jsonObjectson.optString("user"));
            usuario.setPwd(jsonObjectson.optString("pwd"));
            usuario.setNames(jsonObjectson.optString("names"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private void iniciarSesion(){

        String url="http://192.168.1.51/login/sesion.php?user="+cajaUser.getText().toString()+
                "&pwd="+cajaPwd.getText().toString();
        jrq=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }
}