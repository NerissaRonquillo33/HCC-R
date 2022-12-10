<?php
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"]) && isset($json["username"])) {
	$curl = curl_init();
	curl_setopt($curl, CURLOPT_URL, "https://holycross.schedulems.info/student/api/schedule/get/".$json["username"]);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
	$response = curl_exec($curl);
	$decodedData = json_decode($response, true);
	$row = array();
	// var_dump($decodedData["students"]["section"]["section_subjects"]);
	foreach ($decodedData["students"]["section"]["section_subjects"] as $key => $value) {
		$row[] = array("studentid" => ($json["username"] ?? 'TBA'), "subject" => ($value["subject"]["subject"] ?? 'TBA'), "course" => ($decodedData["students"]["section"]["section_code"] ?? 'TBA'), "days" => ($value["generated_schedules"][0]["day"] ?? 'TBA'), "time" => ($value["generated_schedules"][0]["from"] ?? 'TBA')." - ".($value["generated_schedules"][0]["to"] ?? 'TBA'), "room" => ($value["subject"]["room_no"] ?? 'TBA'), "prof" => ($value["generated_schedules"][0]["professor_subject"]["professor"]["name"] ?? 'TBA'));
	}
	curl_close($curl);
	if (count($row) > 0) {
		echo json_encode(array("status"=>"success", "results"=>$row));
	} else {
		echo json_encode(array("status"=>"none"));
	}
	
}