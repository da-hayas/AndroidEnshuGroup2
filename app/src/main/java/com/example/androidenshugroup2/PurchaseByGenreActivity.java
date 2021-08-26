package com.example.androidenshugroup2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PurchaseByGenreActivity extends AppCompatActivity {
    private String genre;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_by_genre_activity);

        //遷移元のアクティビティから「ジャンル (String型)」を受け取る。
        //タイトルをジャンルに応じて設定
        Intent intentMain = getIntent();
        genre = intentMain.getStringExtra(PurchaseItemActivity.EXTRA_GENRE);
        String title = makeTitleString(genre);
        setTitle("商品購入 > " + title);

        //「戻る」ボタン
        Button returnButton = findViewById(R.id.return_btn);
        returnButton.setOnClickListener(v -> finish());

        //RecyclerViewの設定
        recyclerView = findViewById(R.id.item_rview);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration deco = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(deco);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        ItemDatabaseHelper itemDatabaseHelper = new ItemDatabaseHelper(getApplicationContext(), this);
        itemDatabaseHelper.getDataAndUpdateRecyclerView(genre);
    }

    private String makeTitleString(String genre) {
        String title;
        switch (genre) {
            case "juice":
                title = "ジュース";
                break;
            case "snack":
                title = "お菓子";
                break;
            case "ice":
                title = "アイス";
                break;
            case "noodle":
                title = "カップ麺";
                break;
            case "coffee":
                title = "コーヒー＆紅茶";
                break;
            case "others":
                title = "その他";
                break;
            default:
                title = "";
        }
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public void displayMessage(String message) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();  //トーストを表示
    }

    public void updateItemRecyclerView(List<ItemData> itemDataList) {
        ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(itemDataList, this);
        recyclerView.setAdapter(adapter);
    }
}