package com.example.webimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebImageView image1 = (WebImageView) findViewById(R.id.image1);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ws1.se-ed.com/Storage/Originals/978616/204/9786162045585L.jpg";
                image1.setPlaceholderImage(R.mipmap.ic_launcher);
                image1.setImageUrl(url);
            }
        });
    }
}
