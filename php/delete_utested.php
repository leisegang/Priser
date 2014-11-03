<?php

/*
 * Following code will delete a utested from table
 * A utested is identified by utested id (uid)
 * This file will not be used in the final app. 
 * Because we dont want our users to delete the utested.
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['uid'])) {
    $uid = $_POST['uid'];

    // include db connect class
    require_once __DIR__ . '/dbconnect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched uid
    $result = mysql_query("DELETE FROM utested WHERE uid = $uid");
    
    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Utested successfully deleted";

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
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>