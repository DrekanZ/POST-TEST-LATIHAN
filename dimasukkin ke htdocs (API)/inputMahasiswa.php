<?php
require_once 'connection.php';

if ($con) {
    $id = $_POST['id'];
    $nama = $_POST['nama'];
    $nim = $_POST['nim'];
    $alamat = $_POST['alamat'];
    $kelas = $_POST['kelas'];
    
    $insert = "INSERT INTO mahasiswa(id_mahasiswa, nama, nim, alamat, kelas) VALUES('$id','$nama','$nim','$alamat','$kelas')";
    $response = array();


    if ($nama !="" && $nim !="" && $alamat != ""&& $kelas != "") {
            $result = mysqli_query($con, $insert);

            if ($result) {
                array_push($response, array(
                    'status' => 'OK'
                ));
            } else {
                array_push($response, array(
                    'status' => "Register Gagal"
                ));
            }
    } else {
        array_push($response, array(
            'status' => 'All fields are required'
        ));
    }
}
else
{
    array_push($response, array(
            'status' => 'FAILED3'
    ));
}

echo json_encode(array("server_response" => $response));
mysqli_close($con);
?>