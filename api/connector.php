<?php
// define('DBHOST', 'localhost');
define('DBHOST', '127.0.0.1');
define('DBUSER', 'u584085915_hccportal');
define('DBPASS', 'Sishccportal01');
define('DBNAME', 'u584085915_hccportal');
define('SECRET_KEY', 'v70d4rA8cZhbxpGUDP2BSUClg');
function noDB()
{
    $mysqli = new mysqli(DBHOST, DBUSER, DBPASS);
    return $mysqli;
}

function DB()
{
    $mysqli = new mysqli(DBHOST, DBUSER, DBPASS, DBNAME);
    return $mysqli;
}