
function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}
var from = getUrlVars()["from"];
var to = getUrlVars()["to"];
var rdfrom = getUrlVars()["from"];
var rdto = getUrlVars()["to"];
if ((from == "from1" && to == "to1") || (from == "from2" && to == "to2") || (from == "from3" && to == "to3") || (from == "from4" && to == "to4")) {  
window.onload = function() {
    window.location.href = "index.html?msg=same";
}
}
if (!from) {  
window.onload = function() {
    window.location.href = "index.html?msg=f&to="+rdto+"";
}
}
if (!to) { 
window.onload = function() {
    window.location.href = "index.html?msg=t&from="+rdfrom+"";
}
}
if (!from && !to) { 
window.onload = function() {
    window.location.href = "index.html?msg=ft";
}
}

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}
var from = getUrlVars()["from"];
var to = getUrlVars()["to"];
if (from == 'from1') { var from = "Foppingadreef 22"; }
if (from == 'from2') { var from = "Gustav Mahlerlaan"; }
if (from == 'from3') { var from = "CCA Noord"; }
if (from == 'from4') { var from = "CCA Zuid"; }
if (to == 'to1') { var to = "Foppingadreef 22"; }
if (to == 'to2') { var to = "Gustav Mahlerlaan"; }
if (to == 'to3') { var to = "CCA Noord"; }
if (to == 'to4') { var to = "CCA Zuid"; }

var params = '{"arrival": "' + from + '", "destination": "'+ to + '"}';

var hardcodeJourneys = "";
var hardcodeJourneysJSON = "";

 function buildHtmlTable(selector, journeyJson) {
  var columns = addAllColumnHeaders(journeyJson, selector);

  for (var i = 0; i < journeyJson.length; i++) {
  
    var row$ = $('<tr/>');
    for (var colIndex = 1; colIndex < columns.length; colIndex++) {
      var cellValue = journeyJson[i][columns[colIndex]];
      if (cellValue == null) cellValue = "";
      row$.append($('<td/>').html(cellValue));
    }
    $(selector).append(row$);
  }
}


function addAllColumnHeaders(myList, selector) {
  var columnSet = ["id"];
  var headerTr$ = $('<tr/>');

  for (var i = 1; i < myList.length; i++) {
    var rowHash = myList[i];
    for (var key in rowHash) {
      if ($.inArray(key, columnSet) == -1) {
        columnSet.push(key);
        headerTr$.append($('<th/>').html(key));
      }
    }
  }
  $(selector).append(headerTr$);

  return columnSet;
}



function getJourneys() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
             var hardcodeJourneys = this.responseText;
             //'[{"id":1,"type":"BUS","latitude":51.658607,"longitude":51.658607,"departure_stop_name":"Den Dungen, Dungens Molen","arrival_stop_name":"Den Bosch"},{"id":2,"type":"type TODO","latitude":51.658607,"longitude":5.371466,"departure_stop_name":"Den Dungen, Dungens Molen","arrival_stop_name":"ARRIVAL TODO"},{"id":3,"type":"type TODO","latitude":51.658607,"longitude":5.371466,"departure_stop_name":"Den Dungen, Dungens Molen","arrival_stop_name":"ARRIVAL TODO"}]'
             var journeyJson = JSON.parse(hardcodeJourneys);
             buildHtmlTable('#excelDataTable', journeyJson);
             
         }
    };
    xhttp.open("POST", "http://localhost:8080/journeys/journey", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(params);
}
getJourneys();

