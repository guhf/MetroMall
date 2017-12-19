package com.metro.metromall.fragments.search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.metro.metromall.tools.DatabaseContext;
import com.metro.metromall.tools.SearchRecordSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhf on 2017/11/22.
 */

public class SearchHandler {
    DatabaseContext mContext;
    Context context;
    private  SearchRecordSQLiteOpenHelper sqLiteOpenHelper;
    private  SQLiteDatabase db;
    List<String> search_history;
    public SearchHandler(Context context) {
        mContext = new DatabaseContext(context);
        this.context =  context;
        sqLiteOpenHelper = new SearchRecordSQLiteOpenHelper(mContext);
    }

    /**
     * 插入数据
     */
    public void insertData(String tempName) {
        db = sqLiteOpenHelper.getWritableDatabase();
        db.execSQL("insert into SearchHistory(Name,logDate) values('" + tempName + "',datetime('now'))");
        db.close();
    }
    /**
     * 清空数据
     */
    public void deleteData() {
        db = sqLiteOpenHelper.getWritableDatabase();
        db.execSQL("delete from SearchHistory");
        db.close();
    }
    /**
     * 查询全部数据
     */
    public List<String> selectData() {
        search_history = new ArrayList<String>();
        /**
         * table:表名称
         * columns:列名称数组
         * selection:条件字句，相当于where
         * selectionArgs:条件字句，参数数组
         * groupBy:分组列
         * having:分组条件
         * orderBy:排序列
         * limit:分页查询限制
         */
        Cursor cursor = sqLiteOpenHelper.getReadableDatabase().query(true,"SearchHistory",new String[]{"id","Name"}, null,null,"Name",null,"logDate DESC",null);
        //sqLiteOpenHelper.getWritableDatabase().query("SearchHistory",null,null,null,null,null,null); //
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                search_history.add(cursor.getString(cursor.getColumnIndex("Name")));
            }
        }else {
        }
        return  search_history;
    }
}
