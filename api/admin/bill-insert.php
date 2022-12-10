<?php
include_once(dirname(__FILE__).'/../connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
	$studentid = trim(mysqli_real_escape_string($mysqli,$json["studentid"]));
	$tuitionfee = trim(mysqli_real_escape_string($mysqli,$json["tuitionfee"]));
	$learnandins = trim(mysqli_real_escape_string($mysqli,$json["learnandins"]));
	$regfee = trim(mysqli_real_escape_string($mysqli,$json["regfee"]));
	$compprossfee = trim(mysqli_real_escape_string($mysqli,$json["compprossfee"]));
	$guidandcouns = trim(mysqli_real_escape_string($mysqli,$json["guidandcouns"]));
	$schoolidfee = trim(mysqli_real_escape_string($mysqli,$json["schoolidfee"]));
	$studenthand = trim(mysqli_real_escape_string($mysqli,$json["studenthand"]));
	$schoolfab = trim(mysqli_real_escape_string($mysqli,$json["schoolfab"]));
	$insurance = trim(mysqli_real_escape_string($mysqli,$json["insurance"]));
	$totalass = trim(mysqli_real_escape_string($mysqli,$json["totalass"]));
	$discount = trim(mysqli_real_escape_string($mysqli,$json["discount"]));
	$netass = trim(mysqli_real_escape_string($mysqli,$json["netass"]));
	$cashcheckpay = trim(mysqli_real_escape_string($mysqli,$json["cashcheckpay"]));
	$balance = trim(mysqli_real_escape_string($mysqli,$json["balance"]));
    $sql = "INSERT INTO billing (studentid, tuitionfee, learnandins, regfee, compprossfee, guidandcouns, schoolidfee, studenthand, schoolfab, insurance, totalass, discount, netass, cashcheckpay, balance) VALUES ($studentid, $tuitionfee, $learnandins, $regfee, $compprossfee, $guidandcouns, $schoolidfee, $studenthand, $schoolfab, $insurance, $totalass, $discount, $netass, $cashcheckpay, $balance)";
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