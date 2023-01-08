package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CariMahasiswa extends AppCompatActivity {

    EditText searchId,nama,nim,kelas,alamat;

    TextView editTextId;

    Button btnSearch, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_mahasiswa);

        searchId = (EditText) findViewById(R.id.editTextSearchId);

        editTextId = (TextView) findViewById(R.id.editTextCariId);
        nama = (EditText) findViewById(R.id.editTextCariNama);
        nim = (EditText) findViewById(R.id.editTextCariNim);
        kelas = (EditText) findViewById(R.id.editTextCariKelas);
        alamat = (EditText) findViewById(R.id.editTextCariAlamat);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sId = editTextId.getText().toString().trim();
                String sNama = nama.getText().toString().trim();
                String sNim = nim.getText().toString().trim();
                String sKelas = kelas.getText().toString().trim();
                String sAlamat = alamat.getText().toString().trim();

                updateMahasiswa(sId,sNama,sNim,sKelas,sAlamat);
            }
        });

        btnSearch= (Button) findViewById(R.id.buttonSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sId = searchId.getText().toString().trim();

                if (sId.equals(""))
                {
                    searchId.requestFocus();
                    Toast.makeText(CariMahasiswa.this, "Kolom harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                searchMahasiswa(sId);
            }
        });

    }

    private void searchMahasiswa(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.CARI_MAHASISWA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);

                    editTextId.setText(object.getString("id_mahasiswa"));
                    nama.setText(object.getString("nama"));
                    nim.setText(object.getString("nim"));
                    kelas.setText(object.getString("kelas"));
                    alamat.setText(object.getString("alamat"));

                } catch (JSONException e) {
                    Toast.makeText(CariMahasiswa.this, "Gagal Mencari Mahasiswa", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void updateMahasiswa(String id, String nama, String nim, String kelas, String alamat) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.UPDATE_MAHASISWA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output="";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    JSONObject status = jsonArray.getJSONObject(0);
                    output = status.getString("status");

                    if (output.equals("OK")) {
                        Toast.makeText(CariMahasiswa.this, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CariMahasiswa.this, "Gagal Mengupdate Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("nama",nama);
                params.put("nim",nim);
                params.put("kelas",kelas);
                params.put("alamat",alamat);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}