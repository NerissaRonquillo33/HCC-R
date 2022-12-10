<?php
include_once(dirname(__FILE__).'/../connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
    $sql = "SELECT students.id AS idStudent, users.id AS idUser,students.studentid,students.firstname,students.lastname,students.address,students.contact,students.birthday,students.email,students.course,students.year,students.section,users.userid,users.password,users.role,profile_images.image FROM users LEFT JOIN students ON students.studentid = users.userid LEFT JOIN profile_images ON students.studentid = profile_images.studentid WHERE students.studentid IS NOT NULL";
    $result = $mysqli->query($sql);
    $row = $result->fetch_all(MYSQLI_ASSOC);
    echo json_encode(array("status"=>"success", "results"=>$row));
} else {
    echo json_encode(array("status"=>"none"));
}