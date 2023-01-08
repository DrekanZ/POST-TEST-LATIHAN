package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MahasiswaBaru extends AppCompatActivity {

    EditText id,nama,nim,kelas,alamat;
    Button submitMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_baru);

        id = (EditText) findViewById(R.id.editTextId);
        nama = (EditText) findViewById(R.id.editTextNama);
        nim = (EditText) findViewById(R.id.editTextNim);
        kelas = (EditText) findViewById(R.id.editTextKelas);
        alamat = (EditText) findViewById(R.id.editTextAlamat);

        submitMahasiswa = (Button) findViewById(R.id.submitMahasiswa);
        submitMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sId = id.getText().toString().trim();
                String sNama = nama.getText().toString().trim();
                String sNim = nim.getText().toString().trim();
                String sKelas = kelas.getText().toString().trim();
                String sAlamat = alamat.getText().toString().trim();

                if (!sNama.equals("")&& !sNim.equals("")&& !sKelas.equals("")&& !sAlamat.equals("")) {
                    postInputMahasiswa(sId,sNama,sNim,sKelas,sAlamat);
                } else
                {
                    Toast.makeText(MahasiswaBaru.this, "Tolong Isi Kolom yang Kurang", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void postInputMahasiswa(String id, String nama, String nim, String kelas, String alamat) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,DbContract.SUBMIT_MAHASISWA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output="";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    JSONObject status = jsonArray.getJSONObject(0);
                    output = status.getString("status");

                    if (output.equals("OK")) {
                        Toast.makeText(MahasiswaBaru.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                        clearEditText();
                    } else {
                        Toast.makeText(MahasiswaBaru.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT).show();
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

    private void clearEditText() {
        id.setText("");
        nama.setText("");
        nim.setText("");
        kelas.setText("");
        alamat.setText("");
    }
}