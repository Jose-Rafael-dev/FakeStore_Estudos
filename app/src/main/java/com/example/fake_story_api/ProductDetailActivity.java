package com.example.fake_story_api;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProductDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView title = findViewById(R.id.detailTitle);
        TextView price = findViewById(R.id.detailPrice);
        TextView description = findViewById(R.id.detailDescription);
        ImageView image = findViewById(R.id.detailImage);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        price.setText("R$ " + intent.getDoubleExtra("price", 0));
        description.setText(intent.getStringExtra("description"));
        Glide.with(this).load(intent.getStringExtra("image")).into(image);
    }
}