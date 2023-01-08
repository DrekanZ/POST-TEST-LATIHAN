<?php 
 require_once 'connection.php';
 
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }
 
$var1 = $_POST['input1'];
$var2 = $_POST['input2'];

 $stmt = $con->prepare("SELECT kolom1, kolom2 FROM tabel WHERE kolom1='$var1'");

 $stmt->execute();
 
 $stmt->bind_result($output1, $output2);
 
 $output = array(); 
 
 while($stmt->fetch()){
 $temp = array();
 $temp['kolom1'] = $output1; 
 $temp['kolom2'] = $output2; 
 array_push($output, $temp);
 }
 
 echo json_encode($output);
 ?>