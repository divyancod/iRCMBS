<?php
$con=mysqli_connect("localhost","root","","restproject") or die(mysqli_error($con));
class restdata
{
    public $resid;
    public $restname;
    public $owner;
    public $address;
    public $cloudornot;
    public $tables;
    public $employees;
}
$restuid=$_POST['restuid'];
$query = "select * from rest_login_list where resfireid='".$restuid."'";
$result = mysqli_query($con,$query);
$result= mysqli_query($con,$query);
$lop= mysqli_num_rows($result);
while($lop!=0)
{
    $ob= new restdata();
    if(mysqli_num_rows($result)>0)
    {
        $row=mysqli_fetch_array($result);
        $ob->resid=$row['ResID'];
        $ob->restname=$row['RestName'];
        $ob->owner=$row['Owner'];
        $ob->address=$row['Address'];
        $ob->cloudornot=$row['CloudKitchen'];
        $ob->tables=$row['NoOfTables'];
        $ob->employees=$row['NoOfEmployees'];
        
    }
    $lop--;
}
$mjs= json_encode($ob);
echo $mjs;
?>