/*
 * creates a new XMLHttpRequest object which is the backbone of AJAX,
 * or returns false if the browser doesn't support it
 */
function getXMLHttpRequest() {
    var xmlHttpReq = false;
    // to create XMLHttpRequest object in non-Microsoft browsers
    if (window.XMLHttpRequest) {
        xmlHttpReq = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        try {
            // to create XMLHttpRequest object in later versions
            // of Internet Explorer
            xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (exp1) {
            try {
                // to create XMLHttpRequest object in older versions
                // of Internet Explorer
                xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (exp2) {
                xmlHttpReq = false;
            }
        }
    }
    return xmlHttpReq;
}
/*
 * AJAX call starts with this function
 */
function makeRequest(ele) {
    var xmlHttpRequest = getXMLHttpRequest();

    var id = ele.parentNode.id;
    var className = ele.className;
    var formData = "questionid=" + id + "&action=" + className;
    console.log(className);

    switch (className) {
        case "report":
            xmlHttpRequest.onreadystatechange = report(xmlHttpRequest, id);
            break;
        case "upvote":
            xmlHttpRequest.onreadystatechange = markUpvote(xmlHttpRequest, id);
            break;
        case "downvote":
            xmlHttpRequest.onreadystatechange = markDownvote(xmlHttpRequest, id);
            break;
    }

    xmlHttpRequest.open("POST", "voter", true);
    xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttpRequest.send(formData);
}

/*
 * Returns a function that waits for the state change in XMLHttpRequest
 */
function markUpvote(xmlHttpRequest, id) {

    // an anonymous function returned
    // it listens to the XMLHttpRequest instance
    return function() {
        if (xmlHttpRequest.readyState == 4) {
            if (xmlHttpRequest.status == 200) {
                // document.getElementById(id).innerHTML = xmlHttpRequest.responseText;
                var className = document.getElementById(id).firstChild.firstChild.className;
                if (className == "far fa-thumbs-up") {
                    document.getElementById(id).className = "fas fa-thumbs-up";
                }
                else {
                    document.getElementById(id).className = "far fa-thumbs-up";
                }

            } else {
                alert("HTTP error " + xmlHttpRequest.status + ": " + xmlHttpRequest.statusText);
            }
        }
    };
}

function markDownvote(xmlHttpRequest, id) {

    // an anonymous function returned
    // it listens to the XMLHttpRequest instance
    return function() {
        if (xmlHttpRequest.readyState == 4) {
            if (xmlHttpRequest.status == 200) {
                document.getElementById(id).className = "fas fa-thumbs-down";
                var className = document.getElementById(id).firstChild.firstChild.className;
                if (className == "far fa-thumbs-down") {
                    document.getElementById(id).className = "fas fa-thumbs-down";
                }
                else {
                    document.getElementById(id).className = "far fa-thumbs-down";
                }
            } else {
                alert("HTTP error " + xmlHttpRequest.status + ": " + xmlHttpRequest.statusText);
            }
        }
    };
}


function report(xmlHttpRequest, id) {

    // an anonymous function returned
    // it listens to the XMLHttpRequest instance
    return function() {
        if (xmlHttpRequest.readyState == 4) {
            if (xmlHttpRequest.status == 200) {
                var className = document.getElementById(id).firstChild.firstChild.className;
                // change icon style
                document.getElementById(id).className = "fas fa-bu";

                // pull out drop down text field

            } else {
                alert("HTTP error " + xmlHttpRequest.status + ": " + xmlHttpRequest.statusText);
            }
        }
    };
}