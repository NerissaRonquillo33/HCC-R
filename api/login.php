<?php
$status = "";
$rows = array();
try {

    include_once(dirname(__FILE__).'/connector.php');
    $json = json_decode(file_get_contents('php://input'), true);
    if (isset($json["secret_key"]) && isset($json["username"]) && isset($json["password"])) {
        $mysqli = DB();
        $secret_key = $json["secret_key"];
        $username = trim(mysqli_real_escape_string($mysqli,$json["username"]));
        $password = md5(trim(mysqli_real_escape_string($mysqli,$json["password"])));
        $sql = "SELECT _parents.*, students.firstname, students.lastname, students.course FROM _parents LEFT JOIN students ON _parents.student_id=students.studentid WHERE username = '$username' AND password = '$password' LIMIT 1";
        $result = $mysqli->query($sql);
        $row = $result->fetch_all(MYSQLI_ASSOC);
        $row_cnt = $result->num_rows;
        if($row_cnt > 0) {
            $status = "success";
            $rows = $row[0];
        }else {
            $status = "denied";
        }
    } else {
        $status = "none";
    }

}
catch(Exception $e) {
    $status = "error";
}
catch(Error $e) {
    $status = "error";
}
header('Content-Type: application/json; charset=utf-8');
$rows["status"] = $status;
echo json_encode($rows);