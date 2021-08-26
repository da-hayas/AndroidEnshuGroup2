package com.example.androidenshugroup2;

import android.content.Context;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemDatabaseHelper extends AppCompatActivity {
    private ItemDatabase iDb;
    private ItemDao iDao;
    private List<ItemData> itemDataList = null;
    private PurchaseByGenreActivity pbgActivity;
    boolean isGetDataSuccess = false;
    final Handler mHandler = new Handler();

    public ItemDatabaseHelper(Context context, PurchaseByGenreActivity activity) {
        this.iDb = ItemDatabaseSingleton.getInstance(context);
        this.iDao = iDb.itemDao();
        this.pbgActivity = activity;
    }

    public void getDataAndUpdateRecyclerView(String genre) {
        isGetDataSuccess = false;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        //データベース関連は非同期処理で行う
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    itemDataList = iDao.loadItemsMatchingGenreOrderByItemNameASC(pbgActivity.getGenre());
                    isGetDataSuccess = true;
                } catch (Exception e) {
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!isGetDataSuccess) {
                            pbgActivity.displayMessage("データの読み込みに失敗しました。");
                        } else {
                            pbgActivity.updateItemRecyclerView(itemDataList);
                        }
                    }
                });
            }
        });
    }

}
