package com.mrhi.ex82jsonhttprequestdbtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter adapter;

    ArrayList<Item> items= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recycler);
        adapter= new ItemAdapter(this, items);
        recyclerView.setAdapter(adapter);

        //리사이클러뷰의 레이아웃매니져 설정
        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void clickLoad(View view) {

        //서버의 loadDBtoJson.php파일에
        //접속하여 (DB데이터들)결과 받기
        //Volley+ 라이브러리 사용

        //서버주소
        String serverUrl="http://mrhi2018.dothome.co.kr/Android/loadDBtoJson.php";

        //결과를 JsonArray받을 것이므로...
        //StringRequest가 아니라...
        //JsonArrayRequest를 이용할 것임
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                //파라미터로 응답받은 결과 JsonArray를 분석

                items.clear();
                adapter.notifyDataSetChanged();

                try {

                    for(int i=0; i<response.length(); i++){
                        JSONObject jsonObject= response.getJSONObject(i);

                        int no= Integer.parseInt( jsonObject.getString("no") );
                        String name= jsonObject.getString("name");
                        String msg= jsonObject.getString("message");
                        String imgPath= jsonObject.getString("imgPath");
                        String date= jsonObject.getString("date");

                        //이미지 경로의 경우 서버IP가 제외된 주소이므로(uploads/xxxxx.jpg) 바로 사용 불가.
                        imgPath = "http://mrhi2018.dothome.co.kr/Android/"+imgPath;

                        items.add( 0 , new Item(no, name, msg, imgPath, date) );
                        adapter.notifyItemInserted(0);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        //요청큐에 요청객체 추가
        requestQueue.add(jsonArrayRequest);

    }
}
