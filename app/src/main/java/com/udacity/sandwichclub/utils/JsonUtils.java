package com.udacity.sandwichclub.utils;

import android.support.annotation.Nullable;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String SANDWICH_INGREDIENTS = "ingredients";
    private static final String SANDWICH_MAIN_NAME = "mainName";
    private static final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_IMAGE = "image";

    /**
     * Parses JSON string into a Sandwich object
     *
     * @param json JSON String with Sandwich detail
     * @return Sandwich object
     * @throws JSONException String json not valid
     */
    public static Sandwich parseSandwichJson(@Nullable String json) throws JSONException {

        if (json != null && !json.isEmpty()) {

            JSONObject sandwichJson = new JSONObject(json);
            JSONObject sandwichName = sandwichJson.getJSONObject(SANDWICH_NAME);
            JSONArray alsoKnownAsJArray = sandwichName.getJSONArray(SANDWICH_ALSO_KNOWN_AS);
            JSONArray ingredientsJArray = sandwichJson.getJSONArray(SANDWICH_INGREDIENTS);

            String mainName = sandwichName.getString(SANDWICH_MAIN_NAME);
            List<String> alsoKnownAs = jsArrayToList(alsoKnownAsJArray);
            String placeOfOrigin = sandwichJson.getString(SANDWICH_PLACE_OF_ORIGIN);
            String description = sandwichJson.getString(SANDWICH_DESCRIPTION);
            String image = sandwichJson.getString(SANDWICH_IMAGE);
            List<String> ingredients = jsArrayToList(ingredientsJArray);

            return new
                    Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        return null;
    }

    /**
     * Returns a List from a JSONArray
     *
     * @param jsArray JSONArray contained with JSON String
     * @return List converted from JSONArray
     * @throws JSONException if JSON cannot be parsed
     */
    private static List<String> jsArrayToList(@Nullable JSONArray jsArray) throws JSONException {
        List<String> listFromJsArray = new ArrayList<>();
        if (jsArray != null) {
            for (int i = 0; i < jsArray.length(); i++) {
                listFromJsArray.add(jsArray.getString(i));
            }
        }
        return listFromJsArray;
    }
}
