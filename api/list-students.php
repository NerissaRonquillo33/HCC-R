<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Student list</title>
</head>
<body>
	<table border="1">
		<tr>
			<th style="width: 200px;">Fullname</th>
			<th style="width: 100px;">User ID</th>
			<th style="width: 100px;">Password</th>
			<th style="width: 300px;">Image Base64</th>
		</tr>
		<?php
		include_once(dirname(__FILE__).'/connector.php');

		$mysqli = DB();
		$sql = "SELECT students.studentid,students.firstname,students.lastname,students.address,students.contact,students.birthday,students.email,students.course,students.year,students.section,users.userid,users.password,users.role,profile_images.image FROM students LEFT JOIN users ON students.studentid = users.userid LEFT JOIN profile_images ON students.studentid = profile_images.studentid WHERE users.userid IS NOT NULL";
		$result = $mysqli->query($sql);
		$rows = $result->fetch_all(MYSQLI_ASSOC);
		foreach ($rows as $row) {
		    echo "<tr><td>${row['lastname']}, ${row['firstname']}</td><td>${row['userid']}</td><td>${row['password']}</td><td>".(strlen($row['image']) > 50 ? substr($row['image'],0,50) : "N/A")."</td></tr>";
		}
		?>
	</table>
</body>
</html>
