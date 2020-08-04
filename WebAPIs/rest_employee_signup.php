<?php
$con=mysqli_connect("localhost","root","","restproject") or die(mysqli_error($con));
$fireid=$_POST['uid'];
$name=$_POST['name'];
$email=$_POST['email'];
$phone=$_POST['phone'];
$address=$_POST['address'];
class querystatus
{
    public $error='';
    public $status='';
    public $msg='';
}
$query="INSERT INTO `rest_employee_details`(`Sno`, `FirebaseID`, `Name`, `Email`, `Phone`, `Address`) VALUES (null,'".$fireid."','".$name."','".$email."','".$phone."','".$address."')";
$sub=mysqli_query($con,$query);

if($sub)
{
    $nob->error='0';
    $nob->status='success';
    $nob->msg='Data Inserted';
}
else
{
    $nob->error='1';
    $nob->status='failed';
    $nob->msg='Data Insertion Failed';
}
echo json_encode($nob);
?>