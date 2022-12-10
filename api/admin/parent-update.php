<?php
include_once(dirname(__FILE__).'/../connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
	$action = trim(mysqli_real_escape_string($mysqli,$json["action"]));
	$studentid = trim(mysqli_real_escape_string($mysqli,$json["studentid"]));
    $fullname = trim(mysqli_real_escape_string($mysqli,$json["fullname"]));
    $email = trim(mysqli_real_escape_string($mysqli,$json["email"]));
    $username = trim(mysqli_real_escape_string($mysqli,$json["username"]));
    $password = md5(trim(mysqli_real_escape_string($mysqli,$json["password"])));
	$id = trim(mysqli_real_escape_string($mysqli,$json["id"]));
    $sql = "UPDATE _parents SET fullname = '$fullname', student_id = $studentid, username = '$username'".(strlen($json["password"]) > 0 ? ", password = '$password'" : "").", email = '$email' WHERE id=$id";
    if ($action == "delete") {
    	$sql = "DELETE FROM _parents WHERE id=$id";
    }
    try {
        if ($mysqli->query($sql)) $status = "success";
        if ($mysqli->errno) $status = $mysqli->errno;
    } catch (mysqli_sql_exception $e) {
        $status = "duplicate";
    }
    echo json_encode(array("status"=>$status));
} else {
    echo json_encode(array("status"=>"none"));
}