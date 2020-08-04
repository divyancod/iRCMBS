<?php
$con=mysqli_connect("localhost","root","","restproject");
if(mysqli_connect_errno())
{
    echo "failed to connect";
    die();
}
$resid=$_POST['resid'];

$query="SELECT * FROM `rest_employee_list` WHERE restid='".$resid."'";
$result= mysqli_query($con,$query);

$lop= mysqli_num_rows($result);


class ne
{
    public $empid=array();
    public $empname=array();
    public $emppost=array();
    public $empsalary=array();
    public $empgovtid=array();
    public $empaddress=array();
    public $empphone=array();

}


$i=0;
while($lop!=0)
{
    $ob[$i]= new ne();
if(mysqli_num_rows($result)>0)
{
    $row=mysqli_fetch_array($result);
    $ob[$i]->empid=$row['EmpID'];
    $ob[$i]->empname=$row['EmpName'];
    $ob[$i]->emppost=$row['EmpPost'];
    $ob[$i]->empgovtid=$row['EmpGovtID'];
    $ob[$i]->empsalary=$row['EmpSalary'];
    $ob[$i]->empaddress=$row['EmpAddress'];
    $ob[$i]->empphone=$row['EmpPhone'];


}
    $lop--;
    $i++;
}
$mjs= json_encode($ob);
echo $mjs;
?>