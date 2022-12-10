<?php
include_once(dirname(__FILE__).'/../connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
	$action = trim(mysqli_real_escape_string($mysqli,$json["action"]));
	$studentid = trim(mysqli_real_escape_string($mysqli,$json["studentid"]));
	$billingid = trim(mysqli_real_escape_string($mysqli,$json["billingid"]));
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
    $sql = "UPDATE billing SET tuitionfee = $tuitionfee, learnandins = $learnandins, regfee = $regfee, compprossfee = $compprossfee, guidandcouns = $guidandcouns, schoolidfee = $schoolidfee, studenthand = $studenthand, schoolfab = $schoolfab, insurance = $insurance, totalass = $totalass, discount = $discount, netass = $netass, cashcheckpay = $cashcheckpay, balance = $balance WHERE billingid=$billingid";
    if ($action == "delete") {
    	$sql = "DELETE FROM billing WHERE billingid=$billingid";
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