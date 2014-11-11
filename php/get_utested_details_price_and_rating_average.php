<?php

/*
 * Following code will get single utested details
 * A utested is identified by utested id (uid)
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
    
    $result = mysql_query("
SELECT p.uid, u.name, u.description, u.url, u.picurl, u.mapurl, p.pid, p.price, r.rid, r.rating 
FROM utested u 
	INNER JOIN price p 
	    ON p.pid = (SELECT MAX(pid) FROM price WHERE uid = u.uid ORDER BY uid DESC LIMIT 1)
	INNER JOIN rating r 
	    ON r.rid = (SELECT uid, AVG(rating) AS average_rating FROM `rating`)
WHERE u.uid=$uid
ORDER BY u.uid DESC LIMIT 1;
") or die(mysql_error());

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
$utested["rid"] = $result["rid"];
$utested["rating"] = $result["rating"];
			
			
			
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