<?php
class DB_Connect {
    private $conn;
 
    // Connecting to database
    public function connect() {
        require_once 'Config.php';
         
        // Connecting to mysql database
        $this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
//        mysqli_set_charset($this->conn, "utf8");
        // return database handler
        return $this->conn;
    }
}
 
?>