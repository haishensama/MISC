/**
 * Created by zouyao on 2017/3/6.
 */
var myOrderCtrl = m.controller("myOrdercontroller",function ($rootScope,$scope) {
    $scope.prevPage = "上一页";
    $scope.nextPage = "下一页";
    $scope.currentPage = 1;
    $scope.totalPage = 1;
    $scope.constRef = [["查看详情"]];
    $scope.blurUserName = "";
    $scope.orderType = "";
    $scope.deleteTemp_id = 0;
    /**
     * 用户管理初始化
     */
    $rootScope.myo_init = function () {
        $scope.currentPage = 1;
        $scope.blurUserName = "";

        $scope.getOrderInfoListOfOrder();
        //$scope.fortest();
    };
    /**
     * 获取预约分页列表
     */
    $scope.getOrderInfoListOfOrder = function(){
        if($scope.currentPage == 0){
         this.currentPage = 1;
         }else{
         this.currentPage = $scope.currentPage;
         }
        $.ajax({
            type:"POST",
            url:"/sale/getSaleByUser",
            data:{"currentPage":this.currentPage,"pageSize":5,"user_id":$rootScope.idOfLoger},
            contentType:"application/x-www-form-urlencoded",
            dataType:"json",
            success:function(data){
                console.log(data);
                $scope.$apply(function(){
                    if(data.page.list.length == 0 && $scope.currentPage > 1){
                        $scope.currentPage = $scope.currentPage - 1;
                        $scope.getOrderInfoListOfOrder();
                    }
                    $scope.orderList = new Array();
                    var obj = {};
                    for(var temp in data.page.list){
                        obj['id'] = data.page.list[temp].id;
                        obj['arrange'] = data.page.list[temp].arrange;
                        obj['user'] = data.page.list[temp].user;
                        obj['seat'] = data.page.list[temp].seat;
                        obj['row'] = parseInt(data.page.list[temp].seat/10)+1;
                        obj['col'] = data.page.list[temp].seat%10;
                        obj['price'] = data.page.list[temp].arrange.price;
                        
                        var datestr = new Date(parseInt(data.page.list[temp].arrange.startTime));
                        var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                        + datestr.getHours() + ":" + datestr.getMinutes();// + ":" + datestr.getSeconds();
                        obj['startTime'] = temstr;
                        var datestr = new Date(parseInt(data.page.list[temp].arrange.endTime));
                        var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                        + datestr.getHours() + ":" + datestr.getMinutes();// + ":" + datestr.getSeconds();
                        obj['endTime'] = temstr;
                        
                        $scope.orderList.push(obj);obj = {};
                    }
                    //分页相关更新
                    $scope.currentPage = data.page.current;
                    $scope.totalPage = data.page.total;
                });
            }
        });
    };
    /**
     * 分页操作
     * @param obj
     */
    $scope.makePagingList = function(obj){
        if(obj=="上一页"){
            if($scope.currentPage == 0){
                //nothing to do
            }else if($scope.currentPage == 1){
                alert("当前已经是第一页！");//其实并不会发生，因为disabled
            }else{
                $scope.currentPage = $scope.currentPage - 1;
                $scope.getOrderInfoListOfOrder();
            }
        }else if(obj=="下一页"){
            if($scope.currentPage == 0){
                //nothing to do
            }else if($scope.currentPage == $scope.totalPage){
                alert("当前已经是最后一页！");//其实并不会发生，因为disabled
            }else{
                $scope.currentPage = $scope.currentPage + 1;
                $scope.getOrderInfoListOfOrder();
            }
        }
    };
    /**
     * 查看详情
     */
    $scope.viewDetailOfArrange = function(obj){
        $scope.viewDetailOfArrangeTemp = obj;
        $("#modalid-orderArrange-view").modal("toggle");
    };
    $scope.deleteOneOrderModal = function(obj){
    	$scope.deleteTemp_id = obj.id;
    	$scope.deleteOneOrderModalTemp = obj;
        $("#modalid-orderArrange-delete").modal("toggle");
    }

    $scope.deleteOneOrder = function(){
    	 
    	$.ajax({
             type: "POST",
             url: "/sale/deleteOneSale",
             data: {
                 "id": $scope.deleteTemp_id
             },
             contentType: "application/x-www-form-urlencoded",
             dataType: "json",
             success: function (data) {
                 console.log(data);
                 $scope.$apply(function(){
                     $rootScope.justForModalInfomation = "退票成功!";
                     $("#modalid-toastInfo").modal("toggle");
                     $scope.getOrderInfoListOfOrder();
                 });
             }
         });
    	
    } 
});