<?php
include_once(dirname(__FILE__).'/connector.php');
$mysqli = DB();
$tokens = array();
$notif = false;
$sql = "SELECT * FROM activities WHERE statusis = 'Active' ORDER BY eventid DESC";
$result = $mysqli->query($sql);
$row = $result->fetch_all(MYSQLI_ASSOC);
$row_cnt = $result->num_rows;
if (file_exists(dirname(__FILE__)."/notification")) {
	$notification = file_get_contents(dirname(__FILE__)."/notification");
	$notif = ($row_cnt > $notification ? true : false);
	if ($notif) file_put_contents(dirname(__FILE__)."/notification", $row_cnt);
} else {
	$notif = false;
	file_put_contents(dirname(__FILE__)."/notification", $row_cnt);
}
if ($notif) {
	$sql = "SELECT * FROM _tokens";
	$result = $mysqli->query($sql);
	$rows = $result->fetch_all(MYSQLI_ASSOC);
	foreach ($rows as $row) {
		$resp = json_decode(BroadcastToAll($row['token']), true);
	    $tokens[] = array("token" => $row['token'], "success" => $resp['success']);
	}
}

function BroadcastToAll($token) {
	$url = "https://fcm.googleapis.com/fcm/send";

	$curl = curl_init();
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);

	$headers = array(
	   "Authorization: key=AAAAPx5sVz4:APA91bErvBB58qkNnxtswEjsfRvk0PPjqQjJzQEFtjOipRqFn7RMNNSSgaqgC2O5WfVK0N4BEqQrg2-NocTI2ZRzjxZcX-msc2E45FIYoGMQ2SqxqcPhtmaigLYJaCjBZP9C16uBYHBf",
	   "Content-type: application/json",
	);
	curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
	curl_setopt($curl, CURLOPT_POSTFIELDS, json_encode( array( "to"=> $token, "notification" => array("title" => "HCC Portal", "body" => "A new announcement or bill has arrived!") ) ));
	curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, false);
	curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);

	$resp = curl_exec($curl);
	curl_close($curl);
	return $resp;
}

header('Content-Type: application/json; charset=utf-8');
echo json_encode(array("tokens" => $tokens, "notify" => $notif));