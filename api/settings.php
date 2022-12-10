<?php
$status = "";
$info = array();
try {
    include_once(dirname(__FILE__).'/connector.php');
    $json = json_decode(file_get_contents('php://input'), true);
    if (isset($json["secret_key"]) && isset($json["name"])) {
        $mysqli = DB();
        $secret_key = $json["secret_key"];
        $name = trim(mysqli_real_escape_string($mysqli,$json["name"]));
        $sql = "SELECT * FROM _settings WHERE name='$name' LIMIT 1";
        $result = mysqli_query($mysqli,$sql);
        $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
        $count = mysqli_num_rows($result);
        if($count > 0) {
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