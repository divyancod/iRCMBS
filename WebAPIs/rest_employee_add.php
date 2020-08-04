<?php
$con=mysqli_connect("localhost","root","","restproject") or die(mysqli_error($con));

$restid=$_POST['resid'];
$empid=rand(11111,99999);
$empname=$_POST['empname'];
$emppost=$_POST['emppost'];
$empsalary=$_POST['empsalary'];
$empgovid=$_POST['empgovid'];
$empaddress=$_POST['empaddress'];
$empphone=$_POST['empphone'];
class querystatus
{
    public $error='';
    public $status='';
    public $msg='';
}
$nob=new querystatus();
$query="INSERT INTO `rest_employee_list`(`Sno`, `RestID`, `EmpID`, `EmpName`, `EmpPost`, `EmpSalary`, `EmpGovtID`, `EmpAddress`, `EmpPhone`) VALUES ('0','".$restid."','".$empid."','".$empname."','".$emppost."','".$empsalary."','".$empgovid."','".$empaddress."','".$empphone."')";

//echo $query;

$sub=mysqli_query($con,$query);
//echo $sub;

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