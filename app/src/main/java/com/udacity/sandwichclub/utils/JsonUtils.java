package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_ORIGIN = "placeOfOrigin";
    private static final String KEY_INGREDIENTS = "ingredients";
    /**
     * Returns a list of {@link Sandwich} objects that has been built up from
     * parsing JSON data
     *
     * @param json the JSON data to be parsed
     * @return Sandwich objects
     */
    public static Sandwich parseSandwichJson(String json) {

        //try to parse the JSON data. if there's an error, a JSONException
        //exception object is thrown.
        //catch the exception so the app doesn't crash

        try {
            JSONObject baseJsonObject = new JSONObject(json);
            JSONObject name = baseJsonObject.getJSONObject(KEY_NAME);

            // sandwich name
            String mainName = name.getString(KEY_MAIN_NAME);

            // sandwich alias
            JSONArray alsoKnownAsArray = name.getJSONArray(KEY_ALSO_KNOWN_AS);
            List<String> alsoKnownAs = parseStringArray(alsoKnownAsArray);

            // sandwich place of origin
            String origin = baseJsonObject.getString(KEY_ORIGIN);

            // sandwich description
            String description = baseJsonObject.getString(KEY_DESCRIPTION);

            // sandwich image
            String image = baseJsonObject.getString(KEY_IMAGE);

            // sandwich ingredient
            JSONArray ingredientArray = baseJsonObject.getJSONArray(KEY_INGREDIENTS);
            List<String> ingredients = parseStringArray(ingredientArray);

            return new Sandwich(mainName, alsoKnownAs, origin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @param jsonArray is the input array
     * @return an ArrayList of Strings
     */
    private static List<String> parseStringArray(JSONArray jsonArray) {
        List<String> stringArray = null;
        if (jsonArray != null) {
            stringArray = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                stringArray.add(jsonArray.optString(i));
            }
        }
        return stringArray;
    }
}
