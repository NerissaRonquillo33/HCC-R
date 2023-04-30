<?php
include_once(dirname(__FILE__).'/connector.php');

$mysqli = DB();
$tokens = array();
$sql = "SELECT * FROM _tokens";
$result = $mysqli->query($sql);
$rows = $result->fetch_all(MYSQLI_ASSOC);
foreach ($rows as $row) {
    $tokens[] = $row['token'];
}
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
header('Content-Type: application/json; charset=utf-8');
echo json_encode(array("tokens" => $tokens, "notify" => $notif));