<?php
include_once(dirname(__FILE__).'/../connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
	$studentid = trim(mysqli_real_escape_string($mysqli,$json["studentid"]));
	$lastname = trim(mysqli_real_escape_string($mysqli,$json["lastname"]));
	$firstname = trim(mysqli_real_escape_string($mysqli,$json["firstname"]));
	$address = trim(mysqli_real_escape_string($mysqli,$json["address"]));
	$contact = trim(mysqli_real_escape_string($mysqli,$json["contact"]));
	$birthday = trim(mysqli_real_escape_string($mysqli,$json["birthday"]));
	$email = trim(mysqli_real_escape_string($mysqli,$json["email"]));
	$course = trim(mysqli_real_escape_string($mysqli,$json["course"]));
	$year = trim(mysqli_real_escape_string($mysqli,$json["year"]));
	$section = trim(mysqli_real_escape_string($mysqli,$json["section"]));
	$role = trim(mysqli_real_escape_string($mysqli,$json["role"]));
	$password = md5(trim(mysqli_real_escape_string($mysqli,$json["password"])));
    $sql = "INSERT INTO students (studentid, firstname, lastname, address, contact, birthday, email, course, year, section) VALUES ($studentid, '$firstname', '$lastname', '$address', '$contact', '$birthday', '$email', '$course', '$year', '$section')";
    try {
        if ($mysqli->query($sql)) $status = "success";
        if ($mysqli->errno) $status = "error";
    } catch (mysqli_sql_exception $e) {
        $status = "duplicate";
    }
    $sql = "INSERT INTO users (userid, password, role) VALUES ('$studentid', '$password', '$role')";
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