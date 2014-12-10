<?php

/*
 * Following code will get single utested details
 * A utested is identified by utested id (uid)
 */
header('Content-Type: charset=UTF-8');
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
WHERE u.uid=$uid
ORDER BY u.uid DESC LIMIT 1
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
$utested["price"] = $result["price"];
$utested["rating"] = $result["rating"];
			
			
			
            // success
            $response["success"] = 1;

            // user node
            $response["utested"] = array();

            array_push($response["utested"], $utested);
//print_r ($response);
            // echoing JSON response
            print json_encode($response);
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