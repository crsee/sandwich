package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

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
            JSONObject name = baseJsonObject.getJSONObject("name");

            // sandwich name
            String mainName = name.getString("mainName");

            // sandwich alias
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = parseStringArray(alsoKnownAsArray);

            // sandwich place of origin
            String origin = baseJsonObject.getString("placeOfOrigin");

            // sandwich description
            String description = baseJsonObject.getString("description");

            // sandwich image
            String image = baseJsonObject.getString("image");

            // sandwich ingredient
            JSONArray ingredientArray = baseJsonObject.getJSONArray("ingredients");
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
