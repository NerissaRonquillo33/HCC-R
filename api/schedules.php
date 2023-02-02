<?php
include_once(dirname(__FILE__).'/connector.php');
$json = json_decode(file_get_contents('php://input'), true);
header('Content-Type: application/json; charset=utf-8');
if (isset($json["secret_key"]) && isset($json["username"])) {
    $mysqli = DB();
    $studentid = trim(mysqli_real_escape_string($mysqli,$json["username"]));
    $sql = "SELECT * FROM students WHERE studentid = ".$studentid." LIMIT 1";
    $result = mysqli_query($mysqli,$sql);
    $row = mysqli_fetch_array($result,MYSQLI_ASSOC);
    $count = mysqli_num_rows($result);
    $data = array();
    $cys = $row['course'].$row['year'].$row['section'];
    $sql = "SELECT faculty.firstname,faculty.lastname,time.time_start,time.time_end,schedule.subject_code,schedule.room,schedule.day FROM schedule LEFT JOIN faculty ON faculty.facultyid = schedule.facultyid LEFT JOIN time ON schedule.time_id = time.time_id WHERE schedule.cys='".$cys."'";
    $result2 = $mysqli->query($sql);
    $row2 = $result2->fetch_all(MYSQLI_ASSOC);
    foreach ($row2 as $key => $value) {
        $data[] = array("studentid" => ($json["username"] ?? 'TBA'), "subject" => $value['subject_code'], "course" => $row['course'], "days" => abbr2name($value['day']), "time" => $value['time_start']." - ".$value['time_end'], "room" => $value['room'], "prof" => $value['firstname']." ".$value['lastname']);
    }
    echo json_encode(array("status"=>"success", "results"=>$data));
}

function abbr2name($abbr)
{
    $name = "";
    switch ($abbr) {
        case "m":
            $name = "Monday";
            break;
        case "t":
            $name = "Tuesday";
            break;
        case "w":
            $name = "Wednesday";
            break;
        case "th":
            $name = "Thursday";
            break;
        case "f":
            $name = "Friday";
            break;
        case "s":
            $name = "Saturday";
            break;
        case "sn":
            $name = "Sunday";
            break;
    }
    return $name;
}