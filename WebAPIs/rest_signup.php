<?php
$con=mysqli_connect("localhost","root","","restproject") or die(mysqli_error($con));

$restname=$_POST['restname'];
$restowner=$_POST['owner'];
$restaddress=$_POST['address'];
$restcloud=$_POST['cloudkitchen'];
$resttables=$_POST['tables'];
$restemployees=$_POST['employees'];
$resfireid=$_POST['restuid'];
$resid=rand(1000,9999);
class querystatus
{
    public $error='';
    public $status='';
    public $msg='';
}
$nob=new querystatus();

$query = "INSERT INTO `rest_login_list`(`Sno`,`ResFireID`, `ResID`, `RestName`, `Owner`, `Address`, `CloudKitchen`, `NoOfTables`, `NoOfEmployees`) VALUES (null,'".$resfireid."','".$resid."','".$restname."','".$restowner."','".$restaddress."','".$restcloud."','".$resttables."','".$restemployees."')";

//echo $query;

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