<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
include_once(dirname(__FILE__).'/connector.php');
$mysqli = DB();
$tokens = array();
$announce = false;
$bill = false;

function Announcement($mysqli) {
    $announce = false;
    $sql = "SELECT * FROM activities WHERE statusis = 'Active' ORDER BY eventid DESC";
    $result = $mysqli->query($sql);
    $row = $result->fetch_all(MYSQLI_ASSOC);
    $row_cnt = $result->num_rows;
    if (file_exists(dirname(__FILE__)."/notification")) {
        $notification = file_get_contents(dirname(__FILE__)."/notification");
        $announce = ($row_cnt > $notification ? true : false);
        if ($announce) file_put_contents(dirname(__FILE__)."/notification", $row_cnt);
    } else {
        $announce = false;
        file_put_contents(dirname(__FILE__)."/notification", $row_cnt);
    }
    return $announce;
}

function BillPerStudent($mysqli, $studentid) {
    $sql = "SELECT * FROM billing WHERE studentid=$studentid ORDER BY billingid DESC LIMIT 1";
    $result = $mysqli->query($sql);
    $row = $result->fetch_assoc();
    return $row['billingid'];
}

function ReadTokens($mysqli, $announce, $bill) {
    $sql = "SELECT * FROM _tokens";
    $result = $mysqli->query($sql);
    $rows = $result->fetch_all(MYSQLI_ASSOC);
    foreach ($rows as $row) {
        $bill = false;
        if ($row['username']) {
            $bill_current_id = BillPerStudent($mysqli, $row['username']);
            if ($bill_current_id != $row['last_record_bill_id']) {
                $mysqli->query("UPDATE _tokens SET last_record_bill_id=".$bill_current_id." WHERE username = '".$row['username']."'");
                $bill = true;
            }
            $resp = json_decode(BroadcastToAll($row['token'], $announce, $bill), true);
            $tokens[] = array("token" => $row['token'], "success" => $resp['success'], "announce" => $announce, "bill" => $bill);
        }
    }
}

function BroadcastToAll($token, $announce, $bill) {
    $msg = "A new announcement or bill has arrived!";
    $url = "https://fcm.googleapis.com/fcm/send";

    if ($announce && $bill) {
        $msg = "A new announcement and bill has arrived!";
    }
    elseif ($announce && !$bill) {
        $msg = "A new announcement has arrived!";
    }
    elseif (!$announce && $bill) {
        $msg = "A new bill has arrived!";
    } else {
        return json_encode(array("success" => 0));
    }

    $curl = curl_init();
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);

    $headers = array(
       "Authorization: key=AAAAPx5sVz4:APA91bErvBB58qkNnxtswEjsfRvk0PPjqQjJzQEFtjOipRqFn7RMNNSSgaqgC2O5WfVK0N4BEqQrg2-NocTI2ZRzjxZcX-msc2E45FIYoGMQ2SqxqcPhtmaigLYJaCjBZP9C16uBYHBf",
       "Content-type: application/json",
    );
    curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($curl, CURLOPT_POSTFIELDS, json_encode( array( "to"=> $token, "notification" => array("title" => "HCC App", "body" => $msg) ) ));
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, false);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);

    $resp = curl_exec($curl);
    curl_close($curl);
    return $resp;
}

$announce = Announcement($mysqli);
ReadTokens($mysqli, $announce, $bill);

header('Content-Type: application/json; charset=utf-8');
echo json_encode(array("tokens" => $tokens));