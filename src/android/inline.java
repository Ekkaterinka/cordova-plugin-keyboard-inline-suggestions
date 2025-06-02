package com.microline.zont.inline;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import android.view.View;
import android.os.Build;
import android.view.autofill.AutofillManager;

public class inline extends CordovaPlugin {

    protected void keyboardInlineSuggestions(CallbackContext callbackContext) {
        AutofillManager afm = null;
        View view = (View) webView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            afm = cordova.getContext().getSystemService(AutofillManager.class);
            if (afm != null) {
                afm.requestAutofill(view);
            }
        }

    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("keyboardInlineSuggestions")) {
            keyboardInlineSuggestions(callbackContext);
            return true;
        }
        return false;
    }
}
