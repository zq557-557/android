package com.jnu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class BookFragment extends Fragment {

    private View inflate;
    private ListView listView;
    BooksAdapter  adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_book, container, false);
        initView();
        return inflate;
    }

    public void initView() {
        listView = inflate.findViewById(R.id.list_view);

         adapter = new BooksAdapter(getContext(), getListBooks());
        listView.setAdapter(adapter);
        this.registerForContextMenu(listView);

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //设置Menu显示内容
        menu.add(1, 1, 1, "新建");
        menu.add(1, 2, 1, "删除");
        menu.add(1, 3, 1, "关于");
        menu.add(1, 2, 1, "修改");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId()) {
            case 1:
                Intent intent1 = new Intent(getContext(), EditBookActivity.class);
                intent1.putExtra("title", adapter.getItem(menuInfo.position).getTitle());
                startActivityForResult(intent1, 100);
                break;
            case 2:
                int position = menuInfo.position;
                adapter.remove(adapter.getItem(position));
                break;
            case 3:
                Toast.makeText(getContext(), "关于我们：这是个图书列表app", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Intent intent = new Intent(getContext(), EditBookActivity.class);
                intent.putExtra("title", adapter.getItem(menuInfo.position).getTitle());
                startActivityForResult(intent, 100);
                break;

        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            adapter.add(new Book(data.getStringExtra("title"), R.drawable.book_no_name));
        }
    }
    /**
     * 一、私有文件夹下的文件存取（/data/data/包名/files）
     *
     * @param fileName
     * @param message
     */
    public void writeFileData(String fileName, String message) {
        try {
            FileOutputStream fout = getContext().openFileOutput(fileName, MODE_PRIVATE);
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * //读文件在./data/data/包名/files/下面
     *
     * @param fileName
     * @return
     */
    public String readFileData(String fileName) {
        String res = "";
        try {
            FileInputStream fin = getContext().openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer, "utf-8");
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public List<Book> getListBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_1));
        bookList.add(new Book("创新工程实践", R.drawable.book_no_name));
        bookList.add(new Book("信息安全教学基础（第2版）", R.drawable.book_2));
        return bookList;
    }
}
