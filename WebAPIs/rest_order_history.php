<?php
$con=mysqli_connect("localhost","root","","restproject") or die(mysqli_error($con));
class querystatus
{
    public $error='';
    public $status='';
    public $msg='';
}
$nob=new querystatus();
$resid=$_POST['resid'];
$total=$_POST['total'];
$items=$_POST['items'];
$table=$_POST['table'];
$query="INSERT INTO `rest_order_history`(`Sno`,`ResID`,`tableno`, `ItemsQuan`, `TotalPrice`, `Time`) VALUES ('0','".$resid."','".$table."','".$items."','".$total."',now())";
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