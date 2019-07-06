console.log("HERE I AM ")


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


var hardcodeJourneys = '[{"id":1,"type":"BUS","latitude":51.658607,"longitude":51.658607,"departure_stop_name":"Den Dungen, Dungens Molen","arrival_stop_name":"Den Bosch"},{"id":2,"type":"type TODO","latitude":51.658607,"longitude":5.371466,"departure_stop_name":"Den Dungen, Dungens Molen","arrival_stop_name":"ARRIVAL TODO"},{"id":3,"type":"type TODO","latitude":51.658607,"longitude":5.371466,"departure_stop_name":"Den Dungen, Dungens Molen","arrival_stop_name":"ARRIVAL TODO"}]'
var hardcodeJourneysJSON = JSON.parse(hardcodeJourneys);


function buildHtmlTable(selector) {
  var columns = addAllColumnHeaders(hardcodeJourneysJSON, selector);

  for (var i = 0; i < hardcodeJourneysJSON.length; i++) {
  console.log(hardcodeJourneysJSON[i]);
  
    var row$ = $('<tr/>');
    for (var colIndex = 0; colIndex < columns.length; colIndex++) {
      var cellValue = hardcodeJourneysJSON[i][columns[colIndex]];
      if (cellValue == null) cellValue = "";
      row$.append($('<td/>').html(cellValue));
    }
    $(selector).append(row$);
  }
}

function addAllColumnHeaders(myList, selector) {
  var columnSet = [];
  var headerTr$ = $('<tr/>');

  for (var i = 0; i < myList.length; i++) {
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



var request = new XMLHttpRequest();
request.open('GET', 'localhost:8080/journeys/list', true);
request.onload = function () {
var journeys = JSON.parse(this.response);
 console.log("JOURNEYS");
 console.log(journeys);
}
request.send();
