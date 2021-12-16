package com.jnu.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BooksAdapter extends ArrayAdapter<Book> {
    public BooksAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.text_view_book_title);
        ImageView ivCover = (ImageView) convertView.findViewById(R.id.image_view_book_cover);
        tvTitle.setText(book.getTitle());
        ivCover.setImageResource(book.getCoverRecourseId());

        return convertView;
    }
}