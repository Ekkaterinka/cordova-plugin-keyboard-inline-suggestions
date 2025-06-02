package com.microline.zont.inline;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

public class KeyboardInlineSuggestions extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("enableInlineSuggestions".equals(action)) {
            this.enableInlineSuggestions(callbackContext);
            return true;
        }
        return false;
    }

    private void enableInlineSuggestions(final CallbackContext callbackContext) {
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Activity activity = cordova.getActivity();
                    View view = activity.getCurrentFocus();

                    if (view instanceof EditText) {
                        EditText editText = (EditText) view;
                        InputConnection inputConnection = editText.onCreateInputConnection(new EditorInfo());

                        if (inputConnection != null) {
                            // Начиная с Android 11 (API 30), можно установить supportsInlineSuggestions
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                                EditorInfo editorInfo = new EditorInfo();
                                editorInfo.inputType = editText.getInputType();
                                editorInfo.setInitialSurroundingText(editText.getText());
                                editorInfo.supportsInlineSuggestions = true;

                                // Обновляем InputConnection
                                inputConnection = editText.onCreateInputConnection(editorInfo);

                                callbackContext.success("Inline suggestions enabled");
                            } else {
                                callbackContext.error("Inline suggestions require Android 11 (API 30) or higher");
                            }
                        } else {
                            callbackContext.error("Could not get InputConnection");
                        }
                    } else {
                        callbackContext.error("Current focus is not an EditText");
                    }
                } catch (Exception e) {
                    callbackContext.error("Error enabling inline suggestions: " + e.getMessage());
                }
            }
        });
    }
}