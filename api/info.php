<?php
$status = "";
$info = array();
try {
    include_once(dirname(__FILE__).'/connector.php');
    $json = json_decode(file_get_contents('php://input'), true);
    if (isset($json["secret_key"]) && isset($json["username"])) {
        $mysqli = DB();
        $secret_key = $json["secret_key"];
        $studentid = trim(mysqli_real_escape_string($mysqli,$json["username"]));
        $sql = "SELECT * FROM students WHERE studentid = ".$studentid." LIMIT 1";
        $result = mysqli_query($mysqli,$sql);
        $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
        $count = mysqli_num_rows($result);
        if($count > 0) {
            $age = explode("-", $row['birthday']);
            if (count($age) != 3) {
                $row["age"] = 0;
            } else {
                $date1 = new DateTime($row['birthday']);
                $date2 = new DateTime(date("Y-m-d"));
                $days  = $date2->diff($date1)->format('%a');
                $age = $days / 365;
                $age = (int)$age;
                $row["age"] = $age;
            }
            $info = $row;
            $status = "success";
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
$info["status"] = $status;
header('Content-Type: application/json; charset=utf-8');
echo json_encode($info);