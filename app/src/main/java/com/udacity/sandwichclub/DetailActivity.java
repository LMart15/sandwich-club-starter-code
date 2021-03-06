package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich != null) {
                populateUI(sandwich);
                Picasso.with(this)
                        .load(sandwich.getImage())
                        .error(R.drawable.ic_error_outline)
                        .into(ingredientsIv);
                setTitle(sandwich.getMainName());
            } else {
                // Sandwich is null
                closeOnError();
            }
        } catch (JSONException e) {
            // Sandwich json invalid
            closeOnError();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets TextViews text to their corresponding Sandwich value
     *
     * @param sandwich Sandwich object with detail information
     */
    private void populateUI(@NonNull Sandwich sandwich) {

        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTv.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }

        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (!sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setText(TextUtils.join(", ", sandwich.getIngredients()));
        }

        if (!sandwich.getDescription().isEmpty()) {
            descriptionTv.setText(sandwich.getDescription());
        }
    }
}
