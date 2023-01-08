<?php
require_once 'connection.php';

if ($con) {
    $var1 = $_POST['input1'];
    $var2 = $_POST['input2'];
    
    $insert = "INSERT INTO tabel(kolom1, kolom2) VALUES('$var1','$var2')";

    if ($var1 !="" && $var2 !="") {
            $result = mysqli_query($con, $insert);
            $response = array();

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