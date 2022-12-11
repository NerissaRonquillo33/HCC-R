<?php
include_once(dirname(__FILE__).'/connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"])) {
    $mysqli = DB();
    $secret_key = $json["secret_key"];
    $sql = "SELECT IFNULL(students.studentid, users.userid) AS studentid,IFNULL(students.firstname, 'NA') AS firstname,IFNULL(students.lastname, 'NA') AS lastname,IFNULL(students.address, 'NA') AS address,IFNULL(students.contact, 'NA') AS contact,IFNULL(students.birthday, 'NA') AS birthday,IFNULL(students.email, 'NA') AS email,IFNULL(students.course, 'NA') AS course,IFNULL(students.year, 'NA') AS year,IFNULL(students.section, 'NA') AS section,IFNULL(users.userid, students.studentid) AS userid,IFNULL(users.password, 'NA') AS password,users.role,profile_images.image FROM students RIGHT JOIN users ON students.studentid = users.userid LEFT JOIN profile_images ON students.studentid = profile_images.studentid";
    $result = $mysqli->query($sql);
    $row = $result->fetch_all(MYSQLI_ASSOC);
    echo json_encode(array("status"=>"success", "results"=>$row));
} else {
    echo json_encode(array("status"=>"none"));
}