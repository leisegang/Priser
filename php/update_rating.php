<?php

/*
 * Following code will update a utested information
 * A utested is identified by utested id (uid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['uid']) && isset($_POST['rating'])) {
    
    $uid = $_POST['uid'];
    $rating = $_POST['rating'];

    // include db connect class
    require_once __DIR__ . '/dbconnect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched uid
    //$result = mysql_query("UPDATE utested SET name = '$name', price = '$price', description = '$description' WHERE uid = $uid");
    $result = mysql_query("INSERT INTO rating (`rid`, `rating`, `uid`) VALUES (NULL, '$rating', '$uid');");
 

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Utested successfully updated.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
        
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
