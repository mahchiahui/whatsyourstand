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
function makeRequestQuestion(ele) {
    var xmlHttpRequest = getXMLHttpRequest();

    var id = ele.parentNode.id;
    var className = ele.className;
    var questionId = id.split("-")[1];

    var formData = "questionid=" + questionId + "&action=" + className;
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
                // console.log(document.getElementById(id).childNodes);
                var nodeUp = document.getElementById(id).childNodes[6].childNodes[1].firstChild;
                var nodeDown = document.getElementById(id).childNodes[4].childNodes[1].firstChild;
                var count = nodeUp.innerHTML;

                if (nodeUp.className == "far fa-thumbs-up") {
                    nodeUp.className = "fas fa-thumbs-up";
                    nodeUp.innerHTML = parseInt(count, 10) + 1;
                }
                else {
                    nodeUp.className = "far fa-thumbs-up";
                    nodeUp.innerHTML = parseInt(count, 10) - 1;
                }

                if (nodeDown.className == "fas fa-thumbs-down") {
                    nodeDown.className = "far fa-thumbs-down";
                    nodeDown.innerHTML = parseInt(nodeDown.innerHTML, 10) - 1;
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
                var nodeUp = document.getElementById(id).childNodes[6].childNodes[1].firstChild;
                var nodeDown = document.getElementById(id).childNodes[4].childNodes[1].firstChild;
                var count = nodeDown.innerHTML;

                if (nodeDown.className == "far fa-thumbs-down") {
                    nodeDown.className = "fas fa-thumbs-down";
                    nodeDown.innerHTML = parseInt(count, 10) + 1;
                }
                else {
                    nodeDown.className = "far fa-thumbs-down";
                    nodeDown.innerHTML = parseInt(count, 10) - 1;
                }

                if (nodeUp.className == "fas fa-thumbs-up") {
                    nodeUp.className = "far fa-thumbs-up";
                    nodeUp.innerHTML = parseInt(nodeUp.innerHTML, 10) - 1;
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
                var node = document.getElementById(id).childNodes[2].firstChild.firstChild;
                var className = node.className;

                // change icon style
                node.className = "fas fa-times-circle";

                // pull out drop down text field
                /////// ???? //////
            } else {
                alert("HTTP error " + xmlHttpRequest.status + ": " + xmlHttpRequest.statusText);
            }
        }
    };
}