package com.trevorhalvorson.activitytransitionrecyclerviewexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_PARAM = "extra_production";
    public static final String IMAGE_TRANSITION_NAME = "image_transition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Production production = (Production) getIntent().getSerializableExtra(EXTRA_PARAM);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(production.getShowTitle());
        }

        ImageView posterImageView = (ImageView) findViewById(R.id.poster_image_view);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_text_view);

        descriptionTextView.setText(getDescription(production));

        ViewCompat.setTransitionName(posterImageView, IMAGE_TRANSITION_NAME);

        Glide.with(this)
                .load(production.getPoster())
                .into(posterImageView);

    }

    @NonNull
    private String getDescription(Production production) {
        return production.getSummary() +
                "\n\n Released: " + production.getReleaseYear() +
                "\n\n Rating: " + production.getRating() +
                "\n\n Directed by: " + production.getDirector() +
                "\n\n Cast: " + production.getShowCast() +
                "\n\n Runtime: " + production.getRuntime();
    }
}
