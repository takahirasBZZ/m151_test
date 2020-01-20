/**
 * view-controller for testing
 *
 * M151: Refproject
 *
 * @author  Marcel Suter
 */

/**
 * fixme
 */
var objectCount = 0;
$(document).ready(function () {
    /**
     * listener for submitting the form
     */
    $("#testcase").submit(send);
});

function send(form) {
    form.preventDefault();
    var serviceURL = $("#service").val();
    var data = $("#data").val();
    var requestType = "";
    var ajaxData = "";
    var ajaxURL = "./resource/" + serviceURL;

    if (serviceURL == "project/save") {
        requestType = "POST";
        ajaxData = data;
    } else if (serviceURL == "project/delete") {
        requestType = "DELETE";
        ajaxURL += "?" + data;
    } else {
        requestType = "GET";
        ajaxURL += "?" + data;
    }

    $
        .ajax({
            url: ajaxURL,
            dataType: "json",
            type: requestType,
            data: ajaxData
        })
        .done(function (jsonData) {
            $("#httpStatus").text("200");
            var data = "";
            if (typeof jsonData === 'object')
                data = show(jsonData);
            else
                data = jsonData;
            $("#outputData").html(data);
        })
        .fail(function (xhr, status, errorThrown) {
            $("#httpStatus").text(xhr.statusCode);
            $("#outputData").html(show(xhr));
        })
}

function show(jsonData) {
    var output = "";
    for (var property in jsonData) {
        if (jsonData.hasOwnProperty(property)) {
            if (typeof jsonData[property] == "object") {
                output += "<ul><li>" + property + "<ul>" + show(jsonData[property]) + "</ul></li></ul>";
            } else {
                output += "<li>" + property + ": " + jsonData[property] + "</li>";
            }
        }
    }
    return output;
}

