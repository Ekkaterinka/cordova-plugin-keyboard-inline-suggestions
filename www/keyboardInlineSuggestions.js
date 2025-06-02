var exec = require('cordova/exec');

exports.keyboardInlineSuggestions = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'inline', 'keyboardInlineSuggestions', []);
};
