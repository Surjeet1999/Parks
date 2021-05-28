package com.example.parks.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {
    public static final String PARKS_URL ="https://developer.nps.gov/api/v1/parks?stateCode=ny&api_key=Xv5wTCVDOQYTnjDojaa7oqCxnmMNEbAEIcrXRh7z#";
    public static String getParksUrl(String stateCode){
        return "https://developer.nps.gov/api/v1/parks?stateCode="+stateCode+"&api_key=Xv5wTCVDOQYTnjDojaa7oqCxnmMNEbAEIcrXRh7z#";
    }

    public static void hodeSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }
}
