<?php
include_once(dirname(__FILE__).'/../connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"]) && isset($json["name"]) && isset($json["value"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
    $name = $json["name"];
    $value = $json["value"];
    $image_base64 = $json["image_base64"];
    $sql = "SELECT * FROM _settings WHERE name='$name'";
    $result = $mysqli->query($sql);
    $row_cnt = $result->num_rows;
    if ($row_cnt > 0) {
        $sql = "UPDATE _settings SET name='$name', value='$value'".(strlen($image_base64) > 300 ? ", imageb64='$image_base64'" : "")." WHERE name='$name'";
    } else {
        $sql = "INSERT INTO _settings(name,value,imageb64) VALUES('$name','$value','$image_base64')";
    }
    try {
        if ($mysqli->query($sql)) $status = "success";
        if ($mysqli->errno) $status = "error";
    } catch (mysqli_sql_exception $e) {
        $status = "duplicate";
    }
    echo json_encode(array("status"=>$status));
} else {
    echo json_encode(array("status"=>"none"));
}