<?php
$con=mysqli_connect("localhost","root","","restproject");
if(mysqli_connect_errno())
{
    echo "failed to connect";
    die();
}
$resid=$_POST['resid'];
$query="select * from rest_order_history where resid='".$resid."' ORDER BY Time DESC";
$result= mysqli_query($con,$query);

$lop= mysqli_num_rows($result);


class ne
{
    public $tableno=array();
    public $totalitems=array();
    public $totalprice=array();
    public $time=array();
}


$i=0;
while($lop!=0)
{
    $ob[$i]= new ne();
if(mysqli_num_rows($result)>0)
{
    $row=mysqli_fetch_array($result);
    $ob[$i]->tableno=$row['tableno'];
    $ob[$i]->totalitems=$row['ItemsQuan'];
    $ob[$i]->totalprice=$row['TotalPrice'];
    $ob[$i]->time=$row['Time'];

}
    $lop--;
    $i++;
}
$mjs= json_encode($ob);
echo $mjs;
?>