<?php 
 require_once 'connection.php';
 
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }
 
$id = $_POST['id'];

 $stmt = $con->prepare("SELECT * FROM `mahasiswa` WHERE id_mahasiswa ='$id'");

 $stmt->execute();
 
 $stmt->bind_result($id_mahasiswa,$nama,$nim,$alamat,$kelas);
 
 $output = array(); 
 
 while($stmt->fetch()){
 $temp = array();
 $temp['id_mahasiswa'] = $id_mahasiswa; 
 $temp['nama'] = $nama; 
 $temp['nim'] = $nim; 
 $temp['alamat'] = $alamat; 
 $temp['kelas'] = $kelas; 
 array_push($output, $temp);
 }
 
 echo json_encode($output);
 ?>