<?php
/*
 * Following code will list all the utested
 */

// array for JSON response
$response = array();


// include db connect class
require_once(__DIR__.'/dbconnect.php');

// connecting to db
$db = new DB_CONNECT();

// get all utested from utested table
$result = mysql_query("SELECT tbl.uid, tbl.name, tbl.description, tbl.url, tbl.picurl, tbl.mapurl, o.pid, o.price, MaxOrderPID FROM price o JOIN( SELECT c.uid, c.name, c.description, c.url, c.picurl, c.mapurl, MAX(o.pid) AS MaxOrderPID FROM utested c JOIN price o ON c.uid = o.uid GROUP BY c.uid, c.name) tbl ON o.uid = tbl.uid AND o.pid = tbl.MaxOrderPID") or die(mysql_error());


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
		$utested["pid"] = $row["pid"];
		$utested["price"] = $row["price"];
		$utested["MaxOrderPID"] = $row["MaxOrderPID"];


        // push single product into final response array
        array_push($response["utesteder"], $utested);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no utested found
    $response["success"] = 0;
    $response["message"] = "Ingen utested funnet";

    // echo no users JSON
    echo json_encode($response);
}
?>
