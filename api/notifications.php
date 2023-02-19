<?php
$status = "";
$info = array();
$info["billing"] = "";
$info["announcement"] = "";
try {
    include_once(dirname(__FILE__).'/connector.php');
    $json = json_decode(file_get_contents('php://input'), true);
    if (isset($json["secret_key"]) && isset($json["username"])) {
        $mysqli = DB();
        $secret_key = $json["secret_key"];
        $studentid = trim(mysqli_real_escape_string($mysqli,$json["username"]));
        $sql = "SELECT billing.*, UNIX_TIMESTAMP(billing.datecreated) AS convertedTS FROM billing WHERE studentid=$studentid ORDER BY convertedTS DESC LIMIT 1";
        $result = mysqli_query($mysqli,$sql);
        $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
        $count = mysqli_num_rows($result);
        if($count > 0) {
            $info["billing"] = $row;
        }
        $sql = "SELECT * FROM activities WHERE statusis = 'Active' ORDER BY eventid DESC";
        $result = $mysqli->query($sql);
        $row = $result->fetch_all(MYSQLI_ASSOC);
        $info["announcement"] = $row;
        $status = "success";
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