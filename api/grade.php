<?php
include_once(dirname(__FILE__).'/connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"]) && isset($json["username"]) && isset($json["schoolyear"]) && isset($json["semester"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
    $username = trim(mysqli_real_escape_string($mysqli,$json["username"]));
    $schoolyear = trim(mysqli_real_escape_string($mysqli,$json["schoolyear"]));
    $semester = trim(mysqli_real_escape_string($mysqli,$json["semester"]));
    $sql = "SELECT * FROM grades WHERE studentid=$username AND schoolyear='$schoolyear' AND semester='$semester'";
    $result = $mysqli->query($sql);
    $row = $result->fetch_all(MYSQLI_ASSOC);
    echo json_encode(array("status"=>"success", "results"=>$row));
} else {
    echo json_encode(array("status"=>"none"));
}