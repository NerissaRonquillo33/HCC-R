<?php
include '../config.php';
// $sql = "SELECT 'add table profile_images with studentid, image';DROP TABLE IF EXISTS profile_images;CREATE TABLE profile_images ( id INT AUTO_INCREMENT PRIMARY KEY, studentid INT NOT NULL UNIQUE, image LONGTEXT );SHOW TABLES;";
// $sql = "SELECT 'add table _settings with id, name, value, imageb64';DROP TABLE IF EXISTS _settings;CREATE TABLE _settings ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL UNIQUE, value LONGTEXT, imageb64 LONGTEXT );SHOW TABLES;";
$sql = "SELECT 'add table _parents with id, firstname, lastname, student_id, username, password, email';DROP TABLE IF EXISTS _parents;CREATE TABLE _parents ( id INT AUTO_INCREMENT PRIMARY KEY, firstname VARCHAR(255) NOT NULL,lastname VARCHAR(255) NOT NULL, student_id INT NOT NULL, username VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL );SHOW TABLES;";
mysqli_multi_query($conn, $sql);
do {
    if ($result = mysqli_store_result($conn)) {
        while ($row = mysqli_fetch_row($result)) {
            printf("%s<br>", $row[0]);
        }
    }
} while (mysqli_next_result($conn));
mysqli_close($conn);