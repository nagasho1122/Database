package com.nagase.nagasho.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance() //Realmの定義

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memo: Memo? = read()

        if (memo != null){
            titleEditText.setText(memo.title)
            contentEditText.setText(memo.content)
        }

        saveButton.setOnClickListener {
            val title: String = titleEditText.text.toString()
            val content: String = contentEditText.text.toString()
            save(title, content)
        }
    }

    override fun onDestroy() { //アプリ終了時の処理
        super.onDestroy()
        realm.close()
    }
    fun read(): Memo?{
        return realm.where(Memo::class.java).findFirst()  //realmから最初のデータを取り出し
    }
    fun save(title: String, content: String){
        val memo: Memo? = read()

        realm.executeTransaction { //データベースへの書き込み

            if (memo != null) {
                memo.title = title
                memo.content = content
            } else {
                val newMemo: Memo = it.createObject(Memo::class.java) //保存するデータの新規作成
                newMemo.title = title
                newMemo.content = content
            }

            Snackbar.make(container,"保存しました",Snackbar.LENGTH_SHORT).show() //表示する長さ等の設定
        }
    }
}