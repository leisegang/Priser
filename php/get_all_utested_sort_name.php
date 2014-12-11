<?php

/*
 * Following code will list all the utested
 */
header('Content-Type: charset=UTF-8');
// array for JSON response
$response = array();


// include db connect class
require_once(__DIR__.'/dbconnect.php');

// connecting to db
$db = new DB_CONNECT();

// get all utested from utested table
$setmysql = mysql_query("SET CHARACTER SET utf8");  
$result = mysql_query("
SELECT p.uid
      , u.name
      , u.description
      , u.url
      , u.picurl
      , u.mapurl
      , p.price
      , ROUND(AVG(r.rating),1) rating
   FROM utested u
   JOIN price p
     ON p.uid = u.uid
   JOIN ( SELECT uid, MAX(pid) latest_price FROM price GROUP BY uid ) px
     ON px.uid = p.uid
    AND px.latest_price = p.pid
   JOIN rating r
     ON r.uid = u.uid
  GROUP
     BY u.name ASC;
") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // utested node
    $response["utesteder"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $utested = array();
        $utested["uid"] = $row["uid"];
        $utested["name"] = $row["name"];
        $utested["description"] = $row["description"];
		$utested["url"] = $row["url"];
		$utested["picurl"] = $row["picurl"];
		$utested["mapurl"] = $row["mapurl"];
		$utested["price"] = $row["price"];
		$utested["rating"] = $row["rating"];
		



        // push single product into final response array
        array_push($response["utesteder"], $utested);
    }
    // success
    $response["success"] = 1;
//print_r ($response);
    // echoing JSON response
    print str_replace('\\/', '/', json_encode($response));
} else {
    // no utested found
    $response["success"] = 0;
    $response["message"] = "Ingen utested funnet";

    // echo no users JSON
    echo json_encode($response);
}
?>
