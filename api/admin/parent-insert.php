<?php
include_once(dirname(__FILE__).'/../connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
	$studentid = trim(mysqli_real_escape_string($mysqli,$json["studentid"]));
	$fullname = trim(mysqli_real_escape_string($mysqli,$json["fullname"]));
    $email = trim(mysqli_real_escape_string($mysqli,$json["email"]));
	$username = trim(mysqli_real_escape_string($mysqli,$json["username"]));
	$password = md5(trim(mysqli_real_escape_string($mysqli,$json["password"])));
    $sql = "INSERT INTO _parents (fullname, student_id, username, password, email) VALUES ('$fullname', $studentid, '$username', '$password', '$email')";
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