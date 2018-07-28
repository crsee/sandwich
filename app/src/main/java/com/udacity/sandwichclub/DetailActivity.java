package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mKnownAsTv;
    private TextView mOriginTv;
    private TextView mDescriptionTv;
    private TextView mIngredientsTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // set all views
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mKnownAsTv = findViewById(R.id.also_known_tv);
        mOriginTv = findViewById(R.id.origin_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            mOriginTv.setText(R.string.not_available);
            mOriginTv.setTextColor(getResources().getColor(R.color.unavailable));

        }
        mDescriptionTv.setText(sandwich.getDescription());

        if (sandwich.getAlsoKnownAs() != null && !sandwich.getAlsoKnownAs().isEmpty()) {
            List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
            getFromattedString(alsoKnownAsList);
        } else {
            mKnownAsTv.setText(R.string.not_available);
            mKnownAsTv.setTextColor(getResources().getColor(R.color.unavailable));
        }
        if (sandwich.getIngredients() != null) {
            List<String> ingredientList = sandwich.getIngredients();
            getFromattedString(ingredientList);
        } else {
            mIngredientsTv.setText("");
        }
    }

    /**
     * method to convert List<String> to String
     *
     * @param list is the input
     * @return a String
     */
    private String getFromattedString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = list.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                stringBuilder.append(list.get(i));
                if (i != size - 1) {
                    stringBuilder.append("\n");

                }
            }
        }
        return stringBuilder.toString();
    }
}
