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
                $age = (date("md", date("U", mktime(0, 0, 0, $age[1], $age[2], $age[0]))) > date("md") ? ((date("Y") - $age[0]) - 1) : (date("Y") - $age[0]));
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