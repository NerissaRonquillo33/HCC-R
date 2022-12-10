<?php
include_once(dirname(__FILE__).'/connector.php');
$data = array();
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
    $sql = "SELECT DISTINCT schoolyear FROM grades";
    $result = $mysqli->query($sql);
    $row = $result->fetch_all(MYSQLI_ASSOC);
    $data['schoolyear'] = array();
    foreach ($row as $key => $value) {
        $data['schoolyear'][] = $value['schoolyear'];
    }
    $sql = "SELECT DISTINCT semester FROM grades";
    $result = $mysqli->query($sql);
    $row = $result->fetch_all(MYSQLI_ASSOC);
    $data['semester'] = array();
    foreach ($row as $key => $value) {
        $data['semester'][] = $value['semester'];
    }
    echo json_encode(array("status"=>"success", "results"=>$data));
} else {
    echo json_encode(array("status"=>"none"));
}