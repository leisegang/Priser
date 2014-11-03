<?php

/*
 * Following code will get single utested details from the database
 * A utested is identified by utested id (uid)
 * This file creates a JSON array that the Android application parses with Java.
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/dbconnect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_GET["uid"])) {
    $uid = $_GET['uid'];

    // get a utested from utesteds table
    
    $result = mysql_query("SELECT utested.*, price.* FROM utested INNER JOIN price ON utested.uid = price.uid WHERE utested.uid=$uid ORDER BY pid DESC LIMIT 1") or die(mysql_error());

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);
            $utested = array();
			$utested["uid"] = $result["uid"];
			$utested["name"] = $result["name"];
			$utested["description"] = $result["description"];
			$utested["url"] = $result["url"];
			$utested["picurl"] = $result["picurl"];
			$utested["mapurl"] = $result["mapurl"];
			$utested["pid"] = $result["pid"];
			$utested["price"] = $result["price"];

			
			
			
            // success
            $response["success"] = 1;

            // user node
            $response["utested"] = array();

            array_push($response["utested"], $utested);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no utested found
            $response["success"] = 0;
            $response["message"] = "No utested found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no utested found
        $response["success"] = 0;
        $response["message"] = "No utested found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>